package com.chatpuppy.app.web3;

import com.chatpuppy.token.entity.EthereumMessage;

public interface OnSignPersonalMessageListener {
    void onSignPersonalMessage(EthereumMessage message);
}
