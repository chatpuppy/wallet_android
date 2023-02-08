package com.chatpuppy.app.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;

public class KeepCompactUtil {

    private static final Intent[] AUTO_START_INTENTS = {
            // 小米
            new Intent().setComponent(
                    new ComponentName(
                            "com.miui.securitycenter",
                            "com.miui.permcenter.autostart.AutoStartManagementActivity"
                    )
            ),
            // 华为
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity")
            ),
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.huawei.systemmanager/.appcontrol.activity.StartupAppControlActivity")
            ),

            // 魅族
            new Intent().setComponent(ComponentName.unflattenFromString("com.meizu.safe/.SecurityCenterActivity")),

            // 三星
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm_cn",
                            "com.samsung.android.sm.autorun.ui.AutoRunActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm_cn",
                            "com.samsung.android.sm.ui.ram.AutoRunActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm_cn",
                            "com.samsung.android.sm.ui.appmanagement.AppManagementActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm",
                            "com.samsung.android.sm.autorun.ui.AutoRunActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm",
                            "com.samsung.android.sm.ui.ram.AutoRunActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm",
                            "com.samsung.android.sm.ui.appmanagement.AppManagementActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm_cn",
                            "com.samsung.android.sm.ui.cstyleboard.SmartManagerDashBoardActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm",
                            "com.samsung.android.sm.ui.cstyleboard.SmartManagerDashBoardActivity"
                    )
            ),
            new Intent().setComponent(
                    ComponentName.unflattenFromString(
                            "com.samsung.android.sm_cn/.app.dashboard.SmartManagerDashBoardActivity"
                    )
            ),
            new Intent().setComponent(
                    ComponentName.unflattenFromString(
                            "com.samsung.android.sm/.app.dashboard.SmartManagerDashBoardActivity"
                    )
            ),

            // oppo
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.coloros.safecenter/.startupapp.StartupAppListActivity")
            ),
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.coloros.safecenter/.permission.startupapp.StartupAppListActivity")
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.coloros.safecenter",
                            "com.coloros.privacypermissionsentry.PermissionTopActivity"
                    )
            ),
            new Intent().setComponent(
                    ComponentName.unflattenFromString("com.oppo.safe/.permission.startup.StartupAppListActivity")
            ),

            // vivo
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.vivo.permissionmanager/.activity.BgStartUpManagerActivity")
            ),
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.iqoo.secure/.phoneoptimize.BgStartUpManager")
            ),
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.vivo.permissionmanager/.activity.PurviewTabActivity")
            ),
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.iqoo.secure/.ui.phoneoptimize.SoftwareManagerActivity")
            ),

            // 一加
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.oneplus.security/.chainlaunch.view.ChainLaunchAppListActivity")
            ),

            // 乐视
            new Intent().setComponent(
                    ComponentName.unflattenFromString("com.letv.android.letvsafe/.AutobootManageActivity")
            ),

            // HTC
            new Intent().setComponent(
                    ComponentName.unflattenFromString("com.htc.pitroad/.landingpage.activity.LandingPageActivity")
            )
    };

    private static final Intent[] BATTERY_INTENTS = {
            // 小米
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.miui.powerkeeper/.ui.HiddenAppsContainerManagementActivity")
            ),

            // 华为
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.huawei.systemmanager/.power.ui.HwPowerManagerActivity")
            ),

            // 魅族
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.meizu.safe/.SecurityCenterActivity")
            ),

            // 三星
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm_cn",
                            "com.samsung.android.sm.ui.battery.AppSleepListActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm_cn",
                            "com.samsung.android.sm.ui.battery.BatteryActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm",
                            "com.samsung.android.sm.ui.battery.AppSleepListActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm",
                            "com.samsung.android.sm.ui.battery.BatteryActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.lool",
                            "com.samsung.android.sm.battery.ui.BatteryActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.lool",
                            "com.samsung.android.sm.ui.battery.BatteryActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm",
                            "com.samsung.android.sm.ui.battery.BatteryActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.samsung.android.sm_cn",
                            "com.samsung.android.sm.ui.cstyleboard.SmartManagerDashBoardActivity"
                    )
            ),

            // oppo
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.coloros.safecenter/.appfrozen.activity.AppFrozenSettingsActivity")
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.coloros.oppoguardelf",
                            "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.coloros.oppoguardelf",
                            "com.coloros.powermanager.fuelgaue.PowerSaverModeActivity"
                    )
            ),
            new Intent().setComponent(
                    new ComponentName(
                            "com.coloros.oppoguardelf",
                            "com.coloros.powermanager.fuelgaue.PowerConsumptionActivity"
                    )
            ),
            new Intent().setComponent(
                    ComponentName
                            .unflattenFromString("com.oppo.safe/.SecureSafeMainActivity")
            ),

            // vivo
            new Intent().setComponent(
                    new ComponentName(
                            "com.vivo.abe",
                            "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity"
                    )
            ),
            new Intent().setComponent(ComponentName.unflattenFromString("com.iqoo.powersaving/.PowerSavingManagerActivity"))
    };

    // 自启动
    public static boolean daemonSet(Activity activity) {
        for (Intent intent : AUTO_START_INTENTS) {
            if (activity.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    activity.startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        return false;
    }

    // 防睡眠
    public static boolean noSleepSet(Activity activity) {
        for (Intent intent : BATTERY_INTENTS) {
            if (activity.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    activity.startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        return false;
    }
}
