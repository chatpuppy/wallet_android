package com.chatpuppy.app.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.chatpuppy.app.C;
import com.chatpuppy.app.R;
import com.chatpuppy.app.analytics.Analytics;
import com.chatpuppy.app.entity.MediaLinks;
import com.chatpuppy.app.viewmodel.SettingsCopyrightViewModel;
import com.chatpuppy.app.viewmodel.SupportSettingsViewModel;
import com.chatpuppy.app.widget.SettingsItemView;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class SettingCopyrightActivity extends BaseActivity {
    private SettingsCopyrightViewModel viewModel;
    private TextView chatpuppyLink;
    private TextView alphawalletLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_settings_copyright);

        toolbar();

        setTitle(getString(R.string.title_copyright));

        initViewModel();

        initializeSettings();

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.track(Analytics.Navigation.SETTINGS_SUPPORT);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(SettingsCopyrightViewModel.class);
    }

    private void initializeSettings() {
        chatpuppyLink = findViewById(R.id.text_chatpuppy_url);
        alphawalletLink = findViewById(R.id.text_alphawallet_url);

        chatpuppyLink.setOnClickListener(v -> onChatpuppyUrlClicked());
        alphawalletLink.setOnClickListener(v -> onAlphawalletUrlClicked());
    }


    private void onChatpuppyUrlClicked() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(MediaLinks.AWALLET_CHATPUPPY));

        try {
            viewModel.track(Analytics.Action.SUPPORT_COPYRIGHT);
            startActivity(intent);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void onAlphawalletUrlClicked() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(MediaLinks.AWALLET_ALPHAWALLET));


        try {
            viewModel.track(Analytics.Action.SUPPORT_COPYRIGHT);
            startActivity(intent);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

}
