package com.example.jwho_.atmapp.firebase;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseHandlingService extends FirebaseMessagingService {
    private LocalBroadcastManager broadcastManager;

    public MyFirebaseHandlingService() {
    }

    @Override
    public void onCreate() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("hello", "From: "+remoteMessage.getFrom());
        if(remoteMessage.getData().size() > 0) {
            Log.d("hello", "Message data payload: "+remoteMessage.getData());
            Intent intent = new Intent("GCMData");
            String carNumber = remoteMessage.getData().get("carNumber");
            intent.putExtra("carNumber", carNumber);
            broadcastManager.sendBroadcast(intent);
        }

        if (remoteMessage.getNotification() != null) {
            Log.d("hello", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
}
