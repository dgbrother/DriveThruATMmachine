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
        Log.d("helloTest", "From: "+remoteMessage.getFrom());
        if(remoteMessage.getData().size() > 0) {
            Log.d("helloTest", "Message data payload: "+remoteMessage.getData());
            Intent intent = new Intent("GCMData");
            String msgFromServer = remoteMessage.getData().get("msgFromServer");

            try {
                JSONObject jsonObject = new JSONObject(msgFromServer);
                /*
                    [ action ]
                        - carEntry  : 차량 진입. "AtmMain Activity" 에서 receive.
                        - nfcTag    : NFC 카드 TAG. 예약업무 불러오기, 예약업무 실행하기 에서 receive.
                        - error     : 오류. [ errorType ] : "에러 메시지" 형태로 전달.
                 */
                String action = jsonObject.getString("action");
                intent.putExtra(action, jsonObject.toString());
                broadcastManager.sendBroadcast(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
