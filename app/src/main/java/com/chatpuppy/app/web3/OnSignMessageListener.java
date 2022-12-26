package com.chatpuppy.app.web3;

import com.chatpuppy.token.entity.EthereumMessage;

public interface OnSignMessageListener {
    void onSignMessage(EthereumMessage message);
}
