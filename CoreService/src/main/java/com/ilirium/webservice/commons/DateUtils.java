package com.ilirium.webservice.commons;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static final String getFormatedCurrentTimeMillis() {
        return formatMillis(System.currentTimeMillis());
    }

    public static String formatMillis(long currentTimeMillis) {
        return SIMPLE_DATE_FORMAT.format(new Date(currentTimeMillis));
    }

    public static String formatElapsedMillis(long different) {

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        String elapsedTimeFormated = String.format(
                "%d days, %d hours, %d minutes, %d seconds",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        return elapsedTimeFormated;
    }

    public static Date getTodayWithMidnightTime() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        return today.getTime();
    }
}
