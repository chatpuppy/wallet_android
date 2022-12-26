package com.chatpuppy.app.ui.widget;

import java.io.Serializable;

import com.chatpuppy.app.entity.DApp;

public interface OnDappClickListener extends Serializable {
    void onDappClick(DApp dapp);
}
