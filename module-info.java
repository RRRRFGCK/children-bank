module com.system.childbank {
    requires javafx.controls;
    requires javafx.fxml;
    requires fastjson;
    requires javafx.base;


    opens com.system.controller to javafx.fxml;
    opens com.system.bean to fastjson,javafx.base;

    exports com.system.controller;
    exports com.system.bean;
}