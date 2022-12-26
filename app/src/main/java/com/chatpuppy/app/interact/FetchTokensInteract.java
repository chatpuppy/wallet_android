package com.chatpuppy.app.interact;

import com.chatpuppy.app.entity.ContractLocator;
import com.chatpuppy.app.entity.ContractType;
import com.chatpuppy.app.entity.Wallet;
import com.chatpuppy.app.entity.tokens.Token;
import com.chatpuppy.app.entity.tokens.TokenCardMeta;
import com.chatpuppy.app.entity.tokendata.TokenTicker;
import com.chatpuppy.app.entity.tokens.TokenInfo;
import com.chatpuppy.app.repository.TokenRepositoryType;
import com.chatpuppy.app.service.AssetDefinitionService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FetchTokensInteract {

    private final TokenRepositoryType tokenRepository;

    public FetchTokensInteract(TokenRepositoryType tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Observable<ContractLocator> getContractResponse(String address, long chainId, String method)
    {
        return tokenRepository.getTokenResponse(address, chainId, method).toObservable();
    }

    public Observable<Token> updateDefaultBalance(Token token, Wallet wallet)
    {
        return tokenRepository.fetchActiveTokenBalance(wallet.address, token)
                .subscribeOn(Schedulers.io());
    }

    public Observable<Token> updateBalance(String address, Token token)
    {
        if (token == null) return Observable.fromCallable(()
                                      -> new Token(new TokenInfo(), BigDecimal.ZERO, 0, "", ContractType.NOT_SET));
        return tokenRepository.fetchActiveTokenBalance(address, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<TokenTicker> getEthereumTicker(long chainId)
    {
        return tokenRepository.getEthTicker(chainId);
    }

    public Single<Boolean> checkRedeemed(Token token, List<BigInteger> tickets)
    {
        if (token == null || tickets == null || tickets.size() == 0) return Single.fromCallable(() -> true ); //early return for invalid input
        BigInteger tokenId = tickets.get(0);
        return tokenRepository.fetchIsRedeemed(token, tokenId);
    }

    public Single<TokenCardMeta[]> fetchTokenMetas(Wallet wallet, List<Long> networkFilters, AssetDefinitionService svs)
    {
        return tokenRepository.fetchTokenMetas(wallet, networkFilters, svs);
    }

    public Single<TokenCardMeta[]> searchTokenMetas(Wallet wallet, List<Long> networkFilters, String searchTerm)
    {
        return tokenRepository.fetchAllTokenMetas(wallet, networkFilters, searchTerm);
    }
}
