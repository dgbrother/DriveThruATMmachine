package com.example.jwho_.atmapp.firebase;

import android.content.ContentValues;
import android.util.Log;

import com.example.jwho_.atmapp.HttpConnection;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshedTocken = FirebaseInstanceId.getInstance().getToken();
        Log.d("hello", "Token: "+refreshedTocken);
        sendRegistrationToServer(refreshedTocken);
    }

    private void sendRegistrationToServer(final String token) {
        // Send token to Server
        HttpConnection httpConnection = new HttpConnection();
        ContentValues param = new ContentValues();
        param.put("newToken",token);
        httpConnection.requestToServer(param);
    }
}
