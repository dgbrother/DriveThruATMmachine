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
        Log.d("qwer1234", "From: "+remoteMessage.getFrom());
        if(remoteMessage.getData().size() > 0) {
            Log.d("qwer1234", "Message data payload: "+remoteMessage.getData());
            Intent intent = new Intent("GCMData");
            String msgFromServer = remoteMessage.getData().get("msgFromServer");

            intent.putExtra("carNumber", msgFromServer);
            broadcastManager.sendBroadcast(intent);
        }

        if (remoteMessage.getNotification() != null) {
            Log.d("hello", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
}
