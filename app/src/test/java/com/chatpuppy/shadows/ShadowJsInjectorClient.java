package com.chatpuppy.shadows;

import android.content.Context;

import com.chatpuppy.app.web3.JsInjectorClient;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(JsInjectorClient.class)
public class ShadowJsInjectorClient
{
    @Implementation
    protected void __constructor__(Context context) {
    }
}
