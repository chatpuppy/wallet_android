package com.chatpuppy.app.interact;

import com.chatpuppy.app.repository.TokenRepositoryType;
import com.chatpuppy.app.entity.tokens.Token;
import com.chatpuppy.app.entity.Wallet;

import io.reactivex.Completable;

public class ChangeTokenEnableInteract {
    private final TokenRepositoryType tokenRepository;

    public ChangeTokenEnableInteract(TokenRepositoryType tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Completable setEnable(Wallet wallet, Token token, boolean enabled) {
        tokenRepository.setEnable(wallet, token, enabled);
        tokenRepository.setVisibilityChanged(wallet, token);
        return Completable.fromAction(() -> { });
    }
}
