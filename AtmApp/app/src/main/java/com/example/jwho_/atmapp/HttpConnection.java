package com.example.jwho_.atmapp;

import android.content.ContentValues;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpConnection {
    private OkHttpClient client = new OkHttpClient();
    private String SERVER_URL = "http://35.200.117.1:8080/NewToken.jsp";

    public void requestToServer(ContentValues params) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for(Map.Entry<String, Object> parameter : params.valueSet())
            bodyBuilder.add(parameter.getKey(), parameter.getValue().toString());

        RequestBody body = bodyBuilder.build();

        Request.Builder requestBuilder= new Request.Builder();
        requestBuilder.url(SERVER_URL);
        requestBuilder.post(body);

        Request request = requestBuilder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("hello", "Request To Server is "+e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("hello", "Response Body : "+response.body().string());
            }
        });
    }
}
