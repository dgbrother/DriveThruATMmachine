package com.example.jwho_.atmapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.jwho_.atmapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AtmMain extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_main);

        findViewById(R.id.putMoneyBtn)          .setOnClickListener(this);
        findViewById(R.id.getMoneyBtn)          .setOnClickListener(this);
        findViewById(R.id.sendMoneyBtn)         .setOnClickListener(this);
        findViewById(R.id.reservationTaskBtn)   .setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.putMoneyBtn:
                Intent putIntent = new Intent(getApplicationContext(),ReservationListBtnActivity.class);
                putIntent.putExtra("task","putMoney");
                startActivity(putIntent);
                finish();
                break;
            case R.id.getMoneyBtn:
                Intent getIntent = new Intent(getApplicationContext(),ReservationListBtnActivity.class);
                getIntent.putExtra("task","getMoney");
                startActivity(getIntent);
                finish();
                break;
            case R.id.sendMoneyBtn:
                Intent sendIntent = new Intent(getApplicationContext(),ReservationListBtnActivity.class);
                sendIntent.putExtra("task","sendMoney");
                startActivity(sendIntent);
                finish();
                break;
            case R.id.reservationTaskBtn:
                Intent reservationIntent = new Intent(getApplicationContext(),ReservationListBtnActivity.class);
                reservationIntent.putExtra("task","reservation");
                startActivity(reservationIntent);
                finish();
                break;
        }
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
        String carEntry = intent.getExtras().getString("carEntry");

        if(carEntry != null) {
            try {
                JSONObject jsonCarEntryInfo = new JSONObject(carEntry);
                String carNumber    = jsonCarEntryInfo.getString("carNumber");
                String nfcId        = jsonCarEntryInfo.getString("nfcId");

                Intent intentToListPrint = new Intent(getApplicationContext(), ReservationListPrint.class);
                intentToListPrint.putExtra("carNumber", carNumber);
                intentToListPrint.putExtra("nfcId", nfcId);
                startActivity(intentToListPrint);
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        }
    };
}
