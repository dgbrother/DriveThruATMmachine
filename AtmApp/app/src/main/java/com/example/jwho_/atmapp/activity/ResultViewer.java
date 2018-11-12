package com.example.jwho_.atmapp.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.example.jwho_.atmapp.R;
import com.example.jwho_.atmapp.RequestHttpURLConnection;
import com.example.jwho_.atmapp.ReservationResult;

import org.json.JSONObject;

import java.util.ArrayList;

public class ResultViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_viewer);

        Intent intent = getIntent();
        String nfcId = intent.getStringExtra("nfcId");

        loadResult(nfcId);

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                startActivity(new Intent(ResultViewer.this, AtmMain.class));
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0, 6000);
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            JSONObject jsonResult = requestHttpURLConnection.request(url, values);
            Log.d("helloTest",jsonResult.toString());
            ArrayList<ReservationResult> resultList = ReservationResult.jsonToReserveResult(jsonResult);
            String result = getResultString(resultList);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView resultText = findViewById(R.id.result_text);
            resultText.setText(s);
            resultText.setMovementMethod(new ScrollingMovementMethod());
        }
    }

    private void loadResult(String nfcId) {
        final String url = "http://35.200.117.1:8080/control.jsp";

        ContentValues params = new ContentValues();
        params.put("type",      "reservation");
        params.put("action",    "execute");
        params.put("nfcId", nfcId);

        NetworkTask loadResultTask = new NetworkTask(url, params);
        loadResultTask.execute();
    }

    private String getResultString(ArrayList<ReservationResult> list) {
        int executeCount = list.size();
        int successCount = 0;
        StringBuilder stringBuilder = new StringBuilder();

        for(ReservationResult result : list) {
            switch(result.getResult()) {
                case "true":
                    successCount++;
                    stringBuilder.append("[성공] ");
                    stringBuilder.append(result.getNo());
                    stringBuilder.append("번 업무 : ");
                    stringBuilder.append(result.getMsg());
                    stringBuilder.append(System.getProperty("line.separator"));
                    break;
                case "false":
                    stringBuilder.append("[실패] ");
                    stringBuilder.append(result.getNo());
                    stringBuilder.append("번 업무 : ");
                    stringBuilder.append(result.getMsg());
                    stringBuilder.append(System.getProperty("line.separator"));
                    break;
            }
        }
        String summery = executeCount+"개 업무 중 "+successCount+"개 완료하였습니다.\n";

        stringBuilder.insert(0, summery);
        stringBuilder.append(System.getProperty("line.separator"));

        return stringBuilder.toString();
    }
}
