package com.alphawallet.app.web3;

import android.security.keystore.UserNotAuthenticatedException;

import com.alphawallet.app.entity.cryptokeys.KeyServiceException;
import com.alphawallet.app.web3.entity.WalletAddEthereumChainObject;

/**
 * Created by JB on 15/01/2022.
 */
public interface OnWalletActionListener
{
    void onRequestAccounts(long callbackId);

    // Chatpuppy
    void onEthGetEncryptionPublickey(long callbackId);

    // Chatpuppy
    void onEthDecrypt(long callbackId,String encryptedMessage) throws KeyServiceException, UserNotAuthenticatedException;

    void onWalletSwitchEthereumChain(long callbackId, WalletAddEthereumChainObject chainObj);
}
