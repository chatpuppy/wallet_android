package com.alphawallet.app.entity;

import android.security.keystore.UserNotAuthenticatedException;

import com.alphawallet.app.entity.cryptokeys.KeyServiceException;

import org.json.JSONException;

/**
 * Created by James on 21/07/2019.
 * Stormbird in Sydney
 */
public interface SignAuthenticationCallback
{
    void gotAuthorisation(boolean gotAuth);
    default void createdKey(String keyAddress) { }

    void cancelAuthentication();
}
