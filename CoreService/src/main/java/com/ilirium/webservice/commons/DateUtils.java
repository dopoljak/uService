package com.ilirium.webservice.commons;

import java.text.SimpleDateFormat;
import java.time.*;
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

        Duration duration = Duration.ofMillis(different);
        String elapsedTimeFormated = String.format(
                "%d days, %d hours, %d minutes, %d seconds",
                duration.toDays(),
                duration.minusDays(duration.toDays()).toHours(),
                duration.minusHours(duration.toHours()).toMinutes(),
                duration.minusMinutes(duration.toMinutes()).getSeconds());
        return elapsedTimeFormated;
    }

    public Date getTodayWithMidnightTime() {
        return toDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
    }

    public Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
