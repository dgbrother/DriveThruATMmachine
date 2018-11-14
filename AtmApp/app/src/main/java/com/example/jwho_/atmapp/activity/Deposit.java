package com.example.jwho_.atmapp.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jwho_.atmapp.R;
import com.example.jwho_.atmapp.RequestHttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class Deposit extends AppCompatActivity implements View.OnClickListener {
    private String url = "http://35.200.117.1:8080/control.jsp";
    private String nfcId;
    private String carNumber;
    private String task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        task = getIntent().getStringExtra("task");
        nfcId = getIntent().getStringExtra("nfcId");

        if(task.equals("reservation"))
            carNumber = getIntent().getStringExtra("carNumber");
        Log.d("helloTest", "Deposit here");

        if(task.equals("putMoney") || task.equals("reservation")){
            setContentView(R.layout.activity_deposit);
        }
        else if(task.equals("getMoney")){
            setContentView(R.layout.activity_withdraw);
        }
        else if(task.equals("sendMoney")){
            setContentView(R.layout.activity_sendmoney);
        }
        findViewById(R.id.button_putNget).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_putNget:
                EditText depositAmountText = findViewById(R.id.editText_depositAmount);
                String depositAmount = depositAmountText.getText().toString();

                EditText sendTargetText = findViewById(R.id.editText_sendTarget);
                String sendTarget = sendTargetText.getText().toString();

                ContentValues params = new ContentValues();

                if(task.equals("putMoney")) {
                    params.put("type", "banking");
                    params.put("action", "deposit");
                    params.put("amount", depositAmount);
                    params.put("nfcId", nfcId);
                }
                else if(task.equals("getMoney")){
                    params.put("type", "banking");
                    params.put("action", "withdraw");
                    params.put("amount", depositAmount);
                    params.put("nfcId", nfcId);
                }
                else if(task.equals("sendMoney")){
                    params.put("type", "banking");
                    params.put("action", "send");
                    params.put("amount", depositAmount);
                    params.put("dstAccount",sendTarget);
                    params.put("nfcId", nfcId);
                }
                else if(task.equals("reservation")){
                    params.put("type", "reservation");
                    params.put("action", "execute_deposit");
                    params.put("amount", depositAmount);
                    params.put("carNumber", carNumber);
                }

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
            Intent intent = null;
            if(task.equals("putMoney")){
                intent = new Intent(getApplicationContext(), BasicBankingActivity.class);
                intent.putExtra("task",task);
            }
            else if(task.equals("getMoney")){
                intent = new Intent(getApplicationContext(), BasicBankingActivity.class);
                intent.putExtra("task",task);
                try {
                    if(s.getString("result").equals("success")){
                        intent.putExtra("result","success");
                    }
                    else if(s.getString("result").equals("fail")){
                        intent.putExtra("result","fail");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if(task.equals("sendMoney")){
                intent = new Intent(getApplicationContext(), BasicBankingActivity.class);
                intent.putExtra("task",task);
                try {
                    if(s.getString("result").equals("success")){
                        intent.putExtra("result","success");
                    }
                    else if(s.getString("result").equals("NOT_ENOUGH_MONEY")){
                        intent.putExtra("result","NOT_ENOUGH_MONEY");
                    }
                    else if(s.getString("result").equals("NOT_FOUND_ACCOUNT")){
                        intent.putExtra("result","NOT_FOUND_ACCOUNT");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if(task.equals("reservation")){
                intent = new Intent(getApplicationContext(), ResultViewer.class);
                intent.putExtra("task",task);
                intent.putExtra("nfcId", nfcId);
            }

            startActivity(intent);
            finish();
        }
    }
}
