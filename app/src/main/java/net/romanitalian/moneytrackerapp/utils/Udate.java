package net.romanitalian.moneytrackerapp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Udate {
    final public static String dateFormat = "dd-MM-yyyy";

    public static Date getDateNow() {
        return Calendar.getInstance().getTime();
    }

    public static String getDateNowToString() {
        Locale locale = new Locale("ru");
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd", locale);
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }
}
