package com.example.jwho_.atmapp;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jwho_.atmapp.Adapter.ReservationListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ReservationListPrint extends AppCompatActivity {

    private ListView listview;
    private ReservationListAdapter adapter;
    String carNumber;
    private String[] taskNo = new String[10];
    private String[] Task = new String[10];
    private String[] srcAccount = new String[10];
    private String[] desAccount = new String[10];
    private int[] Money = new int[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list_print);
        Intent intent = getIntent();
        String jsonStr = intent.getStringExtra("carNumber");
        Log.d("testnow", jsonStr);

        try {
            JSONArray jarray = new JSONObject(jsonStr).getJSONArray("data");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출

                carNumber = jObject.getString("carnumber");
                taskNo[i] = jObject.getString("no");
                Task[i] = jObject.getString("type");
                srcAccount[i] = jObject.getString("src_account");
                desAccount[i] = jObject.getString("dst_account");
                Money[i] = jObject.getInt("amount");

                TextView tv = (TextView) findViewById(R.id.text1);
                tv.setText("환영합니다.");
                TextView tv1 = (TextView) findViewById(R.id.text2);
                tv1.setText(carNumber);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new ReservationListAdapter();
        for (int j = 0; j < taskNo.length; j++) {
            Log.d("testzzz", "zzzxxxx");
            if(taskNo[j] != null) {
                adapter.addVO(carNumber,taskNo[j], Task[j], srcAccount[j], desAccount[j], Money[j]);
            }
        }

        listview = findViewById(R.id.List_view);
        listview.setAdapter(adapter);
    }

}
