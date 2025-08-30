package com.archiving.archivingTool.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    public static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static int getYear() {
        return LocalDateTime.now().getYear();
    }

    public static int getMonthValue() {
        return LocalDateTime.now().getMonthValue();
    }

    public static String getMonthName() {
        return LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL, DEFAULT_LOCALE);
    }

    public static int getDayOfMonth() {
        return LocalDateTime.now().getDayOfMonth();
    }

    public static String getWeekDay() {
        return LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL, DEFAULT_LOCALE);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static int getYear(LocalDateTime dateTime) {
        return dateTime.getYear();
    }

    public static int getMonthValue(LocalDateTime dateTime) {
        return dateTime.getMonthValue();
    }

    public static String getMonthName(LocalDateTime dateTime) {
        return dateTime.getMonth().getDisplayName(TextStyle.FULL, DEFAULT_LOCALE);
    }

    public static int getDayOfMonth(LocalDateTime dateTime) {
        return dateTime.getDayOfMonth();
    }

    public static String getWeekDay(LocalDateTime dateTime) {
        return dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, DEFAULT_LOCALE);
    }


    private static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String formatDateTime(Date date) {
        LocalDateTime dateTime = toLocalDateTime(date);
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static int getYear(Date date) {
        return toLocalDateTime(date).getYear();
    }

    public static int getMonthValue(Date date) {
        return toLocalDateTime(date).getMonthValue();
    }

    public static String getMonthName(Date date) {
        return toLocalDateTime(date).getMonth().getDisplayName(TextStyle.FULL, DEFAULT_LOCALE);
    }

    public static int getDayOfMonth(Date date) {
        return toLocalDateTime(date).getDayOfMonth();
    }

    public static String getWeekDay(Date date) {
        return toLocalDateTime(date).getDayOfWeek().getDisplayName(TextStyle.FULL, DEFAULT_LOCALE);
    }
}
