package com.chatpuppy.app.viewmodel;

import com.chatpuppy.app.service.AnalyticsServiceType;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MyDappsViewModel extends BaseViewModel
{
    @Inject
    MyDappsViewModel(AnalyticsServiceType analyticsService)
    {
        setAnalyticsService(analyticsService);
    }
}
