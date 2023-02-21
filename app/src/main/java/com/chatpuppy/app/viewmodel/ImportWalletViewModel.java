package com.chatpuppy.app.viewmodel;

import static com.chatpuppy.ethereum.EthereumNetworkBase.MAINNET_ID;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chatpuppy.app.C;
import com.chatpuppy.app.entity.ErrorEnvelope;
import com.chatpuppy.app.entity.ImportWalletCallback;
import com.chatpuppy.app.entity.SignAuthenticationCallback;
import com.chatpuppy.app.entity.analytics.ImportWalletType;
import com.chatpuppy.app.entity.Operation;
import com.chatpuppy.app.entity.Wallet;
import com.chatpuppy.app.interact.ImportWalletInteract;
import com.chatpuppy.app.repository.TokenRepository;
import com.chatpuppy.app.service.AnalyticsServiceType;
import com.chatpuppy.app.service.KeyService;
import com.chatpuppy.app.ui.widget.OnSetWatchWalletListener;
import com.chatpuppy.app.util.ens.AWEnsResolver;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletFile;
import org.web3j.utils.Numeric;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class ImportWalletViewModel extends BaseViewModel implements OnSetWatchWalletListener
{
    private final ImportWalletInteract importWalletInteract;
    private final KeyService keyService;
    private final AWEnsResolver ensResolver;

    private final MutableLiveData<Pair<Wallet, ImportWalletType>> wallet = new MutableLiveData<>();
    private final MutableLiveData<Boolean> badSeed = new MutableLiveData<>();
    private final MutableLiveData<String> watchExists = new MutableLiveData<>();

    @Inject
    ImportWalletViewModel(ImportWalletInteract importWalletInteract, KeyService keyService,
                          AnalyticsServiceType analyticsService)
    {
        this.importWalletInteract = importWalletInteract;
        this.keyService = keyService;
        this.ensResolver = new AWEnsResolver(TokenRepository.getWeb3jService(MAINNET_ID), keyService.getContext());
        setAnalyticsService(analyticsService);
    }

    public void onKeystore(String keystore, String password, String newPassword, KeyService.AuthenticationLevel level)
    {
        progress.postValue(true);

        importWalletInteract
                .importKeystore(keystore, password, newPassword)
                .flatMap(wallet -> importWalletInteract.storeKeystoreWallet(wallet, level, ensResolver))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(w -> onWallet(w, ImportWalletType.KEYSTORE), this::onError).isDisposed();
    }

    public void onPrivateKey(String privateKey, String newPassword, KeyService.AuthenticationLevel level)
    {
        progress.postValue(true);
        importWalletInteract
                .importPrivateKey(privateKey, newPassword)
                .flatMap(wallet -> importWalletInteract.storeKeystoreWallet(wallet, level, ensResolver))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(w -> onWallet(w, ImportWalletType.PRIVATE_KEY), this::onError).isDisposed();
    }

    public void onSeed(String walletAddress, KeyService.AuthenticationLevel level)
    {
        if (walletAddress == null)
        {
            Timber.e("walletAddress is null");
            progress.postValue(false);
            badSeed.postValue(true);
        }
        else
        {
            progress.postValue(true);
            //begin key storage process
            disposable = importWalletInteract.storeHDWallet(walletAddress, level, ensResolver)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(w -> onWallet(w, ImportWalletType.SEED_PHRASE), this::onError); //signal to UI wallet import complete
        }
    }

    @Override
    public void onWatchWallet(String address)
    {
        //do we already have this as a wallet?
        if (keystoreExists(address))
        {
            watchExists.postValue(address);
            return;
        }

        progress.postValue(true);
        //user just asked for a watch wallet
        disposable = importWalletInteract.storeWatchWallet(address, ensResolver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(w -> onWallet(w, ImportWalletType.WATCH), this::onError); //signal to UI wallet import complete
    }

    public LiveData<Pair<Wallet, ImportWalletType>> wallet()
    {
        return wallet;
    }

    public LiveData<Boolean> badSeed()
    {
        return badSeed;
    }

    public LiveData<String> watchExists()
    {
        return watchExists;
    }

    private void onWallet(Wallet wallet, ImportWalletType type)
    {
        progress.postValue(false);
        this.wallet.postValue(new Pair<>(wallet, type));
    }

    public void onError(Throwable throwable)
    {
        error.postValue(new ErrorEnvelope(C.ErrorCode.UNKNOWN, throwable.getMessage()));
    }

    public void getAuthorisation(Wallet wallet, Activity activity, SignAuthenticationCallback callback)
    {
        keyService.getAuthenticationForSignature(wallet, activity, callback);
    }

    public void importHDWallet(String seedPhrase, Activity activity, ImportWalletCallback callback)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            keyService.importHDKey(seedPhrase, activity, callback);
        }
    }

    public void importKeystoreWallet(String address, Activity activity, ImportWalletCallback callback)
    {
        keyService.createKeystorePassword(address, activity, callback);
    }

    public void importPrivateKeyWallet(String address, Activity activity, ImportWalletCallback callback)
    {
        keyService.createPrivateKeyPassword(address, activity, callback);
    }

    public boolean keystoreExists(String address)
    {
        return importWalletInteract.keyStoreExists(address);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Single<Boolean> checkKeystorePassword(String keystore, String keystoreAddress, String password)
    {
        return Single.fromCallable(() -> {
            boolean isValid = false;
            ObjectMapper objectMapper = new ObjectMapper();
            WalletFile walletFile = objectMapper.readValue(keystore, WalletFile.class);
            ECKeyPair kp = org.web3j.crypto.Wallet.decrypt(password, walletFile);
            String address = Numeric.prependHexPrefix(Keys.getAddress(kp));
            if (address.equalsIgnoreCase(keystoreAddress)) {
                isValid = true;
                storePubKey(kp.getPrivateKey().toByteArray(), address);
            }
            return isValid;
        });
    }

    public void resetSignDialog()
    {
        keyService.resetSigningDialog();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void completeAuthentication(Operation taskCode)
    {
        keyService.completeAuthentication(taskCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void failedAuthentication(Operation taskCode)
    {
        keyService.failedAuthentication(taskCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    // Chatpuppy
    public boolean storePubKey(byte[] pk, String address) {
        byte[] publicKey = keyService.createEncryptionPublicKey(pk);
        return keyService.storeEncryptionPublicKey(publicKey, address);
    }
}
