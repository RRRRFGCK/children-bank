package com.system.bean;

public class Config {
    private double interestRate;
    private double savingAccountGoal;


    public Config(double interestRate, double savingAccountGoal) {
        this.interestRate = interestRate;
        this.savingAccountGoal = savingAccountGoal;
    }

    public Config() {
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getSavingAccountGoal() {
        return savingAccountGoal;
    }

    public void setSavingAccountGoal(double savingAccountGoal) {
        this.savingAccountGoal = savingAccountGoal;
    }

    @Override
    public String toString() {
        return "Config{" +
                "interestRate=" + interestRate +
                ", savingAccountGoal=" + savingAccountGoal +
                '}';
    }
}
