package com.example.jwho_.atmapp;

public class resTaskList {
    private String task;
    private String account;
    private int money;

    public resTaskList(String task, String account, int money){
        this.task = task;
        this.account = account;
        this.money = money;
    }

    public String getTask(){
        return task;
    }

    public void setTask(String task){
        this.task = task;
    }

    public String getAccount(){
        return account;
    }

    public void setAccount(String account){
        this.account = account;
    }

    public int getMoney(){
        return money;
    }

    public void setMoney(int money){
        this.money = money;
    }
}
