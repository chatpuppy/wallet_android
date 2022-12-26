package com.chatpuppy.app.di;

import android.content.Context;

import com.chatpuppy.app.C;
import com.chatpuppy.app.interact.WalletConnectInteract;
import com.chatpuppy.app.service.RealmManager;
import com.chatpuppy.app.walletconnect.AWWalletConnectClient;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;

@Module
@InstallIn(SingletonComponent.class)
public class ToolsModule
{

    @Singleton
    @Provides
    Gson provideGson()
    {
        return new Gson();
    }

    @Singleton
    @Provides
    OkHttpClient okHttpClient()
    {
        return new OkHttpClient.Builder()
                //.addInterceptor(new LogInterceptor())
                .connectTimeout(C.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(C.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(C.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
    }

    @Singleton
    @Provides
    RealmManager provideRealmManager()
    {
        return new RealmManager();
    }

    @Singleton
    @Provides
    AWWalletConnectClient provideAWWalletConnectClient(@ApplicationContext Context context, WalletConnectInteract walletConnectInteract)
    {
        return new AWWalletConnectClient(context, walletConnectInteract);
    }
}
