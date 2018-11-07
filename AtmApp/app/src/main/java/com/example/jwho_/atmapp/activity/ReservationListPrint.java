package com.example.jwho_.atmapp.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jwho_.atmapp.R;
import com.example.jwho_.atmapp.RequestHttpURLConnection;
import com.example.jwho_.atmapp.ReservationListAdapter;
import com.example.jwho_.atmapp.ReservationWork;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ReservationListPrint extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private String url = "http://35.200.117.1:8080/control.jsp";
    private ReservationListAdapter adapter;
    private String currentCarNumber;
    private String currentNFCId;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list_print);

        Intent intent = getIntent();
        currentCarNumber    = intent.getStringExtra("carNumber");
        currentNFCId        = intent.getStringExtra("nfcId");

        if(currentCarNumber != null)
            loadReservation(currentCarNumber);
        else
            currentCarNumber = "등록된 예약업무가 없습니다.";

        TextView carNumberView = findViewById(R.id.text2);
        carNumberView.setText(currentCarNumber);

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
            String result = intent.getExtras().getString("nfcTag");

            if(result != null) {
                try {
                    JSONObject jsonNFCTagInfo = new JSONObject(result);
                    String nfcId = jsonNFCTagInfo.getString("nfcId");

                    if(currentNFCId.equals(nfcId)){
                    Intent intentToResultViewer = new Intent(getApplicationContext(), ResultViewer.class);
                    intentToResultViewer.putExtra("nfcId", nfcId);
                    startActivity(intentToResultViewer);
                    finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"잘못된 카드입니다. 다시 입력 해 주세요.",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

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

            for (ReservationWork work : works)
                adapter.addVO(work);

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

    private void loadReservation(String carNumber) {
        ContentValues params = new ContentValues();
        params.put("type",      "reservation");
        params.put("action",    "select");
        params.put("from",      "machine");
        params.put("carNumber", carNumber);

        NetworkTask loadReservationInfoTask = new NetworkTask(url, params);
        loadReservationInfoTask.execute();
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
