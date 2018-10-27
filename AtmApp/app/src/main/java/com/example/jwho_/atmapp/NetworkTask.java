package com.example.jwho_.atmapp;

import android.content.ContentValues;
import android.os.AsyncTask;

import org.json.JSONObject;

public class NetworkTask extends AsyncTask<Void, Void, JSONObject> {
    private String url;
    private ContentValues values;

    public NetworkTask(String url, ContentValues values) {
        this.url = url;
        this.values = values;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        JSONObject result;
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result = requestHttpURLConnection.request(url, values);

        return result;      // onPostExecute의 인자값이 된다.
    }

    @Override
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);
    }
}
