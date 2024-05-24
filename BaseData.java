package com.system.database;


import com.system.bean.*;
import com.system.utils.ConfigUtil;
import com.system.utils.FileUtil;

import java.io.IOException;
import java.util.List;

public class BaseData {

    public static String USER_DATA_PATH = "/data/user.json";
    public static String ACCOUNT_DATA_PATH = "/data/account.json";
    public static String MISSION_DATA_PATH = "/data/mission.json";
    public static String HISTORY_DATA_PATH = "/data/history.json";
    public static String CONFIG_DATA_PATH = "/data/config.json";

    public static void init() {
        UserData.userList = FileUtil.readJsonFile(USER_DATA_PATH, User.class);
        AccountData.accountList = FileUtil.readJsonFile(ACCOUNT_DATA_PATH, Account.class);
        MissionData.missionList = FileUtil.readJsonFile(MISSION_DATA_PATH, Mission.class);
        HistoryData.historyList = FileUtil.readJsonFile(HISTORY_DATA_PATH, History.class);
        ConfigUtil.configList =  FileUtil.readJsonFile(CONFIG_DATA_PATH, Config.class);
        AccountData.executeCurrentInterest();
    }


    public static <T> void save(String path, List<T> dataList) throws IOException {
        FileUtil.writeJsonFile(path, dataList);
    }

    public static <T> void add(List<T> dataList, T data, String path) throws IOException {
        dataList.add(data);
        save(path, dataList);
    }
}
