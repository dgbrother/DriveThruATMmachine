package com.example.jwho_.atmapp;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jwho_.atmapp.Adapter.ReservationListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReservationListPrint extends AppCompatActivity {

    private ListView listview;
    private ReservationListAdapter adapter;
    private String[] Task = {"입금","출금","송금"};
    private String[] Account = {"12-1234","23-3456","44-5677"};
    private int[] Money = {1000,2500,15000};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list_print);
        Intent intent = getIntent();
        String carNumber = intent.getStringExtra("carNumber");
        Log.d("qqqqq",carNumber);
        TextView tv =(TextView)findViewById(R.id.text1);
        tv.setText("환영합니다.\n" + carNumber);

//        JSONObject jsonResult= null;
//        resTaskList restasklist = resTaskList(jsonResult);

        adapter = new ReservationListAdapter();
        for(int i=0; i<Task.length; i++){
            adapter.addVO(Task[i],Account[i],Money[i]);
//            adapter.addVO(restasklist.getTask(),restasklist.getAccount(),restasklist.getMoney());
            }

        listview = findViewById(R.id.List_view);
        listview.setAdapter(adapter);
    }

//    private resTaskList resTaskList(JSONObject jsonObject) {
//        try {
//            JSONArray jsonArray = jsonObject.getJSONArray("data");
//            JSONObject jobj = jsonArray.getJSONObject(0);
//
//            resTaskList restasklist = new resTaskList(
//                    jobj.getString("task"),
//                    jobj.getString("account"),
//                    jobj.getInt("money")
//            );
//        return restasklist;
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}