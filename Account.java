package com.system.bean;

public class Account {
    private User user;
    private double savingsBalance = 0.0;
    private double savingsBalance2 = 0.0;
    private double currentBalance = 0.0;
    private String saving2InDate;
    private String currentInterestDate;

    public Account() {
    }

    public Account(User user, double savingsBalance, double savingsBalance2, double currentBalance, String saving2InDate, String currentInterestDate) {
        this.user = user;
        this.savingsBalance = savingsBalance;
        this.savingsBalance2 = savingsBalance2;
        this.currentBalance = currentBalance;
        this.saving2InDate = saving2InDate;
        this.currentInterestDate = currentInterestDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getSavingsBalance() {
        return savingsBalance;
    }

    public void setSavingsBalance(double savingsBalance) {
        this.savingsBalance = savingsBalance;
    }

    public double getSavingsBalance2() {
        return savingsBalance2;
    }

    public void setSavingsBalance2(double savingsBalance2) {
        this.savingsBalance2 = savingsBalance2;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getSaving2InDate() {
        return saving2InDate;
    }

    public void setSaving2InDate(String saving2InDate) {
        this.saving2InDate = saving2InDate;
    }

    public String getCurrentInterestDate() {
        return currentInterestDate;
    }

    public void setCurrentInterestDate(String currentInterestDate) {
        this.currentInterestDate = currentInterestDate;
    }

    @Override
    public String toString() {
        return "Account{" +
                "user=" + user +
                ", savingsBalance=" + savingsBalance +
                ", savingsBalance2=" + savingsBalance2 +
                ", currentBalance=" + currentBalance +
                ", saving2InDate='" + saving2InDate + '\'' +
                ", currentInterestDate='" + currentInterestDate + '\'' +
                '}';
    }
}
