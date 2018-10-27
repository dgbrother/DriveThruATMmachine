package com.example.jwho_.atmapp.Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ListVO{

    private String carNumber;
    private String no;
    private String task;
    private String srcAccount;
    private String desAccount;
    private int money;

    public String getCarNumber(){return carNumber;}

    public void setCarNumber(String carNumber){ this.carNumber = carNumber;}

    public String getNo() { return no; }

    public void setNo(String no){ this.no = no;}

    public String getTask() {
        return task;
    }

    public void setTask(String title) {
        this.task = title;
    }

    public String getSrcAccount() {
        return srcAccount;
    }

    public void setSrcAccount(String context) {
        this.srcAccount = context;
    }

    public String getDesAccount() {
        return desAccount;
    }

    public void setDesAccount(String context) {
        this.desAccount = context;
    }

    public int getMoney(){ return money; }

    public void setMoney(int money) { this.money = money; }

}
