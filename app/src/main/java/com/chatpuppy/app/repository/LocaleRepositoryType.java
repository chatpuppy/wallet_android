package com.chatpuppy.app.repository;

import android.content.Context;

import java.util.ArrayList;

import com.chatpuppy.app.entity.LocaleItem;

public interface LocaleRepositoryType {
    String getUserPreferenceLocale();
    void setUserPreferenceLocale(String locale);

    void setLocale(Context context, String locale);

    ArrayList<LocaleItem> getLocaleList(Context context);

    String getActiveLocale();

    boolean isLocalePresent(String locale);
}
