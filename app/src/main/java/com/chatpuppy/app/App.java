package com.chatpuppy.app;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;
import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import com.chatpuppy.app.ui.HomeActivity;
import com.chatpuppy.app.util.FileUtils;
import com.chatpuppy.app.util.ReleaseTree;
import com.chatpuppy.app.walletconnect.AWWalletConnectClient;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.sdk.WebView;

import java.util.HashMap;
import java.util.Stack;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import io.reactivex.plugins.RxJavaPlugins;
import io.realm.Realm;
import timber.log.Timber;

@HiltAndroidApp
public class App extends Application
{

    @Inject
    AWWalletConnectClient awWalletConnectClient;

    private static App mInstance;
    private static final String TAG = "X5Webview";
    private final Stack<Activity> activityStack = new Stack<>();

    public static App getInstance()
    {
        return mInstance;
    }

    public Activity getTopActivity()
    {
        return activityStack.peek();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        Realm.init(this);

        // Chatpuppy: Initialize X5 webview engine instead of default android webview
//        initX5Webview();



        if (BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
        }
        else
        {
            Timber.plant(new ReleaseTree());
        }

        int defaultTheme = PreferenceManager.getDefaultSharedPreferences(this)
                .getInt("theme", C.THEME_DARK);

        if (defaultTheme == C.THEME_LIGHT)
        {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
        }
        else if (defaultTheme == C.THEME_DARK)
        {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        }
        else
        {
            UiModeManager uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
            int mode = uiModeManager.getNightMode();
            if (mode == UiModeManager.MODE_NIGHT_YES)
            {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
            }
            else if (mode == UiModeManager.MODE_NIGHT_NO)
            {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
            }
        }

        RxJavaPlugins.setErrorHandler(Timber::e);

        try
        {
            awWalletConnectClient.init(this);
        }
        catch (Exception e)
        {
            Timber.tag("WalletConnect").e(e);
        }

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks()
        {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState)
            {
            }

            @Override
            public void onActivityDestroyed(Activity activity)
            {
            }

            @Override
            public void onActivityStarted(Activity activity)
            {
            }

            @Override
            public void onActivityResumed(Activity activity)
            {
                activityStack.push(activity);
            }

            @Override
            public void onActivityPaused(Activity activity)
            {
                pop();
            }

            @Override
            public void onActivityStopped(Activity activity)
            {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState)
            {
            }
        });

        QbSdk.initX5Environment(this , new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {

            }
        });
    }

    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        if (awWalletConnectClient != null)
        {
            awWalletConnectClient.shutdown();
        }
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        activityStack.clear();
        if (awWalletConnectClient != null)
        {
            awWalletConnectClient.shutdown();
        }
    }

    private void pop()
    {
        activityStack.pop();
    }



    // Chatpuppy: using X5 webview engine
    private void initX5Webview () {
        if (!startX5WebProcessPreinitService()) {
            return;
        }

        QbSdk.setDownloadWithoutWifi(true);

//        QbSdk.setCoreMinVersion(QbSdk.CORE_VER_ENABLE_202112);

        QbSdk.setTbsListener(new TbsListener() {
            /**
             * @param stateCode ?????????????????????????????????{@link com.tencent.smtt.sdk.TbsCommonCode}
             */
            @Override
            public void onDownloadFinish(int stateCode) {
                Log.i(TAG, "onDownloadFinished: " + stateCode);
            }

            /**
             * @param stateCode ?????????????????????????????????{@link com.tencent.smtt.sdk.TbsCommonCode}
             */
            @Override
            public void onInstallFinish(int stateCode) {
                Log.i(TAG, "onInstallFinished: " + stateCode);
            }

            /**
             * ???????????????????????????????????????????????????????????????????????????????????????
             * @param progress 0 - 100
             */
            @Override
            public void onDownloadProgress(int progress) {
                Log.i(TAG, "Core Downloading: " + progress);
            }
        });

        QbSdk.installLocalTbsCore(getApplicationContext(),46141,getApplicationContext().getFilesDir().getPath() +"/x5.tbs.org.apk");
        /* ???????????????X5???????????????????????????????????????????????????????????????x5??????????????????????????????????????? */
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                // ????????????????????????????????????????????????????????????????????????
                Log.i(TAG, "onCoreInitFinished: true");
            }

            /**
             * ??????????????????
             * ??????X5?????????????????????????????????wifi??????????????????????????????????????????????????????????????????false???????????????????????????????????????
             * ???????????????????????????24????????????????????????????????????????????????24?????????????????????
             * ???????????????????????? WebView ?????? debugtbs.qq.com -> ?????????????????? ??????
             * @param isX5 ????????????X5??????
             */
            @Override
            public void onViewInitFinished(boolean isX5) {
                Log.i(TAG, "onViewInitFinished: " + isX5);
                // hint: you can use QbSdk.getX5CoreLoadHelp(context) anytime to get help.
            }
        });
    }
    private boolean startX5WebProcessPreinitService() {
        String currentProcessName = QbSdk.getCurrentProcessName(this);
        // ?????????????????????????????????????????????????????????????????????????????????WebView???crash???X5?????????ANR
        WebView.setDataDirectorySuffix(QbSdk.getCurrentProcessName(this));
        if (currentProcessName.equals(this.getPackageName())) {
            this.startService(new Intent(this, com.chatpuppy.app.util.X5ProcessInitService.class));
            return true;
        }
        return false;
    }

}
