package com.system.controller;

import com.system.bean.User;
import com.system.database.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class LoginController {
    @FXML
    private Label errorMsg;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void loginAction(ActionEvent event) {
        if (username.getText().equals("admin") && password.getText().equals("admin")) {
            errorMsg.setText("");
            errorMsg.setVisible(false);
            MainApplication.changeController("admin-view.fxml");
            return;
        }
        User user = UserData.getUser(username.getText(), password.getText());
        if (null == user) {
            errorMsg.setText("Invalid username or password");
            errorMsg.setVisible(true);
        } else {
            errorMsg.setText("");
            errorMsg.setVisible(false);
            UserData.loginUser = user;
            if (UserData.loginUser.getUserType().equals("parent")) {
                MainApplication.changeController("parent-view.fxml");
            } else {
                MainApplication.changeController("child-view.fxml");
            }
        }


    }

    @FXML
    void initialize() {

    }

}