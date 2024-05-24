package com.system.utils;

import javafx.scene.image.Image;

public class ImgUtil {
    public static String LOGIN_IMG_PATH = "/img/login.jpg";
    public static String LOGO_IMG_PATH = "/img/logo.jpg";

    public static Image LOGO_ICON = getIcon(LOGO_IMG_PATH);
    public static Image LOGIN_ICON = getIcon(LOGIN_IMG_PATH);

    public static Image getIcon(String imgPath) {
        Image icon = new Image(ImgUtil.class.getResourceAsStream(imgPath));
        return icon;
    }
}
