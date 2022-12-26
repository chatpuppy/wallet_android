package com.chatpuppy.app.ui.widget;


import android.util.Pair;

import com.chatpuppy.app.entity.nftassets.NFTAsset;

import java.math.BigInteger;

public interface OnAssetClickListener
{
    void onAssetClicked(Pair<BigInteger, NFTAsset> item);
}
