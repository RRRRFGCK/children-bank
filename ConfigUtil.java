package com.system.utils;

import com.system.bean.Config;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConfigUtil {
    public static String SYSTEM_NAME = "Children Bank System";
    public static double SAVING_1_RATE = 0.1;
    public static double SAVING_2_RATE = 0.2;
    public static int SAVING_2_DAYS = 30;
    public static double SAVING_2_MIN_AMOUNT = 200.0;
    public static List<Config> configList = new ArrayList<Config>();

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String saving2DaysLimit(String dateStr1, String dateStr2) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date1 = LocalDate.parse(dateStr1, dateFormatter);
        LocalDate date2 = LocalDate.parse(dateStr2, dateFormatter);

        return Long.toString(SAVING_2_DAYS - ChronoUnit.DAYS.between(date1, date2));
    }

    public static boolean isFloatingPoint(String input) {
        return input.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public static boolean hasLetterAndDigit(String input) {
        return input.matches(".*\\d.*") && input.matches(".*[a-zA-Z].*");
    }


}
