package com.example.jwho_.atmapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jwho_.atmapp.R;

public class BasicBankingActivity extends AppCompatActivity {
    private String task;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        task = getIntent().getStringExtra("task");
        result = getIntent().getStringExtra("result");

        if(task.equals("putMoney")){
            setContentView(R.layout.activity_basic_banking);
            TextView resultText = findViewById(R.id.task_result);
            resultText.setText("입금이 완료되었습니다.");
        }
        else if(task.equals("getMoney")){
            if(result.equals("success")){
                setContentView(R.layout.activity_basic_banking);
                TextView resultText = findViewById(R.id.task_result);
                resultText.setText("출금이 완료되었습니다.");
            }
            else{
                setContentView(R.layout.activity_basic_banking);
                TextView resultText = findViewById(R.id.task_result);
                resultText.setText("잔액이 부족합니다.");
            }
        }

        else if(task.equals("sendMoney")){
            if(result.equals("success")){
                setContentView(R.layout.activity_basic_banking);
                TextView resultText = findViewById(R.id.task_result);
                resultText.setText("송금이 완료되었습니다.");
            }
            else if(result.equals("NOT_ENOUGH_MONEY")){
                setContentView(R.layout.activity_basic_banking);
                TextView resultText = findViewById(R.id.task_result);
                resultText.setText("잔액이 부족합니다.");
            }
            else if(result.equals("NOT_FOUND_ACCOUNT")){
                setContentView(R.layout.activity_basic_banking);
                TextView resultText = findViewById(R.id.task_result);
                resultText.setText("계좌번호를 다시 확인해 주세요.");
            }
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                startActivity(new Intent(BasicBankingActivity.this, AtmMain.class));
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0, 6000);
    }
}
