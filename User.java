package com.system.bean;

public class User {
    private int uid;
    private String username;
    private String password;
    private String userType;


    public User(int uid, String username, String password, String userType) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public User() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
         return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", userType=" + userType + "]";
    }


}
