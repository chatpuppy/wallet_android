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
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


//            mNotificationManager.cancel(notificationId);
            mNotificationManager.cancelAll();
            if (badgeCount == 0) {
                return;
            }
//            notificationId++;

            Notification.Builder builder = new Notification.Builder(getApplicationContext())
                    .setContentTitle(noticeTitle != null && noticeTitle.equals("") ? "NewMessages" : noticeTitle)
                    .setContentText(noticeMsg.equals("") ? "You've received " + badgeCount + " messages." : noticeMsg)
                    .setSmallIcon(R.drawable.ic_logo);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setupNotificationChannel();

                builder.setChannelId(NOTIFICATION_CHANNEL);
            }
            builder.setContentIntent(pendingIntent);

            Notification notification = builder.build();
            ShortcutBadger.applyNotification(getApplicationContext(), notification, badgeCount);
            mNotificationManager.notify(notificationId, notification);

        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void setupNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, "ShortcutBadger Sample",
                NotificationManager.IMPORTANCE_DEFAULT);

        mNotificationManager.createNotificationChannel(channel);
    }
}
