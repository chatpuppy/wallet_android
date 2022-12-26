package com.chatpuppy.app.router;

import android.content.Context;
import android.content.Intent;

import com.chatpuppy.app.C;
import com.chatpuppy.app.ui.MyAddressActivity;
import com.chatpuppy.app.entity.tokens.Token;
import com.chatpuppy.app.entity.Wallet;

public class MyAddressRouter {

    public void open(Context context, Wallet wallet) {
        Intent intent = new Intent(context, MyAddressActivity.class);
        intent.putExtra(C.Key.WALLET, wallet);
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(intent);
    }

    public void open(Context context, Wallet wallet, Token token) {
        Intent intent = new Intent(context, MyAddressActivity.class);
        intent.putExtra(C.Key.WALLET, wallet);
        intent.putExtra(C.EXTRA_CHAIN_ID, token.tokenInfo.chainId);
        intent.putExtra(C.EXTRA_ADDRESS, token.getAddress());
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(intent);
    }
}
