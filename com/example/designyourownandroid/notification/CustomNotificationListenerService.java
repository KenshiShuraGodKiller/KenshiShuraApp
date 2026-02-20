package com.example.designyourownandroid.notification;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class CustomNotificationListenerService extends NotificationListenerService {
    
    private static final String ACTION_NOTIFICATION_POSTED = "com.example.designyourownandroid.NOTIFICATION_POSTED";
    private static final String ACTION_NOTIFICATION_REMOVED = "com.example.designyourownandroid.NOTIFICATION_REMOVED";
    private static final String EXTRA_NOTIFICATION = "notification_data";
    
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        
        NotificationData notificationData = new NotificationData();
        notificationData.setKey(sbn.getKey());
        notificationData.setPackageName(sbn.getPackageName());
        notificationData.setPostTime(sbn.getPostTime());
        
        Notification notification = sbn.getNotification();
        if (notification != null) {
            if (notification.extras != null) {
                notificationData.setTitle(notification.extras.getString(Notification.EXTRA_TITLE));
                notificationData.setText(notification.extras.getString(Notification.EXTRA_TEXT));
                notificationData.setSubText(notification.extras.getString(Notification.EXTRA_SUB_TEXT));
            }
        }
        
        Intent intent = new Intent(ACTION_NOTIFICATION_POSTED);
        intent.putExtra(EXTRA_NOTIFICATION, notificationData);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        
        Intent intent = new Intent(ACTION_NOTIFICATION_REMOVED);
        intent.putExtra("notification_key", sbn.getKey());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}