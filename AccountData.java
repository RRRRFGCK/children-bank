package com.system.database;


import com.system.bean.Account;
import com.system.bean.User;
import com.system.utils.ConfigUtil;
import com.system.utils.FileUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AccountData {

    public static List<Account> accountList = new ArrayList<Account>();

    public static void executeCurrentInterest() {
        for (Account account : accountList) {
            if (!account.getCurrentInterestDate().equals(ConfigUtil.dateToString(new Date()))) {
                account.setCurrentBalance(account.getCurrentBalance() + account.getCurrentBalance() * ConfigUtil.configList.get(0).getInterestRate());
            }
        }
    }

    public static void addAccount(User user) {
        Account account = new Account(user, 0.0, 0.0,0.0,"", ConfigUtil.dateToString(new Date()));
        accountList.add(account);
        FileUtil.writeJsonFile(BaseData.ACCOUNT_DATA_PATH, accountList);
    }

    public static Account getAccountByUid(int uid) {
        return accountList.stream()
                .filter(account -> account.getUser().getUid() == uid)
                .collect(Collectors.toList()).get(0);
    }

    public static void transfer(String accountType, double amount) {
        for (Account a : accountList) {
            if (a.getUser().getUid() == UserData.loginUser.getUid()) {
                a.setCurrentBalance(a.getCurrentBalance() - amount);
                if(accountType.equals("Saving1")){
                    a.setSavingsBalance(a.getSavingsBalance() + amount);
                }else{
                    a.setSavingsBalance2(a.getSavingsBalance2() + amount);
                    a.setSaving2InDate(ConfigUtil.dateToString(new Date()));
                }
            }
        }
        FileUtil.writeJsonFile(BaseData.ACCOUNT_DATA_PATH, accountList);
    }

    public static void withdraw(String accountType,double amount) {
        for (Account a : accountList) {
            if (a.getUser().getUid() == UserData.loginUser.getUid()) {
                if(accountType.equals("Saving1")){
                    a.setSavingsBalance(a.getSavingsBalance() - amount);
                }else{
                    a.setSavingsBalance2(0.0);
                    a.setSaving2InDate("");
                }
            }
        }
        FileUtil.writeJsonFile(BaseData.ACCOUNT_DATA_PATH, accountList);
    }

    public static void addShopping(double amount) {
        for (Account a : accountList) {
            if (a.getUser().getUid() == UserData.loginUser.getUid()) {
                a.setCurrentBalance(a.getCurrentBalance() - amount);
            }
        }
        FileUtil.writeJsonFile(BaseData.ACCOUNT_DATA_PATH, accountList);
    }


    public static Account getChildAccount() {
        return accountList.stream()
                .filter(account -> account.getUser().getUid() == UserData.loginUser.getUid())
                .collect(Collectors.toList()).get(0);
    }


    public static void updateAccount(Account account) {
        for (Account a : accountList) {
            if (a.getUser().getUid() == account.getUser().getUid()) {
                a = account;
            }
        }
        FileUtil.writeJsonFile(BaseData.ACCOUNT_DATA_PATH, accountList);
    }

}
