package com.chatpuppy.shadows;


import com.chatpuppy.app.di.mock.KeyProviderMockNonProductionImpl;
import com.chatpuppy.app.repository.KeyProvider;
import com.chatpuppy.app.repository.KeyProviderFactory;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(KeyProviderFactory.class)
public class ShadowKeyProviderFactoryNonProduction
{
    @Implementation
    public static KeyProvider get() {
        return new KeyProviderMockNonProductionImpl();
    }
}
