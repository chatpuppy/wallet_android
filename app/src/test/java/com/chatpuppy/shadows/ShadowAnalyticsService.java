package com.chatpuppy.shadows;

import android.content.Context;

import com.chatpuppy.app.service.AnalyticsService;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(AnalyticsService.class)
public class ShadowAnalyticsService
{
    @Implementation
    public void __constructor__(Context context) {
    }

    @Implementation
    public void identify(String uuid) {
    }
}
