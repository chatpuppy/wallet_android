package com.chatpuppy.app.widget;

//import android.webkit.WebBackForwardList;
import com.tencent.smtt.sdk.WebBackForwardList;

public interface AddressBarListener
{
    boolean onLoad(String urlText);

    void onClear();

    com.tencent.smtt.sdk.WebBackForwardList loadNext();

    com.tencent.smtt.sdk.WebBackForwardList loadPrevious();

    WebBackForwardList onHomePagePressed();
}
