package com.system.database;


import com.system.bean.Account;
import com.system.bean.History;
import com.system.bean.Mission;
import com.system.bean.User;
import com.system.utils.ConfigUtil;
import com.system.utils.FileUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MissionData {

	public static List<Mission> missionList = new ArrayList<Mission>();

	public static void addMission(Mission mission) {
		Mission maxMission =missionList.stream()
				.max(Comparator.comparingInt(Mission::getMid))
				.orElse(null);
		mission.setMid(null == maxMission? 1 : maxMission.getMid() + 1);
		missionList.add(mission);
		FileUtil.writeJsonFile(BaseData.MISSION_DATA_PATH, missionList);
	}

	public static void deleteMission(Mission mission) {
		missionList.remove(mission);
		FileUtil.writeJsonFile(BaseData.MISSION_DATA_PATH, missionList);
	}



	public static void receiveMission(Mission mission) {
		for (Mission data : missionList) {
			if (data.getMid() == mission.getMid()) {
				data.setStatus("Progress");
				data.setChild(UserData.loginUser);
			}
		}
		FileUtil.writeJsonFile(BaseData.MISSION_DATA_PATH, missionList);
	}

	public static void confirmMission(Mission mission) {
		for (Mission data : missionList) {
			if (data.getMid() == mission.getMid()) {
				data.setStatus("Completed");
				data.setEndDate(ConfigUtil.dateToString(new java.util.Date()));
				Account account = AccountData.getAccountByUid(mission.getChild().getUid());
				account.setCurrentBalance(account.getCurrentBalance() + mission.getAmount());
				AccountData.updateAccount(account);
				HistoryData.addHistory(new History("Mission Completed", mission.getAmount(), mission,mission.getChild(), ConfigUtil.dateToString(new java.util.Date())));
			}
		}
		FileUtil.writeJsonFile(BaseData.MISSION_DATA_PATH, missionList);
	}

	public static List<Mission> getParentMission() {
		return missionList.stream()
				.filter(mission -> mission.getParent().getUid() == UserData.loginUser.getUid())
				.sorted(Comparator
						.<Mission, String>comparing(mission -> {
                            return switch (mission.getStatus()) {
                                case "Progress" -> "A";
                                case "Pending" -> "B";
                                case "Completed" -> "C";
                                default -> "D";
                            };
						}))
				.collect(Collectors.toList());
	}

	public static List<Mission> getChildMission() {
		return missionList.stream()
				.filter(mission -> (mission.getChild().getUid() == UserData.loginUser.getUid()&&mission.getStatus().equals("Progress"))||mission.getStatus().equals("Pending"))
				.sorted(Comparator
				.<Mission, String>comparing(mission -> {
					return switch (mission.getStatus()) {
						case "Progress" -> "A";
						case "Pending" -> "B";
						default -> "D";
					};
				}))
				.collect(Collectors.toList());
	}

	public static List<Mission> getChildMissionCount() {
		return missionList.stream()
				.filter(mission -> mission.getChild().getUid() == UserData.loginUser.getUid())
				.filter(mission -> mission.getStatus().equals("Progress"))
				.collect(Collectors.toList());
	}

}
