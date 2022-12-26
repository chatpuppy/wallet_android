package com.chatpuppy.shadows;

import android.content.Context;

import com.chatpuppy.app.entity.AnalyticsProperties;
import com.chatpuppy.app.service.AnalyticsServiceType;
import com.chatpuppy.app.service.KeyService;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(KeyService.class)
public class ShadowKeyService
{
    @Implementation
    public void __constructor__(Context ctx, AnalyticsServiceType<AnalyticsProperties> analyticsService) {

    }
}
