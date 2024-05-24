package com.system.controller;

import com.system.bean.History;
import com.system.bean.Mission;
import com.system.database.AccountData;
import com.system.database.HistoryData;
import com.system.database.MissionData;
import com.system.database.UserData;
import com.system.utils.ConfigUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Date;

public class ChildController {

    @FXML
    private Label balanceLab;

    @FXML
    private ProgressBar goalsBar;

    @FXML
    private Label goalsLab;

    @FXML
    private TextField goodsAmount;

    @FXML
    private TextField goodsName;

    @FXML
    private TableColumn<Mission, String> historyAmount;

    @FXML
    private TableColumn<Mission, String> historyDate;

    @FXML
    private Pane historyPane;

    @FXML
    private TableView<History> historyTableView;

    @FXML
    private TableColumn<History, String> historyType;

    @FXML
    private Pane homePane;

    @FXML
    private Label loginUserName;

    @FXML
    private TableColumn<Mission, Integer> mid;

    @FXML
    private TableColumn<Mission, String> missionAmount;

    @FXML
    private TableColumn<Mission, String> missionDesc;

    @FXML
    private TableColumn<Mission, String> missionName;

    @FXML
    private Pane missionPane;

    @FXML
    private TableColumn<Mission, String> missionParent;

    @FXML
    private TableColumn<Mission, String> missionStatus;

    @FXML
    private TableView<Mission> missionTableView;

    @FXML
    private Label shoppingMsg;

    @FXML
    private Label transferMsg;

    @FXML
    private TextField transferText;

    @FXML
    private TextField withdrawAmount;

    @FXML
    private Label withdrawMsg;
    @FXML
    private Label receiveMsg;
    @FXML
    private Label balance2Lab;
    @FXML
    private Label daysLimitLab;

    @FXML
    private RadioButton radio1;

    @FXML
    private RadioButton radio2;

    @FXML
    private Label withdrawMsg2;
    @FXML
    private Label inDateLab;
    private ToggleGroup chooseToggleGroup;

    @FXML
    void addShopping(ActionEvent event) {
        if (goodsName.getText().isEmpty() || goodsAmount.getText().isEmpty()) {
            shoppingMsg.setTextFill(javafx.scene.paint.Color.RED);
            shoppingMsg.setText("Please input all!");
            return;
        }
        if (!ConfigUtil.isFloatingPoint(goodsAmount.getText())) {
            shoppingMsg.setTextFill(javafx.scene.paint.Color.RED);
            shoppingMsg.setText("Amount must be a number!");
            return;
        }
        if (AccountData.getAccountByUid(UserData.loginUser.getUid()).getCurrentBalance() < Double.parseDouble(goodsAmount.getText())) {
            shoppingMsg.setTextFill(javafx.scene.paint.Color.RED);
            shoppingMsg.setText("Insufficient balance!");
        } else {
            AccountData.addShopping(Double.parseDouble(goodsAmount.getText()));
            shoppingMsg.setTextFill(javafx.scene.paint.Color.GREEN);
            shoppingMsg.setText("Shopping successful!");
            HistoryData.addHistory(new History("Shopping：" + goodsName.getText(), Double.parseDouble(goodsAmount.getText()), new Mission(), UserData.loginUser, ConfigUtil.dateToString(new Date())));
            goodsAmount.setText("");
            goodsName.setText("");
            loadAccount();
        }
    }

    @FXML
    void addWithdraw(ActionEvent event) {
        if (withdrawAmount.getText().isEmpty()) {
            withdrawMsg.setTextFill(javafx.scene.paint.Color.RED);
            withdrawMsg.setText("Please input amount!");
            return;
        }
        if (!ConfigUtil.isFloatingPoint(withdrawAmount.getText())) {
            withdrawMsg.setTextFill(javafx.scene.paint.Color.RED);
            withdrawMsg.setText("Amount must be a number!");
            return;
        }
        if (AccountData.getAccountByUid(UserData.loginUser.getUid()).getSavingsBalance() < Double.parseDouble(withdrawAmount.getText())) {
            withdrawMsg.setTextFill(javafx.scene.paint.Color.RED);
            withdrawMsg.setText("Insufficient balance!");
        } else if (AccountData.getAccountByUid(UserData.loginUser.getUid()).getSavingsBalance() < ConfigUtil.configList.get(0).getSavingAccountGoal()) {
            withdrawMsg.setTextFill(javafx.scene.paint.Color.RED);
            withdrawMsg.setText("Not achieving the goal!");
        } else {
            AccountData.withdraw("Saving1", Double.parseDouble(withdrawAmount.getText()));
            withdrawMsg.setTextFill(javafx.scene.paint.Color.GREEN);
            withdrawMsg.setText("Withdraw successful!");
            HistoryData.addHistory(new History("Withdraw Saving1", Double.parseDouble(withdrawAmount.getText()) + (Double.parseDouble(withdrawAmount.getText()) * ConfigUtil.SAVING_1_RATE), new Mission(), UserData.loginUser, ConfigUtil.dateToString(new Date())));
            withdrawAmount.setText("");
            loadAccount();
        }
    }

