package com.chatpuppy.app.ui.widget;

import android.view.View;

import com.chatpuppy.token.entity.MagicLinkData;

/**
 * Created by James on 21/02/2018.
 */

public interface OnSalesOrderClickListener {
    void onOrderClick (View view, MagicLinkData range);
}
