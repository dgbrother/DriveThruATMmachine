package com.example.jwho_.atmapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jwho_.atmapp.Adapter.ReservationListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ReservationListPrint extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String url = "http://35.200.117.1:8080/control.jsp";
    private ReservationListAdapter adapter;
    private String currentCarNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list_print);

        try {
            Intent intent = getIntent();
            String jsonStr = intent.getStringExtra("carNumber");
            JSONObject jsonObject = new JSONObject(jsonStr);

            ArrayList<ReservationWork> works = ReservationWork.jsonToReserveInfo(jsonObject);
            currentCarNumber = works.get(0).getCarNumber();

            adapter = new ReservationListAdapter();
            for (int i = 0; i < works.size(); i++)
                adapter.addVO(works.get(i));

            ListView listview = findViewById(R.id.List_view);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(ReservationListPrint.this);

            TextView carnum;
            carnum = (TextView)findViewById(R.id.text2);
            carnum.setText(currentCarNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
        public void onItemClick (AdapterView < ? > adapterView, View view,final int i, long l){
            AlertDialog.Builder builder = new AlertDialog.Builder(ReservationListPrint.this);
            builder.setTitle("예약 번호 : " + adapter.getNo(i));
            builder.setMessage("정말 삭제하시겠습니까?");

            builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    deleteReservation(adapter.getNo(i));
                }
            });

            builder.show();
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

            ArrayList<ReservationWork> works = ReservationWork.jsonToReserveInfo(result);
            adapter = new ReservationListAdapter();
            for (int i = 0; i < works.size(); i++)
                adapter.addVO(works.get(i));

            return result;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s);
            ListView listview = findViewById(R.id.List_view);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(ReservationListPrint.this);
        }
    }

    private void deleteReservation(String no) {
        ContentValues params = new ContentValues();
        params.put("type",      "reservation");
        params.put("action",    "update");
        params.put("from",      "machine");
        params.put("carNumber", currentCarNumber);
        params.put("no",        no);

        NetworkTask deleteReservationInfoTask = new NetworkTask(url, params);
        deleteReservationInfoTask.execute();
    }
}
