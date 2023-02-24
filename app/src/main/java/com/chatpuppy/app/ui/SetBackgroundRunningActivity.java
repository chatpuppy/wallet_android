package com.chatpuppy.app.ui;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.chatpuppy.app.C;
import com.chatpuppy.app.R;
import com.chatpuppy.app.util.KeepCompactUtil;
import com.chatpuppy.app.viewmodel.SelectThemeViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SetBackgroundRunningActivity extends BaseActivity {
    private SelectThemeViewModel viewModel;
    private static final Intent[] POWERMANAGER_INTENTS = {new Intent().setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")), new Intent().setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity")), new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity")), new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity")), new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity")), new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity")), new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity")), new Intent().setComponent(new ComponentName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity")), new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity")), new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager")), new Intent().setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity")), new Intent().setComponent(new ComponentName("com.samsung.android.lool", "com.samsung.android.sm.ui.battery.BatteryActivity")), new Intent().setComponent(new ComponentName("com.htc.pitroad", "com.htc.pitroad.landingpage.activity.LandingPageActivity")), new Intent().setComponent(new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.MainActivity")), new Intent().setComponent(new ComponentName("com.transsion.phonemanager", "com.itel.autobootmanager.activity.AutoBootMgrActivity"))};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_running);
        initViewModel();
        initViews();
        toolbar();
        setTitle(getString(R.string.title_set_bg_running));
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(SelectThemeViewModel.class);
    }


    private void setBrandText(TextView backgroundStepText, TextView autoStartStepText, String brand) {

        if (brand.equalsIgnoreCase("xiaomi")) {
            backgroundStepText.setText(getString(R.string.allow_background_activities_step_xiaomi));
            autoStartStepText.setText(getString(R.string.allow_auto_start_step_xiaomi));
        } else if (brand.equalsIgnoreCase("Honor")) {
            backgroundStepText.setText(getString(R.string.allow_background_activities_step_honor));
            autoStartStepText.setText(getString(R.string.allow_auto_start_step_honor));
        } else if (brand.equalsIgnoreCase("Huawei")) {
            backgroundStepText.setText(getString(R.string.allow_background_activities_step_huawei));
            autoStartStepText.setText(getString(R.string.allow_auto_start_step_huawei));
        } else if (brand.equalsIgnoreCase("oppo")) {
            backgroundStepText.setText(getString(R.string.allow_background_activities_step_oppo));
            autoStartStepText.setText(getString(R.string.allow_auto_start_step_oppo));
        } else if (brand.equalsIgnoreCase("vivo")) {
            backgroundStepText.setText(getString(R.string.allow_background_activities_step_vivo));
            autoStartStepText.setText(getString(R.string.allow_auto_start_step_vivo));
        } else {
            backgroundStepText.setText(getString(R.string.allow_background_activities_step_others));
            autoStartStepText.setText(getString(R.string.allow_auto_start_step_others));
        }

    }

    @SuppressLint("ResourceType")
    private void enableAutoStart() {
        if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
            new MaterialAlertDialogBuilder(this).setTitle("Enable AutoStart").setMessage("Please allow AppName to always run in the background,else our services can't be accessed.").setPositiveButton(R.string.ok, (dialogInterface, i) -> {

                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                startActivity(intent);
            }).show();
        } else if (Build.BRAND.equalsIgnoreCase("Honor")) {
            new MaterialAlertDialogBuilder(this).setTitle("Enable AutoStart").setMessage("Please allow AppName to always run in the background,else our services can't be accessed.").setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
                startActivity(intent);
            }).show();
        } else if (Build.BRAND.equalsIgnoreCase("Huawei")) {
            new MaterialAlertDialogBuilder(this).setTitle("Enable AutoStart").setMessage("Please allow AppName to always run in the background,else our services can't be accessed.").setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"));
                startActivity(intent);
            }).show();
        } else if (Build.MANUFACTURER.equalsIgnoreCase("oppo")) {
            new MaterialAlertDialogBuilder(this).setTitle("Enable AutoStart").setMessage("Please allow AppName to always run in the background,else our services can't be accessed.").setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                try {
                    Intent intent = new Intent();
                    intent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity");
                    startActivity(intent);
                } catch (Exception e) {
                    try {
                        Intent intent = new Intent();
                        intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity");
                        startActivity(intent);
                    } catch (Exception ex) {
                        try {
                            Intent intent = new Intent();
                            intent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity");
                            startActivity(intent);
                        } catch (Exception exx) {

                        }
                    }
                }
            }).show();
        } else if (Build.MANUFACTURER.contains("vivo")) {
            new MaterialAlertDialogBuilder(this).setTitle("Enable AutoStart").setMessage("Please allow AppName to always run in the background,else our services can't be accessed.").setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                try {

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);


                    intent.setComponent(new ComponentName("com.vivo.applicationbehaviorengine", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity"));
//                            intent.setComponent(ComponentName.unflattenFromString("com.iqoo.powersaving/.PowerSavingManagerActivity"));
                    startActivity(intent);

                } catch (Exception e) {
                    try {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                        startActivity(intent);
                    } catch (Exception ex) {
                        try {
                            Intent intent = new Intent();
                            intent.setClassName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager");
                            startActivity(intent);
                        } catch (Exception exx) {
                            ex.printStackTrace();
                        }
                    }
                }
            }).show();
        } else {
            for (Intent intent : POWERMANAGER_INTENTS) {
                if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                    startActivity(intent);
                    break;
                }
            }
        }
    }


    private void initViews() {
        TextView brandText = findViewById(R.id.text_device_brand);
        TextView changeBtn = findViewById(R.id.label_change_brand);
        TextView background_step = findViewById(R.id.allow_background_activities_step);
        TextView auto_start_step = findViewById(R.id.allow_auto_start_step);

        Button power_button = findViewById(R.id.btn_power_setting);
        Button auto_start_button = findViewById(R.id.btn_auto_start_setting);

        brandText.setText(Build.BRAND);

        setBrandText(background_step, auto_start_step, Build.BRAND);

        changeBtn.setOnClickListener(item -> {
            PopupMenu popupMenu = new PopupMenu(this, item);
            popupMenu.inflate(R.menu.menu_devices_brand);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.item_menu_brand_vivo:
                        brandText.setText(getString(R.string.menu_brand_vivo));
                        setBrandText(background_step, auto_start_step, "vivo");
                        break;
                    case R.id.item_menu_brand_oppo:
                        brandText.setText(getString(R.string.menu_brand_oppo));
                        setBrandText(background_step, auto_start_step, "oppo");
                        break;
                    case R.id.item_menu_brand_xiaomi:
                        brandText.setText(getString(R.string.menu_brand_xiaomi));
                        setBrandText(background_step, auto_start_step, "xiaomi");
                        break;
                    case R.id.item_menu_brand_huawei:
                        brandText.setText(getString(R.string.menu_brand_huawei));
                        setBrandText(background_step, auto_start_step, "huawei");
                        break;
                    case R.id.item_menu_brand_honor:
                        brandText.setText(getString(R.string.menu_brand_honor));
                        setBrandText(background_step, auto_start_step, "honor");
                        break;
                    case R.id.item_menu_brand_others:
                        brandText.setText(getString(R.string.menu_brand_others));
                        setBrandText(background_step, auto_start_step, "others");
                        break;
                }
                return false;

            });
            popupMenu.show();
        });

        power_button.setOnClickListener(v -> {
            KeepCompactUtil.noSleepSet(this);


        });
        auto_start_button.setOnClickListener(v -> {
            KeepCompactUtil.daemonSet(this);
        });

    }
}
