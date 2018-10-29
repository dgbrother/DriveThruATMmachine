package com.example.jwho_.atmapp.firebase;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

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
            String msgFromServer = remoteMessage.getData().get("msgFromServer");

            String TAG = "";
            try {
                JSONObject jsonObject = new JSONObject(msgFromServer);
                if(jsonObject.has("data"))
                    TAG = "data";
                if(jsonObject.has("result"))
                    TAG = "result";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            intent.putExtra(TAG, msgFromServer);
            broadcastManager.sendBroadcast(intent);
        }
    }
}
