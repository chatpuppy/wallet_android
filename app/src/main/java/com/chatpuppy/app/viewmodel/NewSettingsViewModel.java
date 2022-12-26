package com.chatpuppy.app.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.content.Context;

import com.chatpuppy.app.entity.CurrencyItem;
import com.chatpuppy.app.entity.LocaleItem;
import com.chatpuppy.app.entity.Transaction;
import com.chatpuppy.app.entity.Wallet;
import com.chatpuppy.app.interact.GenericWalletInteract;
import com.chatpuppy.app.repository.CurrencyRepositoryType;
import com.chatpuppy.app.repository.LocaleRepositoryType;
import com.chatpuppy.app.repository.PreferenceRepositoryType;
import com.chatpuppy.app.router.ManageWalletsRouter;
import com.chatpuppy.app.router.MyAddressRouter;
import com.chatpuppy.app.service.TickerService;
import com.chatpuppy.app.service.AnalyticsServiceType;
import com.chatpuppy.app.service.TransactionsService;
import com.chatpuppy.app.util.LocaleUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Single;

@HiltViewModel
public class NewSettingsViewModel extends BaseViewModel
{

    private final MutableLiveData<Wallet> defaultWallet = new MutableLiveData<>();
    private final MutableLiveData<Transaction[]> transactions = new MutableLiveData<>();
    private final MutableLiveData<String> backUpMessage = new MutableLiveData<>();
    private final GenericWalletInteract genericWalletInteract;
    private final MyAddressRouter myAddressRouter;
    private final ManageWalletsRouter manageWalletsRouter;
    private final PreferenceRepositoryType preferenceRepository;
    private final LocaleRepositoryType localeRepository;
    private final CurrencyRepositoryType currencyRepository;
    private final TransactionsService transactionsService;
    private final TickerService tickerService;

    @Inject
    NewSettingsViewModel(
            GenericWalletInteract genericWalletInteract,
            MyAddressRouter myAddressRouter,
            ManageWalletsRouter manageWalletsRouter,
            PreferenceRepositoryType preferenceRepository,
            LocaleRepositoryType localeRepository,
            CurrencyRepositoryType currencyRepository,
            TransactionsService transactionsService,
            TickerService tickerService,
            AnalyticsServiceType analyticsService)
    {
        this.genericWalletInteract = genericWalletInteract;
        this.myAddressRouter = myAddressRouter;
        this.manageWalletsRouter = manageWalletsRouter;
        this.preferenceRepository = preferenceRepository;
        this.localeRepository = localeRepository;
        this.currencyRepository = currencyRepository;
        this.transactionsService = transactionsService;
        this.tickerService = tickerService;
        setAnalyticsService(analyticsService);
    }

    public ArrayList<LocaleItem> getLocaleList(Context context)
    {
        return localeRepository.getLocaleList(context);
    }

    public void setLocale(Context activity)
    {
        String currentLocale = localeRepository.getActiveLocale();
        LocaleUtils.setLocale(activity, currentLocale);
    }

    public void updateLocale(String newLocale, Context context)
    {
        localeRepository.setUserPreferenceLocale(newLocale);
        localeRepository.setLocale(context, newLocale);
    }

    public String getDefaultCurrency()
    {
        return currencyRepository.getDefaultCurrency();
    }

    public ArrayList<CurrencyItem> getCurrencyList()
    {
        return currencyRepository.getCurrencyList();
    }

    public Single<Boolean> updateCurrency(String currencyCode)
    {
        currencyRepository.setDefaultCurrency(currencyCode);
        tickerService.updateCurrencyConversion();
        //delete tickers from realm
        return transactionsService.wipeTickerData();
    }

    public String getActiveLocale()
    {
        return localeRepository.getActiveLocale();
    }

    public void showManageWallets(Context context, boolean clearStack)
    {
        manageWalletsRouter.open(context, clearStack);
    }

    public boolean getNotificationState()
    {
        return preferenceRepository.getNotificationsState();
    }

    public void setNotificationState(boolean notificationState)
    {
        preferenceRepository.setNotificationState(notificationState);
    }

    @Override
    protected void onCleared()
    {
        super.onCleared();
    }

    public LiveData<Wallet> defaultWallet()
    {
        return defaultWallet;
    }

    public LiveData<Transaction[]> transactions()
    {
        return transactions;
    }

    public LiveData<String> backUpMessage()
    {
        return backUpMessage;
    }

    public void prepare()
    {
        disposable = genericWalletInteract
                .find()
                .subscribe(this::onDefaultWallet, this::onError);
    }

    private void onDefaultWallet(Wallet wallet)
    {
        defaultWallet.setValue(wallet);

        TestWalletBackup();
    }

    public void TestWalletBackup()
    {
        if (defaultWallet.getValue() != null)
        {
            genericWalletInteract.getWalletNeedsBackup(defaultWallet.getValue().address)
                    .subscribe(backUpMessage::postValue).isDisposed();
        }
    }

    public void showMyAddress(Context context)
    {
        myAddressRouter.open(context, defaultWallet.getValue());
    }

    public void setIsDismissed(String walletAddr, boolean isDismissed)
    {
        genericWalletInteract.setIsDismissed(walletAddr, isDismissed);
    }

    public void setMarshMallowWarning(boolean shown)
    {
        preferenceRepository.setMarshMallowWarning(shown);
    }
}
