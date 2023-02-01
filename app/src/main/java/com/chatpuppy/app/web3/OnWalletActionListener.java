package com.chatpuppy.app.web3;

import android.security.keystore.UserNotAuthenticatedException;

import com.chatpuppy.app.entity.cryptokeys.KeyServiceException;
import com.chatpuppy.app.web3.entity.NoticeMessage;
import com.chatpuppy.app.web3.entity.WalletAddEthereumChainObject;

import org.json.JSONException;

/**
 * Created by JB on 15/01/2022.
 */
public interface OnWalletActionListener {
    void onRequestAccounts(long callbackId);

    // Chatpuppy
    void onEthGetEncryptionPublickey(long callbackId);

    // Chatpuppy
    void onEthDecrypt(long callbackId, String encryptedMessage) throws KeyServiceException, UserNotAuthenticatedException, JSONException;

    // Chatpuppy
    void onNoticeMsg(long callbackId, NoticeMessage noticeMessage);

    void onWalletSwitchEthereumChain(long callbackId, WalletAddEthereumChainObject chainObj);
}
