package com.chatpuppy.app.viewmodel;

import android.os.Environment;

import com.chatpuppy.app.repository.PreferenceRepositoryType;
import com.chatpuppy.app.service.AssetDefinitionService;
import com.chatpuppy.app.service.TransactionsService;

import java.io.File;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Single;

@HiltViewModel
public class AdvancedSettingsViewModel extends BaseViewModel {
    private final AssetDefinitionService assetDefinitionService;
    private final PreferenceRepositoryType preferenceRepository;
    private final TransactionsService transactionsService;

    @Inject
    AdvancedSettingsViewModel(
            AssetDefinitionService assetDefinitionService,
            PreferenceRepositoryType preferenceRepository,
            TransactionsService transactionsService) {
        this.assetDefinitionService = assetDefinitionService;
        this.preferenceRepository = preferenceRepository;
        this.transactionsService = transactionsService;
    }

    public boolean createDirectory() {
        //create XML repository directory
        File directory = new File(
                Environment.getExternalStorageDirectory()
                        + File.separator + HomeViewModel.ALPHAWALLET_DIR);

        if (!directory.exists()) {
            return directory.mkdir();
        }
        else
        {
            return directory.exists();
        }
    }

    public void startFileListeners()
    {
        assetDefinitionService.startAlphaWalletListener();
    }

    public void setFullScreenState(boolean state)
    {
        preferenceRepository.setFullScreenState(state);
    }

    public void toggle1559Transactions(boolean toggleState)
    {
        preferenceRepository.setUse1559Transactions(toggleState);
    }

    public boolean get1559TransactionsState()
    {
        return preferenceRepository.getUse1559Transactions();
    }

    public boolean getFullScreenState()
    {
        return preferenceRepository.getFullScreenState();
    }

    public void blankFilterSettings()
    {
        preferenceRepository.blankHasSetNetworkFilters();
    }

    public Single<Boolean> resetTokenData()
    {
        return transactionsService.wipeDataForWallet();
    }

    public void stopChainActivity()
    {
        transactionsService.stopActivity();
    }
}
