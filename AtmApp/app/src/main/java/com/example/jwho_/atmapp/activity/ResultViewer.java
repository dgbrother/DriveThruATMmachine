package com.example.jwho_.atmapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.jwho_.atmapp.R;
import com.example.jwho_.atmapp.ReservationResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_viewer);

        try {
            Intent intent = getIntent();
            String jsonStr = intent.getStringExtra("result");
            JSONObject jsonObject = new JSONObject(jsonStr);

            ArrayList<ReservationResult> resultList = ReservationResult.jsonToReserveResult(jsonObject);
            String result = getResultString(resultList);

            TextView resultText = findViewById(R.id.result_text);
            resultText.setText(result);
            resultText.setMovementMethod(new ScrollingMovementMethod());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
