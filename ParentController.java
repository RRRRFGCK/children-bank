package com.system.controller;

import com.system.bean.Account;
import com.system.bean.History;
import com.system.bean.Mission;
import com.system.bean.User;
import com.system.database.*;
import com.system.utils.ConfigUtil;
import com.system.utils.FileUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ParentController {
    @FXML
    private TextField savingsGoal;
    @FXML
    private TextField interestRate;
    @FXML
    private Label configMsg;
    @FXML
    private Label confirmMsg;
    @FXML
    private TableColumn<Account, String> accountChild;

    @FXML
    private TableColumn<Account, Double> accountCurrent;

    @FXML
    private TableColumn<Account, Double> accountSavings;
    @FXML
    private TableColumn<Account, Double> accountSavings2;

    @FXML
    private TableColumn<Account, String> accountSavings2Date;


    @FXML
    private Button addBtn;
    @FXML
    private Label addMissionMsg;
    @FXML
    private TextArea addMissionDesc;

    @FXML
    private TextField addMissionName;

    @FXML
    private TextField amount;
    @FXML
    private Pane historyPane;

    @FXML
    private TableColumn<History, Double> historyAmount;

    @FXML
    private TableColumn<History, String> historyChild;

    @FXML
    private TableColumn<History, String> historyDate;

    @FXML
    private TableColumn<History, String> historyType;

    @FXML
    private Label loginUserName;

    @FXML
    private Pane missionPane;

    @FXML
    private TableColumn<Mission, Integer> mid;

    @FXML
    private TableColumn<Mission, Double> missionAmount;

    @FXML
    private TableColumn<Mission, String> missionChild;

    @FXML
    private TableColumn<Mission, String> missionEndDate;

    @FXML
    private TableColumn<Mission, String> missionName;


    @FXML
    private TableColumn<Mission, String> missionStartDate;

    @FXML
    private TableColumn<Mission, String> missionStatus;

    @FXML
    private Pane homePane;
    @FXML
    private TableView<Mission> missionTableView;
    @FXML
    private TableView<Account> accountTableView;
    @FXML
    private TableView<History> historyTableView;
    @FXML
    void addMission(ActionEvent event) {
        if (addMissionName.getText().isEmpty() || amount.getText().isEmpty()|| addMissionDesc.getText().isEmpty()) {
            addMissionMsg.setTextFill(javafx.scene.paint.Color.RED);
            addMissionMsg.setText("Please input all!");
            return;
        }
        if(!ConfigUtil.isFloatingPoint(amount.getText())){
            addMissionMsg.setTextFill(javafx.scene.paint.Color.RED);
            addMissionMsg.setText("Amount must be a number!");
            return;
        }
        Mission mission = new Mission();
        mission.setMissionName(addMissionName.getText());
        mission.setMissionDesc(addMissionDesc.getText());
        addMissionDesc.clear();
        mission.setAmount(Double.parseDouble(amount.getText()));
        mission.setStartDate(ConfigUtil.dateToString(new java.util.Date()));
        mission.setEndDate("");
        mission.setStatus("Pending");
        mission.setChild(new User());
        mission.setParent(UserData.loginUser);
        MissionData.addMission(mission);
        addMissionMsg.setTextFill(javafx.scene.paint.Color.GREEN);
        addMissionMsg.setText("Success!");
        missionTableView.setItems(FXCollections.observableList(MissionData.getParentMission()));
        addMissionName.clear();
        amount.clear();
        addMissionDesc.clear();
    }

    @FXML
    void confirmMission(ActionEvent event) {
        if (missionTableView.getSelectionModel().getSelectedItem() == null) {
            confirmMsg.setTextFill(javafx.scene.paint.Color.RED);
            confirmMsg.setText("Choose!");
            return;
        }
        if (missionTableView.getSelectionModel().getSelectedItem().getStatus().equals("Progress")) {
            MissionData.confirmMission(missionTableView.getSelectionModel().getSelectedItem());
            confirmMsg.setTextFill(javafx.scene.paint.Color.GREEN);
            confirmMsg.setText("Success!");
            missionTableView.setItems(FXCollections.observableList(MissionData.getParentMission()));
            missionTableView.refresh();
        } else {
            confirmMsg.setTextFill(javafx.scene.paint.Color.RED);
            confirmMsg.setText("Error!");
        }
    }

    @FXML
    void deleteMission(ActionEvent event) {
        if (missionTableView.getSelectionModel().getSelectedItem() == null) {
            confirmMsg.setTextFill(javafx.scene.paint.Color.RED);
            confirmMsg.setText("Choose!");
            return;
        }
        if (missionTableView.getSelectionModel().getSelectedItem().getStatus().equals("Completed")) {
            confirmMsg.setTextFill(javafx.scene.paint.Color.RED);
            confirmMsg.setText("Cannot delete completed!");
            return;
        }
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Delete Mission Dialog");
        confirmationDialog.setHeaderText("Are you sure you want to delete?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        confirmationDialog.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                MissionData.deleteMission(missionTableView.getSelectionModel().getSelectedItem());
                confirmMsg.setTextFill(javafx.scene.paint.Color.GREEN);
                confirmMsg.setText("Success!");
                missionTableView.setItems(FXCollections.observableList(MissionData.getParentMission()));
                missionTableView.refresh();
            } else {
                confirmMsg.setTextFill(javafx.scene.paint.Color.RED);
                confirmMsg.setText("Error!");
            }
        });
    }
    @FXML
    void logout(ActionEvent event) {
        MainApplication.changeController("login-view.fxml");
    }

    @FXML
    void setConfig(ActionEvent event) {
        if (savingsGoal.getText().isEmpty() || interestRate.getText().isEmpty()) {
            configMsg.setTextFill(javafx.scene.paint.Color.RED);
            configMsg.setText("Please input all!");
        } else {
            ConfigUtil.configList.get(0).setSavingAccountGoal(Double.parseDouble(savingsGoal.getText()));
            ConfigUtil.configList.get(0).setInterestRate(Double.parseDouble(interestRate.getText()));
            FileUtil.writeJsonFile(BaseData.CONFIG_DATA_PATH, ConfigUtil.configList);
            configMsg.setTextFill(javafx.scene.paint.Color.GREEN);
            configMsg.setText("Config saved!");
        }
    }

    @FXML
    void toHistory(ActionEvent event) {
        historyTableView.setItems(FXCollections.observableList(HistoryData.getLoginUserHistoryList()));
        historyTableView.refresh();
        changePane(false, false, true);
    }

    @FXML
    void toHome(ActionEvent event) {
        accountTableView.setItems(FXCollections.observableList(AccountData.accountList));
        accountTableView.refresh();
        changePane(true, false, false);
    }

    @FXML
    void toMissionPane(ActionEvent event) {
        missionTableView.setItems(FXCollections.observableList(MissionData.getParentMission()));
        missionTableView.refresh();
        changePane(false, true, false);
    }

    @FXML
    void initialize() {
        //load username
        loginUserName.setText(UserData.loginUser.getUsername());
        //load account table and config
        accountChild.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUser().getUsername()));
        accountCurrent.setCellValueFactory(new PropertyValueFactory<>("currentBalance"));
        accountSavings.setCellValueFactory(new PropertyValueFactory<>("savingsBalance"));
        accountSavings2.setCellValueFactory(new PropertyValueFactory<>("savingsBalance2"));
        accountSavings2Date.setCellValueFactory(new PropertyValueFactory<>("saving2InDate"));
        accountTableView.setItems(FXCollections.observableList(AccountData.accountList));
        accountTableView.setPlaceholder(new StackPane(new Label("No data yet!")));
        savingsGoal.setText(Double.toString(ConfigUtil.configList.get(0).getSavingAccountGoal()));
        interestRate.setText(Double.toString(ConfigUtil.configList.get(0).getInterestRate()));
        //load mission table
        mid.setCellValueFactory(new PropertyValueFactory<>("mid"));
        missionName.setCellValueFactory(new PropertyValueFactory<>("missionName"));
        missionAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        missionStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        missionEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        missionStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        missionChild.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getChild().getUsername()));
        missionTableView.setItems(FXCollections.observableList(MissionData.getParentMission()));
        missionTableView.setPlaceholder(new StackPane(new Label("No data yet!")));
        //load history table
        historyAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        historyDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        historyType.setCellValueFactory(new PropertyValueFactory<>("historyType"));
        historyChild.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getChild().getUsername()));
        historyTableView.setItems(FXCollections.observableList(HistoryData.getLoginUserHistoryList()));
        historyTableView.setPlaceholder(new StackPane(new Label("No data yet!")));

        changePane(true, false, false);


    }

    public void changePane(boolean home, boolean mission, boolean history) {
        configMsg.setText("");
        confirmMsg.setText("");
        addMissionName.clear();
        amount.clear();
        addMissionDesc.clear();
        homePane.setVisible(home);
        missionPane.setVisible(mission);
        historyPane.setVisible(history);
    }
}
