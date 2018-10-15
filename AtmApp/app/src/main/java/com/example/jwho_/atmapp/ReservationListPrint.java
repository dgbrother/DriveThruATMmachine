package com.example.jwho_.atmapp;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jwho_.atmapp.Adapter.ReservationListAdapter;

public class ReservationListPrint extends AppCompatActivity {

    private ListView listview;
    private ReservationListAdapter adapter;
    private String[] Task = {"a","b","c"};
    private String[] Account = {"1","2","3"};
    private int[] Money = {100,200,5000};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reservation_list_print);
        adapter = new ReservationListAdapter();
        for(int i=0; i<Task.length; i++){
            adapter.addVO(Task[i],Account[i],Money[i]);
            }

        listview = (ListView)findViewById(R.id.List_view);

        listview.setAdapter(adapter);
    }
}