package com.system.bean;

public class Mission {
    private int mid;
    private String missionName;
    private String missionDesc;
    private double amount;
    private String status;
    private User parent;
    private User child;
    private String startDate;
    private String endDate;

    public Mission(int mid, String missionName, String missionDesc, double amount, String status, User parent, User child, String startDate, String endDate) {
        this.mid = mid;
        this.missionName = missionName;
        this.missionDesc = missionDesc;
        this.amount = amount;
        this.status = status;
        this.parent = parent;
        this.child = child;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Mission() {
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getMissionDesc() {
        return missionDesc;
    }

    public void setMissionDesc(String missionDesc) {
        this.missionDesc = missionDesc;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public User getChild() {
        return child;
    }

    public void setChild(User child) {
        this.child = child;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "mid=" + mid +
                ", missionName='" + missionName + '\'' +
                ", missionDesc='" + missionDesc + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", parent=" + parent +
                ", child=" + child +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
