package com.chatpuppy.app.di;

import static com.chatpuppy.app.service.KeystoreAccountService.KEYSTORE_FOLDER;

import android.content.Context;

import com.chatpuppy.app.repository.CoinbasePayRepository;
import com.chatpuppy.app.repository.CoinbasePayRepositoryType;
import com.chatpuppy.app.repository.EthereumNetworkRepository;
import com.chatpuppy.app.repository.EthereumNetworkRepositoryType;
import com.chatpuppy.app.repository.OnRampRepository;
import com.chatpuppy.app.repository.OnRampRepositoryType;
import com.chatpuppy.app.repository.PreferenceRepositoryType;
import com.chatpuppy.app.repository.SharedPreferenceRepository;
import com.chatpuppy.app.repository.SwapRepository;
import com.chatpuppy.app.repository.SwapRepositoryType;
import com.chatpuppy.app.repository.TokenLocalSource;
import com.chatpuppy.app.repository.TokenRepository;
import com.chatpuppy.app.repository.TokenRepositoryType;
import com.chatpuppy.app.repository.TokensRealmSource;
import com.chatpuppy.app.repository.TransactionLocalSource;
import com.chatpuppy.app.repository.TransactionRepository;
import com.chatpuppy.app.repository.TransactionRepositoryType;
import com.chatpuppy.app.repository.TransactionsRealmCache;
import com.chatpuppy.app.repository.WalletDataRealmSource;
import com.chatpuppy.app.repository.WalletRepository;
import com.chatpuppy.app.repository.WalletRepositoryType;
import com.chatpuppy.app.service.AccountKeystoreService;
import com.chatpuppy.app.service.AlphaWalletService;
import com.chatpuppy.app.service.AnalyticsService;
import com.chatpuppy.app.service.AnalyticsServiceType;
import com.chatpuppy.app.service.AssetDefinitionService;
import com.chatpuppy.app.service.GasService;
import com.chatpuppy.app.service.IPFSService;
import com.chatpuppy.app.service.IPFSServiceType;
import com.chatpuppy.app.service.KeyService;
import com.chatpuppy.app.service.KeystoreAccountService;
import com.chatpuppy.app.service.NotificationService;
import com.chatpuppy.app.service.OpenSeaService;
import com.chatpuppy.app.service.RealmManager;
import com.chatpuppy.app.service.SwapService;
import com.chatpuppy.app.service.TickerService;
import com.chatpuppy.app.service.TokensService;
import com.chatpuppy.app.service.TransactionsNetworkClient;
import com.chatpuppy.app.service.TransactionsNetworkClientType;
import com.chatpuppy.app.service.TransactionsService;
import com.google.gson.Gson;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoriesModule
{
    @Singleton
    @Provides
    PreferenceRepositoryType providePreferenceRepository(@ApplicationContext Context context)
    {
        return new SharedPreferenceRepository(context);
    }

    @Singleton
    @Provides
    AccountKeystoreService provideAccountKeyStoreService(@ApplicationContext Context context, KeyService keyService)
    {
        File file = new File(context.getFilesDir(), KEYSTORE_FOLDER);
        return new KeystoreAccountService(file, context.getFilesDir(), keyService);
    }

    @Singleton
    @Provides
    TickerService provideTickerService(OkHttpClient httpClient, PreferenceRepositoryType sharedPrefs, TokenLocalSource localSource)
    {
        return new TickerService(httpClient, sharedPrefs, localSource);
    }

    @Singleton
    @Provides
    EthereumNetworkRepositoryType provideEthereumNetworkRepository(
            PreferenceRepositoryType preferenceRepository,
            @ApplicationContext Context context
    )
    {
        return new EthereumNetworkRepository(preferenceRepository, context);
    }

    @Singleton
    @Provides
    WalletRepositoryType provideWalletRepository(
            PreferenceRepositoryType preferenceRepositoryType,
            AccountKeystoreService accountKeystoreService,
            EthereumNetworkRepositoryType networkRepository,
            WalletDataRealmSource walletDataRealmSource,
            KeyService keyService)
    {
        return new WalletRepository(
                preferenceRepositoryType, accountKeystoreService, networkRepository, walletDataRealmSource, keyService);
    }

    @Singleton
    @Provides
    TransactionRepositoryType provideTransactionRepository(
            EthereumNetworkRepositoryType networkRepository,
            AccountKeystoreService accountKeystoreService,
            TransactionLocalSource inDiskCache,
            TransactionsService transactionsService)
    {
        return new TransactionRepository(
                networkRepository,
                accountKeystoreService,
                inDiskCache,
                transactionsService);
    }

    @Singleton
    @Provides
    OnRampRepositoryType provideOnRampRepository(@ApplicationContext Context context)
    {
        return new OnRampRepository(context);
    }

    @Singleton
    @Provides
    SwapRepositoryType provideSwapRepository(@ApplicationContext Context context)
    {
        return new SwapRepository(context);
    }

    @Singleton
    @Provides
    CoinbasePayRepositoryType provideCoinbasePayRepository()
    {
        return new CoinbasePayRepository();
    }

    @Singleton
    @Provides
    TransactionLocalSource provideTransactionInDiskCache(RealmManager realmManager)
    {
        return new TransactionsRealmCache(realmManager);
    }

    @Singleton
    @Provides
    TransactionsNetworkClientType provideBlockExplorerClient(
            OkHttpClient httpClient,
            Gson gson,
            RealmManager realmManager)
    {
        return new TransactionsNetworkClient(httpClient, gson, realmManager);
    }

    @Singleton
    @Provides
    TokenRepositoryType provideTokenRepository(
            EthereumNetworkRepositoryType ethereumNetworkRepository,
            TokenLocalSource tokenLocalSource,
            OkHttpClient httpClient,
            @ApplicationContext Context context,
            TickerService tickerService)
    {
        return new TokenRepository(
                ethereumNetworkRepository,
                tokenLocalSource,
                httpClient,
                context,
                tickerService);
    }

    @Singleton
    @Provides
    TokenLocalSource provideRealmTokenSource(RealmManager realmManager, EthereumNetworkRepositoryType ethereumNetworkRepository)
    {
        return new TokensRealmSource(realmManager, ethereumNetworkRepository);
    }

    @Singleton
    @Provides
    WalletDataRealmSource provideRealmWalletDataSource(RealmManager realmManager)
    {
        return new WalletDataRealmSource(realmManager);
    }

    @Singleton
    @Provides
    TokensService provideTokensServices(EthereumNetworkRepositoryType ethereumNetworkRepository,
                                        TokenRepositoryType tokenRepository,
                                        TickerService tickerService,
                                        OpenSeaService openseaService,
                                        AnalyticsServiceType analyticsService)
    {
        return new TokensService(ethereumNetworkRepository, tokenRepository, tickerService, openseaService, analyticsService);
    }

    @Singleton
    @Provides
    IPFSServiceType provideIPFSService(OkHttpClient client)
    {
        return new IPFSService(client);
    }

    @Singleton
    @Provides
    TransactionsService provideTransactionsServices(TokensService tokensService,
                                                    EthereumNetworkRepositoryType ethereumNetworkRepositoryType,
                                                    TransactionsNetworkClientType transactionsNetworkClientType,
                                                    TransactionLocalSource transactionLocalSource)
    {
        return new TransactionsService(tokensService, ethereumNetworkRepositoryType, transactionsNetworkClientType, transactionLocalSource);
    }

    @Singleton
    @Provides
    GasService provideGasService(EthereumNetworkRepositoryType ethereumNetworkRepository, OkHttpClient client, RealmManager realmManager)
    {
        return new GasService(ethereumNetworkRepository, client, realmManager);
    }

    @Singleton
    @Provides
    OpenSeaService provideOpenseaService()
    {
        return new OpenSeaService();
    }

    @Singleton
    @Provides
    SwapService provideSwapService()
    {
        return new SwapService();
    }

    @Singleton
    @Provides
    AlphaWalletService provideFeemasterService(OkHttpClient okHttpClient, TransactionRepositoryType transactionRepository, Gson gson)
    {
        return new AlphaWalletService(okHttpClient, transactionRepository, gson);
    }

    @Singleton
    @Provides
    NotificationService provideNotificationService(@ApplicationContext Context ctx)
    {
        return new NotificationService(ctx);
    }

    @Singleton
    @Provides
    AssetDefinitionService providingAssetDefinitionServices(IPFSServiceType ipfsService, @ApplicationContext Context ctx, NotificationService notificationService, RealmManager realmManager,
                                                            TokensService tokensService, TokenLocalSource tls,
                                                            AlphaWalletService alphaService)
    {
        return new AssetDefinitionService(ipfsService, ctx, notificationService, realmManager, tokensService, tls, alphaService);
    }

    @Singleton
    @Provides
    KeyService provideKeyService(@ApplicationContext Context ctx, AnalyticsServiceType analyticsService)
    {
        return new KeyService(ctx, analyticsService);
    }

    @Singleton
    @Provides
    AnalyticsServiceType provideAnalyticsService(@ApplicationContext Context ctx)
    {
        return new AnalyticsService(ctx);
    }
}
