package com.chatpuppy.app.repository;

import com.chatpuppy.app.entity.OnRampContract;
import com.chatpuppy.app.entity.tokens.Token;

public interface OnRampRepositoryType {
    String getUri(String address, Token token);

    OnRampContract getContract(Token token);
}
