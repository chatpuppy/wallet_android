package com.chatpuppy.app.walletconnect.entity;

import com.chatpuppy.app.entity.walletconnect.WCRequest;

/**
 * Created by JB on 6/10/2021.
 */
public interface WalletConnectCallback
{
    boolean receiveRequest(WCRequest request);
}
