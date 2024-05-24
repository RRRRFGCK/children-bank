package com.system.bean;

public class History {
    private int hid;
    private String historyType;
    private double amount;
    private Mission mission;
    private User child;
    private String date;

    public History(String historyType, double amount, Mission mission, User child, String date) {
        this.historyType = historyType;
        this.amount = amount;
        this.mission = mission;
        this.child = child;
        this.date = date;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public String getHistoryType() {
        return historyType;
    }

    public void setHistoryType(String historyType) {
        this.historyType = historyType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public User getChild() {
        return child;
    }

    public void setChild(User child) {
        this.child = child;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "History{" + "hid=" + hid + ", historyType=" + historyType + ", amount=" + amount + ", mission=" + mission + ", child=" + child + ", date=" + date + '}';
    }
}
