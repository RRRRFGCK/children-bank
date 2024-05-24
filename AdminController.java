package com.system.controller;

import com.system.bean.User;
import com.system.database.MissionData;
import com.system.database.UserData;
import com.system.utils.ConfigUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Comparator;
import java.util.List;

public class AdminController {
    @FXML
    private Button addBtn;
    @FXML
    private TextField addPassword;
    @FXML
    private Pane addUserPane;
    @FXML
    private TextField addUsername;
    @FXML
    private Pane userListPane;

    private ToggleGroup userTypeToggleGroup;
    @FXML
    private TableColumn<User, String> password;

    @FXML
    private TableColumn<User, Integer> uid;

    @FXML
    private TableColumn<User, String> username;

    @FXML
    private TableColumn<User, String> userType;
    @FXML
    private RadioButton userType1;

    @FXML
    private RadioButton userType2;

    @FXML
    private TableView<User> tableView;
    @FXML
    private Label msgLabel;
    @FXML
    private Label updateMsg;
    @FXML
    private TextField updatePasswordText;
    @FXML
    void updatePassword(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            updateMsg.setTextFill(javafx.scene.paint.Color.RED);
            updateMsg.setText("Choose!");
            return;
        }
        User user = tableView.getSelectionModel().getSelectedItem();
        if (updatePasswordText.getText().isEmpty()) {
            updateMsg.setTextFill(javafx.scene.paint.Color.RED);
            updateMsg.setText("Please enter new password");
            return;
        }
        if(!ConfigUtil.hasLetterAndDigit(updatePasswordText.getText())) {
            updateMsg.setText("Password must contain letter and digit");
            updateMsg.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }
        user.setPassword(updatePasswordText.getText());
        UserData.updateUser(user);
        updateMsg.setText("Update password successfully");
        updateMsg.setTextFill(javafx.scene.paint.Color.GREEN);
        updatePasswordText.setText("");
        tableView.setItems(FXCollections.observableList(UserData.userList));
        tableView.refresh();
    }
    @FXML
    void addUser(ActionEvent event) {
        if (addUsername.getText().isEmpty() || addPassword.getText().isEmpty()) {
            msgLabel.setText("Please enter username and password");
            msgLabel.setTextFill(javafx.scene.paint.Color.RED);
            msgLabel.setVisible(true);
            return;
        }
        if(!ConfigUtil.hasLetterAndDigit(addPassword.getText())) {
            msgLabel.setText("Password must contain letter and digit");
            msgLabel.setTextFill(javafx.scene.paint.Color.RED);
            msgLabel.setVisible(true);
            return;
        }
        if(UserData.userExists(addUsername.getText())){
            msgLabel.setText("Username already exists");
            msgLabel.setTextFill(javafx.scene.paint.Color.RED);
            msgLabel.setVisible(true);
            return;
        }
        User userWithMaxUid = UserData.userList.stream()
                .max(Comparator.comparingInt(User::getUid))
                .orElse(null);
        assert userWithMaxUid != null;
        User user = new User(userWithMaxUid.getUid() + 1, addUsername.getText(), addPassword.getText(), userTypeToggleGroup.getSelectedToggle().getUserData().toString());
        msgLabel.setText("Add user successfully");
        msgLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        msgLabel.setVisible(true);
        UserData.addUser(user);
        addUsername.setText("");
        addPassword.setText("");
        tableView.refresh();
    }

    @FXML
    void logout(ActionEvent event) {
        MainApplication.changeController("login-view.fxml");
    }
    @FXML
    void toAddUser(ActionEvent event) {
        changePane(true,false);
    }

    @FXML
    void toHome(ActionEvent event) {
        changePane(false,true);
        tableView.refresh();
    }

    @FXML
    void initialize() {
        List<User> users = UserData.userList;
        uid.setCellValueFactory(new PropertyValueFactory<>("uid"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        userType.setCellValueFactory(new PropertyValueFactory<>("userType"));
        tableView.setItems(FXCollections.observableList(users));
        tableView.setPlaceholder(new StackPane(new Label("No data yet!")));

        userTypeToggleGroup = new ToggleGroup();
        userType1.setToggleGroup(userTypeToggleGroup);
        userType1.setUserData("parent");
        userType2.setToggleGroup(userTypeToggleGroup);
        userType2.setUserData("child");
        userType2.setSelected(true);

        changePane(false,true);
    }

    public void changePane(boolean user,boolean userList) {
        addUserPane.setVisible(user);
        userListPane.setVisible(userList);
    }
}