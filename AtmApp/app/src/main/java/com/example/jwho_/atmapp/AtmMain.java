package com.example.jwho_.atmapp;

import android.content.Intent;
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.putMoneyBtn:
                Toast.makeText(AtmMain.this,"button click",Toast.LENGTH_SHORT).show();
                break;
            case R.id.getMoneyBtn:

                Intent intent = new Intent(getApplicationContext(),ReservationListPrint.class);
                startActivity(intent);
                break;
        }
    }
}