    @FXML
    void addWithdraw2(ActionEvent event) {
        if (AccountData.getAccountByUid(UserData.loginUser.getUid()).getSavingsBalance2() == 0) {
            withdrawMsg2.setTextFill(javafx.scene.paint.Color.RED);
            withdrawMsg2.setText("Insufficient balance!");
            return;
        }
        if (Integer.parseInt(ConfigUtil.saving2DaysLimit(AccountData.getAccountByUid(UserData.loginUser.getUid()).getSaving2InDate(), ConfigUtil.dateToString(new Date()))) > 0) {
            withdrawMsg2.setTextFill(javafx.scene.paint.Color.RED);
            withdrawMsg2.setText("Days not reached!");
        } else {
            HistoryData.addHistory(new History("Withdraw Saving2", AccountData.getAccountByUid(UserData.loginUser.getUid()).getSavingsBalance2() + (AccountData.getAccountByUid(UserData.loginUser.getUid()).getSavingsBalance2() * ConfigUtil.SAVING_2_RATE), new Mission(), UserData.loginUser, ConfigUtil.dateToString(new Date())));
            AccountData.withdraw("Saving2", AccountData.getAccountByUid(UserData.loginUser.getUid()).getSavingsBalance2());
            withdrawMsg2.setTextFill(javafx.scene.paint.Color.GREEN);
            withdrawMsg2.setText("Withdraw successful!");
            loadAccount();
        }

    }

    @FXML
    void logout(ActionEvent event) {
        MainApplication.changeController("login-view.fxml");
    }

