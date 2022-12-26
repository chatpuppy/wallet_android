package com.chatpuppy.app.ui.widget;

import com.chatpuppy.app.entity.tokens.Token;

public interface OnTokenManageClickListener
{
    void onTokenClick(Token token, int position, boolean isChecked);
}
