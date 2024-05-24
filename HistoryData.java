package com.system.database;


import com.system.bean.History;
import com.system.bean.Mission;
import com.system.bean.User;
import com.system.utils.FileUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryData {

	public static List<History> historyList = new ArrayList<History>();

	public static void addHistory(History history) {
		History maxHistory =historyList.stream()
				.max(Comparator.comparingInt(History::getHid))
				.orElse(null);
		history.setHid(null == maxHistory? 1 : maxHistory.getHid() + 1);
		historyList.add(history);
		FileUtil.writeJsonFile(BaseData.HISTORY_DATA_PATH, historyList);
	}

	public static List<History> getLoginUserHistoryList() {
		if(!UserData.loginUser.getUserType().equals("child")){
			return historyList;
		}else{
			return historyList.stream()
					.filter(history -> history.getChild().getUid() == UserData.loginUser.getUid())
					.collect(Collectors.toList());
		}
	}
}
