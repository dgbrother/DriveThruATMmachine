package com.example.jwho_.atmapp.Adapter;

public class ListVO {

    private String task;
    private String account;
    private int money;

    public String getTitle() {
        return task;
    }

    public void setTitle(String title) {
        this.task = title;
    }

    public String getContext() {
        return account;
    }

    public void setContext(String context) {
        this.account = context;
    }

    public int getMoney(){ return money; }

    public void setMoney(int money) { this.money = money; }

}
