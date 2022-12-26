package com.chatpuppy.app.repository;

import com.chatpuppy.app.entity.CurrencyItem;

import java.util.ArrayList;

public interface CurrencyRepositoryType {
    String getDefaultCurrency();

    void setDefaultCurrency(String currency);

    ArrayList<CurrencyItem> getCurrencyList();
}
