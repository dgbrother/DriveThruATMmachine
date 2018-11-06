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
                Toast.makeText(AtmMain.this,"button click",Toast.LENGTH_SHORT).show();
                break;
            case R.id.getMoneyBtn:
                Toast.makeText(AtmMain.this,"button click",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sendMoneyBtn:
                Toast.makeText(AtmMain.this,"button click",Toast.LENGTH_SHORT).show();
                break;
            case R.id.reservationTaskBtn:
                Intent intent = new Intent(getApplicationContext(),ReservationListPrint.class);
                startActivity(intent);
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
