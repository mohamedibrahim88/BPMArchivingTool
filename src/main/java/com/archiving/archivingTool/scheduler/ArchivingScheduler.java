package com.archiving.archivingTool.scheduler;

import com.archiving.archivingTool.util.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

@Component
public class ArchivingScheduler {


    @Scheduled(cron = "${my.scheduler.cron}")
    public void runArchiveTask() {
        LocalDateTime now = LocalDateTime.now();

        System.out.println("Scheduled task executed...");
        System.out.println("Current DateTime: " + DateUtil.formatDateTime(now));
        System.out.println("Year: " + DateUtil.getYear(now));
        System.out.println("Month: " + DateUtil.getMonthValue(now) + " (" + DateUtil.getMonthName(now) + ")");
        System.out.println("Day of Month: " + DateUtil.getDayOfMonth(now));
        System.out.println("Weekday: " + DateUtil.getWeekDay(now));

        Date dateNow = new Date();

        System.out.println("Scheduled task executed...");
        System.out.println("Current DateTime: " + DateUtil.formatDateTime(dateNow));
        System.out.println("Year: " + DateUtil.getYear(dateNow));
        System.out.println("Month: " + DateUtil.getMonthValue(dateNow) + " (" + DateUtil.getMonthName(dateNow) + ")");
        System.out.println("Day of Month: " + DateUtil.getDayOfMonth(dateNow));
        System.out.println("Weekday: " + DateUtil.getWeekDay(dateNow));


        // logic : retrieve all archiving config based on data
    }
}