    @FXML
    void receiveMission(ActionEvent event) {
        if (missionTableView.getSelectionModel().getSelectedItem() == null) {
            receiveMsg.setTextFill(javafx.scene.paint.Color.RED);
            receiveMsg.setText("Please select a mission!");
            return;
        }
        if (missionTableView.getSelectionModel().getSelectedItem().getStatus().equals("Progress")) {
            receiveMsg.setTextFill(javafx.scene.paint.Color.RED);
            receiveMsg.setText("The mission has been receive!");
            return;
        }
        if (MissionData.getChildMissionCount().size() >= 3) {
            receiveMsg.setTextFill(javafx.scene.paint.Color.RED);
            receiveMsg.setText("Exceeding maximum limit!");
        } else {
            MissionData.receiveMission(missionTableView.getSelectionModel().getSelectedItem());
            receiveMsg.setTextFill(javafx.scene.paint.Color.GREEN);
            receiveMsg.setText("Receive successful!");
            missionTableView.setItems(FXCollections.observableList(MissionData.getChildMission()));
            missionTableView.refresh();
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
        clearMsg();
        loadAccount();
        changePane(true, false, false);
    }

    private void loadAccount() {
        //load balance
        balanceLab.setText("$" + String.format("%.2f", AccountData.getAccountByUid(UserData.loginUser.getUid()).getCurrentBalance()));
        balance2Lab.setText("$" + String.format("%.2f", AccountData.getAccountByUid(UserData.loginUser.getUid()).getSavingsBalance2()));
        //load goals
        goalsLab.setText("$" + String.format("%.2f", AccountData.getAccountByUid(UserData.loginUser.getUid()).getSavingsBalance()) + "/" + String.format("%.2f", ConfigUtil.configList.get(0).getSavingAccountGoal()));
        goalsBar.setProgress(AccountData.getAccountByUid(UserData.loginUser.getUid()).getSavingsBalance() / ConfigUtil.configList.get(0).getSavingAccountGoal());
        inDateLab.setText(AccountData.getAccountByUid(UserData.loginUser.getUid()).getSaving2InDate());
        if (AccountData.getAccountByUid(UserData.loginUser.getUid()).getSaving2InDate().isEmpty() || AccountData.getAccountByUid(UserData.loginUser.getUid()).getSaving2InDate().equals("null")) {
            daysLimitLab.setText("");
        } else {
            daysLimitLab.setText(ConfigUtil.saving2DaysLimit(AccountData.getAccountByUid(UserData.loginUser.getUid()).getSaving2InDate(), ConfigUtil.dateToString(new Date())));
        }
    }

    @FXML
    void toMissionPane(ActionEvent event) {
        missionTableView.setItems(FXCollections.observableList(MissionData.getChildMission()));
        missionTableView.refresh();
        changePane(false, true, false);
    }

    @FXML
    void transfer(ActionEvent event) {
        if (transferText.getText().isEmpty()) {
            transferMsg.setTextFill(javafx.scene.paint.Color.RED);
            transferMsg.setText("Please input amount!");
            return;
        }
        if (!ConfigUtil.isFloatingPoint(transferText.getText())) {
            transferMsg.setTextFill(javafx.scene.paint.Color.RED);
            transferMsg.setText("Amount must be a number!");
            return;
        }
        if (AccountData.getAccountByUid(UserData.loginUser.getUid()).getCurrentBalance() < Double.parseDouble(transferText.getText())) {
            transferMsg.setTextFill(javafx.scene.paint.Color.RED);
            transferMsg.setText("Insufficient balance!");
        } else {
            if (chooseToggleGroup.getSelectedToggle().getUserData().toString().equals("Saving2")) {
                if (Double.parseDouble(transferText.getText()) < ConfigUtil.SAVING_2_MIN_AMOUNT) {
                    transferMsg.setTextFill(javafx.scene.paint.Color.RED);
                    transferMsg.setText("Minimum deposit of 200!");
                    return;
                } else if (AccountData.getAccountByUid(UserData.loginUser.getUid()).getSavingsBalance2() > 0) {
                    transferMsg.setTextFill(javafx.scene.paint.Color.RED);
                    transferMsg.setText("Account already has an amount!");
                    return;
                } else {
                    AccountData.transfer(chooseToggleGroup.getSelectedToggle().getUserData().toString(), Double.parseDouble(transferText.getText()));
                }
            } else {
                AccountData.transfer(chooseToggleGroup.getSelectedToggle().getUserData().toString(), Double.parseDouble(transferText.getText()));
            }
            transferMsg.setTextFill(javafx.scene.paint.Color.GREEN);
            transferMsg.setText("Transfer successful!");
            HistoryData.addHistory(new History("Transfer " + chooseToggleGroup.getSelectedToggle().getUserData().toString(), Double.parseDouble(transferText.getText()), new Mission(), UserData.loginUser, ConfigUtil.dateToString(new Date())));
            transferText.setText("");

            loadAccount();
        }
    }

    @FXML
    void showTips(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Tips");
        dialog.getDialogPane().setPrefSize(400, 200);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        Label label = new Label("1.SavingAccount 1 needs to reach the target amount before it can be withdrawn, with an interest rate of 10%, which can be obtained upon withdrawal.\n" +
                "2.SavingAccount2 can be deposited when it is empty, with a 30 day limit and a minimum deposit of $200. The interest rate is 20%, and all funds must be withdrawn upon maturity.");
        label.setWrapText(true);
        dialog.getDialogPane().setContent(label);

        dialog.showAndWait();
    }

    @FXML
    void initialize() {
        loadAccount();
        clearMsg();
        //load username
        loginUserName.setText(UserData.loginUser.getUsername());
        //load mission table
        mid.setCellValueFactory(new PropertyValueFactory<>("mid"));
        missionName.setCellValueFactory(new PropertyValueFactory<>("missionName"));
        missionAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        missionDesc.setCellValueFactory(new PropertyValueFactory<>("missionDesc"));
        missionStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        missionParent.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getParent().getUsername()));
        missionTableView.setItems(FXCollections.observableList(MissionData.getChildMission()));
        missionTableView.setPlaceholder(new StackPane(new Label("No data yet!")));
        //load history table
        historyAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        historyDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        historyType.setCellValueFactory(new PropertyValueFactory<>("historyType"));
        historyTableView.setItems(FXCollections.observableList(HistoryData.getLoginUserHistoryList()));
        historyTableView.setPlaceholder(new StackPane(new Label("No data yet!")));

        chooseToggleGroup = new ToggleGroup();
        radio1.setToggleGroup(chooseToggleGroup);
        radio1.setUserData("Saving1");
        radio2.setToggleGroup(chooseToggleGroup);
        radio2.setUserData("Saving2");
        radio1.setSelected(true);
        changePane(true, false, false);
    }


    public void clearMsg() {
        shoppingMsg.setText("");
        transferMsg.setText("");
        withdrawMsg.setText("");
        receiveMsg.setText("");
        withdrawMsg2.setText("");
    }

    public void changePane(boolean home, boolean mission, boolean history) {
        homePane.setVisible(home);
        missionPane.setVisible(mission);
        historyPane.setVisible(history);
    }
}
