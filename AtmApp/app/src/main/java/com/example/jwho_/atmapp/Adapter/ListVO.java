package com.example.jwho_.atmapp.Adapter;

public class ListVO {

    private String task;
    private String account;
    public int money;

    public String getTask() {
        return task;
    }

    public void setTask(String title) {
        this.task = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String context) {
        this.account = context;
    }

    public int getMoney(){ return money; }

    public void setMoney(int money) { this.money = money; }

}
