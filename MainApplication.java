package com.system.controller;

import com.system.database.BaseData;
import com.system.utils.ConfigUtil;
import com.system.utils.ImgUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        MainApplication.stage = stage;
        changeController("login-view.fxml");
    }

    public static void changeController(String fxmlName) {
        BaseData.init();
        Parent root = null;
        try {
            root = FXMLLoader.load(MainApplication.class.getResource(fxmlName));
            stage.setTitle(ConfigUtil.SYSTEM_NAME);
            stage.getIcons().add(ImgUtil.LOGO_ICON);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}