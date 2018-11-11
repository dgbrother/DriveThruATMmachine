package com.example.jwho_.atmapp.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.jwho_.atmapp.R;
import com.example.jwho_.atmapp.RequestHttpURLConnection;

import org.json.JSONObject;

public class Deposit extends AppCompatActivity implements View.OnClickListener {
    private String url = "http://35.200.117.1:8080/control.jsp";
    private String nfcId;
    private String carNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        findViewById(R.id.button_deposit).setOnClickListener(this);

        nfcId = getIntent().getStringExtra("nfcId");
        carNumber = getIntent().getStringExtra("carNumber");
        Log.d("helloTest", "Deposit here");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_deposit:
                EditText depositAmountText = findViewById(R.id.editText_depositAmount);
                String depositAmount = depositAmountText.getText().toString();
                ContentValues params = new ContentValues();
                params.put("type",      "reservation");
                params.put("action",    "execute_deposit");
                params.put("amount",    depositAmount);
                params.put("carNumber", carNumber);
                NetworkTask depositProcess = new NetworkTask(url, params);
                depositProcess.execute();
                break;
        }
    }

    public class NetworkTask extends AsyncTask<Void, Void, JSONObject> {
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            JSONObject result = requestHttpURLConnection.request(url, values);

            return result;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s);
            Intent intentToResultView = new Intent(getApplicationContext(), ResultViewer.class);
            intentToResultView.putExtra("nfcId", nfcId);
            startActivity(intentToResultView);
            finish();
        }
    }
}
