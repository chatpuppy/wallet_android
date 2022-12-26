package com.chatpuppy.app.entity;
import com.chatpuppy.app.entity.cryptokeys.KeyEncodingType;
import com.chatpuppy.app.service.KeyService;

public interface ImportWalletCallback
{
    void walletValidated(String address, KeyEncodingType type, KeyService.AuthenticationLevel level);
}
