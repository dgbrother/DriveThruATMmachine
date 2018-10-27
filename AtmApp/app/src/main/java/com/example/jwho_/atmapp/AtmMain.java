package com.example.jwho_.atmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jwho_.atmapp.Adapter.ReservationListAdapter;

public class AtmMain extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_main);

        Button button = findViewById(R.id.putMoneyBtn);
        button.setOnClickListener(this);

        Button button2 = findViewById(R.id.getMoneyBtn);
        button2.setOnClickListener(this);

        Button button3 = findViewById(R.id.sendMoneyBtn);
        button3.setOnClickListener(this);

        Button button4 = findViewById(R.id.reservationTaskBtn);
        button4.setOnClickListener(this);
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
            String carNumber = intent.getExtras().getString("carNumber");
            //Log.d("abcdefg",carNumber);

            //Toast.makeText(getApplicationContext(), carNumber, Toast.LENGTH_LONG).show();

            Intent intentac = new Intent(getApplicationContext(), ReservationListPrint.class);
            intentac.putExtra("carNumber",carNumber);
            startActivity(intentac);
            finish();
        }
    };
}
