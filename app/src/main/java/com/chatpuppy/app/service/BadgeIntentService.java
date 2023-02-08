package com.chatpuppy.app.service;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.chatpuppy.app.R;
import com.chatpuppy.app.ui.ChatPuppyFragment;
import com.chatpuppy.app.ui.HomeActivity;

import me.leolin.shortcutbadger.ShortcutBadger;

public class BadgeIntentService extends IntentService {

    private static final String NOTIFICATION_CHANNEL = "com.chatpuppy.app";

    private int notificationId = 850708;

    public BadgeIntentService() {
        super("BadgeIntentService");
    }

    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            int badgeCount = intent.getIntExtra("badgeCount", 0);
            String noticeMsg = intent.getStringExtra("noticeMsg");
            String noticeTitle = intent.getStringExtra("noticeTitle");
            Intent resultIntent = new Intent(this, HomeActivity.class);
//            PendingIntent contentIntent = PendingIntent.getActivities(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_MUTABLE : PendingIntent.FLAG_UPDATE_CURRENT);


//            mNotificationManager.cancel(notificationId);
            mNotificationManager.cancelAll();
            if (badgeCount == 0) {
                return;
            }
//            notificationId++;

            setupNotificationChannel();
            Notification.Builder builder = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder = new Notification.Builder(getApplicationContext(), NOTIFICATION_CHANNEL)
                        .setContentTitle(noticeTitle != null && noticeTitle.equals("") ? "NewMessages" : noticeTitle)
                        .setContentText(noticeMsg.equals("") ? "You've received " + badgeCount + " messages." : noticeMsg)
                        .setSmallIcon(R.drawable.ic_logo);
                builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();
                startForeground(1, notification);
                ShortcutBadger.applyNotification(getApplicationContext(), notification, badgeCount);
                mNotificationManager.notify(notificationId, notification);
            } else {
                builder = new Notification.Builder(getApplicationContext())
                        .setContentTitle(noticeTitle != null && noticeTitle.equals("") ? "NewMessages" : noticeTitle)
                        .setContentText(noticeMsg.equals("") ? "You've received " + badgeCount + " messages." : noticeMsg)
                        .setSmallIcon(R.drawable.ic_logo);
                builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();
                ShortcutBadger.applyNotification(getApplicationContext(), notification, badgeCount);
                mNotificationManager.notify(notificationId, notification);
            }


//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                NotificationChannel channel = null;
//                channel = new NotificationChannel(CHANNEL_ID_STRING, getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
//                notificationManager.createNotificationChannel(channel);
//                Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_STRING).build();


        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void setupNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH);

        mNotificationManager.createNotificationChannel(channel);
    }
}
