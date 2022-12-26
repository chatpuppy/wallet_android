package com.chatpuppy.app.web3;


import com.chatpuppy.token.entity.EthereumTypedMessage;

public interface OnSignTypedMessageListener {
    void onSignTypedMessage(EthereumTypedMessage message);
}
