package com.system.database;


import com.system.bean.User;
import com.system.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserData {

    public static List<User> userList = new ArrayList<User>();
    public static User loginUser = null;

    public static User getUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loginUser = user;
                return user;
            }
        }
        return null;
    }

    public static void addUser(User user) {
        userList.add(user);
        if (user.getUserType().equals("child")) {
            AccountData.addAccount(user);
        }
        FileUtil.writeJsonFile(BaseData.USER_DATA_PATH, userList);
    }

    public static void updateUser(User user) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUid()==user.getUid()) {
                userList.set(i, user);
                FileUtil.writeJsonFile(BaseData.USER_DATA_PATH, userList);
                return;
            }
        }
    }

    public static boolean userExists(String username) {
        return userList.stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }


}
