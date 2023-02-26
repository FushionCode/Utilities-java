package com.fushioncoder.Utility_Service.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class DateTimeUtils {
    private static final SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss"
    );
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss"
    );

    public static LocalDate getLocalDate(String date) {
        LocalDate dt = null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            dt = LocalDate.parse(date, dtf);
        } catch (Exception ex) {
            dtf = DateTimeFormatter.ofPattern("yy/MM/dd");
            try {
                dt = LocalDate.parse(date, dtf);
                if ((dt.getYear() - LocalDate.now().getYear()) >= 10) {
                    dt = dt.minusYears(100);
                }
            } catch (Exception ex1) {
                dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
                try {
                    dt = LocalDate.parse(date, dtf);
                } catch (Exception ex2) {
                    dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    try {
                        dt = LocalDate.parse(date, dtf);
                    } catch (Exception ex3) {
                        dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        try {
                            dt = LocalDate.parse(date, dtf);
                        } catch (Exception ex4) {
                            dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
                            try {
                                dt = LocalDate.parse(date, dtf);
                            } catch (Exception ex5) {
                                dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                                try {
                                    dt = LocalDate.parse(date, dtf);
                                } catch (Exception ex6) {
                                    System.out.println("unable to parse date ::: " + date);
                                }
                            }
                        }
                    }
                }
            }
        }
        return dt;
    }

    public static String getDateTime() {
        ZoneId zonedId = ZoneId.of("Africa/Lagos");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss").withZone(zonedId);
        LocalDateTime today = LocalDateTime.now();
        return today.format(formatter);
    }

    public static LocalDateTime addMinutesToNowDate(Integer somMinutes)
            throws ParseException {
        return LocalDateTime.now().plusMinutes(somMinutes);
    }

    public static String getDateTime(LocalDateTime localDateTime) {
        String nowNow = localDateTime.format(dateTimeFormatter);
        return nowNow;
    }

    public static Date getDateTimeAsDate(LocalDateTime localDateTime) {
        try {
            return simpleDateTimeFormat.parse(getDateTime(localDateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, LocalDateTime> getTodaysTimeInterval() {
        LocalDateTime nowNow = LocalDateTime.now();
        LocalDateTime startTimeLdt = LocalDateTime.of(
                nowNow.getYear(),
                nowNow.getMonth(),
                nowNow.getDayOfMonth(),
                0,
                0,
                0
        );
        LocalDateTime endTimeLdt = LocalDateTime.of(
                nowNow.getYear(),
                nowNow.getMonth(),
                nowNow.getDayOfMonth(),
                23,
                59,
                59
        );
        HashMap<String, LocalDateTime> dt = new HashMap<>();

        dt.put("start", startTimeLdt);
        dt.put("end", endTimeLdt);

        return dt;
    }

    public static LocalDateTime convertStringToDate(String theDate) throws ParseException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        return LocalDateTime.parse(theDate, formatter);
    }

}
