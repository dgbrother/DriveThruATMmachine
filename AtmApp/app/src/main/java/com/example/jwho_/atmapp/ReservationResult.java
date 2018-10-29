package com.example.jwho_.atmapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReservationResult {
    String result;
    String msg;
    String no;

    public ReservationResult(String result, String msg, String no) {
        this.result = result;
        this.msg = msg;
        this.no = no;
    }

    public static ArrayList<ReservationResult> jsonToReserveResult(JSONObject jsonMain) {
        try {
            JSONArray jsonArray = jsonMain.getJSONArray("result");
            ArrayList<ReservationResult> reservationResults = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);  // JSONObject 추출
                ReservationResult reservationResult = new ReservationResult(
                        jsonObject.getString("result"),
                        jsonObject.getString("msg"),
                        jsonObject.getString("no")
                );
                reservationResults.add(reservationResult);
            }
            return reservationResults;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
