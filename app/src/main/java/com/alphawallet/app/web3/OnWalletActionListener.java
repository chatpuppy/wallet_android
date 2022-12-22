package com.alphawallet.app.web3;

import com.alphawallet.app.web3.entity.WalletAddEthereumChainObject;

/**
 * Created by JB on 15/01/2022.
 */
public interface OnWalletActionListener
{
    void onRequestAccounts(long callbackId);

    void onEthGetEncryptionPublickey(long callbackId);

    void onEthDecrypt(long callbackId,String encryptedMessage);

    void onWalletSwitchEthereumChain(long callbackId, WalletAddEthereumChainObject chainObj);
}
