package com.chatpuppy.app.viewmodel;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chatpuppy.app.C;
import com.chatpuppy.app.entity.Wallet;
import com.chatpuppy.app.entity.nftassets.NFTAsset;
import com.chatpuppy.app.entity.tokens.Token;
import com.chatpuppy.app.interact.FetchTransactionsInteract;
import com.chatpuppy.app.service.AssetDefinitionService;
import com.chatpuppy.app.service.TokensService;
import com.chatpuppy.app.ui.TransferNFTActivity;
import com.chatpuppy.app.util.Utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Erc1155AssetSelectViewModel extends BaseViewModel {
    private final FetchTransactionsInteract fetchTransactionsInteract;
    private final AssetDefinitionService assetDefinitionService;
    private final TokensService tokensService;

    private final MutableLiveData<Map<BigInteger, NFTAsset>> assets = new MutableLiveData<>();

    @Inject
    public Erc1155AssetSelectViewModel(FetchTransactionsInteract fetchTransactionsInteract,
                                       AssetDefinitionService assetDefinitionService,
                                       TokensService tokensService)
    {
        this.fetchTransactionsInteract = fetchTransactionsInteract;
        this.assetDefinitionService = assetDefinitionService;
        this.tokensService = tokensService;
    }

    public TokensService getTokensService()
    {
        return tokensService;
    }

    public AssetDefinitionService getAssetDefinitionService()
    {
        return this.assetDefinitionService;
    }

    public LiveData<Map<BigInteger, NFTAsset>> assets() {
        return assets;
    }

    public void getAssets(Token token, List<BigInteger> tokenIds)
    {
        if (tokenIds.size() > 0)
        {
            Map<BigInteger, NFTAsset> assetMap = new HashMap<>();
            for (BigInteger tokenId : tokenIds) { assetMap.put(tokenId, token.getAssetForToken(tokenId)); }
            assets.postValue(assetMap);
        }
        else
        {
            assets.postValue(token.getTokenAssets());
        }
    }

    public Intent completeTransferIntent(Context ctx, Token token, List<BigInteger> tokenIds, ArrayList<NFTAsset> selection, Wallet wallet)
    {
        Intent intent = new Intent(ctx, TransferNFTActivity.class);
        intent.putExtra(C.Key.WALLET, wallet);
        intent.putExtra(C.EXTRA_CHAIN_ID, token.tokenInfo.chainId);
        intent.putExtra(C.EXTRA_ADDRESS, token.getAddress());
        intent.putExtra(C.EXTRA_TOKENID_LIST, Utils.bigIntListToString(tokenIds, false));
        intent.putParcelableArrayListExtra(C.EXTRA_NFTASSET_LIST, selection);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }
}
