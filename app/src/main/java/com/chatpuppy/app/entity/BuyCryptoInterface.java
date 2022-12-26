package com.chatpuppy.app.entity;

import com.chatpuppy.app.entity.tokens.Token;

public interface BuyCryptoInterface {
    void handleBuyFunction(Token token);
    void handleGeneratePaymentRequest(Token token);
}
