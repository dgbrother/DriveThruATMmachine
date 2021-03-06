package com.example.jwho_.atmapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jwho_.atmapp.R;
import org.json.JSONException;
import org.json.JSONObject;

public class ReservationListBtnActivity extends AppCompatActivity {

    String task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        task = intent.getExtras().getString("task");

        setContentView(R.layout.activity_reservation_list_btn);
        findViewById(R.id.Back).setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), AtmMain.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }


    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("GCMData"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager
                .getInstance(this)
                .unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String nfcTag = intent.getExtras().getString("nfcTag");

            if(nfcTag != null) {
                try {
                    JSONObject jsonNfcTagInfo = new JSONObject(nfcTag);
                    String nfcId = jsonNfcTagInfo.getString("nfcId");

                    if(task.equals("putMoney")){
                        Intent intentDeposit = new Intent(getApplicationContext(), Deposit.class);
                        intentDeposit.putExtra("task",task);
                        intentDeposit.putExtra("nfcId",nfcId);
                        startActivity(intentDeposit);
                        finish();
                    }
                    else if(task.equals("getMoney")){
                        Intent intentDeposit = new Intent(getApplicationContext(), Deposit.class);
                        intentDeposit.putExtra("task",task);
                        intentDeposit.putExtra("nfcId",nfcId);
                        startActivity(intentDeposit);
                        finish();
                    }
                    else if(task.equals("sendMoney")){
                        Intent intentDeposit = new Intent(getApplicationContext(), Deposit.class);
                        intentDeposit.putExtra("task",task);
                        intentDeposit.putExtra("nfcId",nfcId);
                        startActivity(intentDeposit);
                        finish();
                    }
                    else if(task.equals("reservation")){
                        String carNumber = jsonNfcTagInfo.getString("carNumber");

                        Intent intentToListPrint = new Intent(getApplicationContext(), ReservationListPrint.class);
                        intentToListPrint.putExtra("carNumber", carNumber);
                        intentToListPrint.putExtra("nfcId", nfcId);
                        startActivity(intentToListPrint);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
