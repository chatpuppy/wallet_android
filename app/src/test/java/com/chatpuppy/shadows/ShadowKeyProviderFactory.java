package com.chatpuppy.shadows;

import com.chatpuppy.app.di.mock.KeyProviderMockImpl;
import com.chatpuppy.app.repository.KeyProvider;
import com.chatpuppy.app.repository.KeyProviderFactory;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(KeyProviderFactory.class)
public class ShadowKeyProviderFactory
{
    @Implementation
    public static KeyProvider get() {
        return new KeyProviderMockImpl();
    }
}
