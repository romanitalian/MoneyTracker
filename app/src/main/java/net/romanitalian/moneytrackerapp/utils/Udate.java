package net.romanitalian.moneytrackerapp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Udate {
    final public static String dateFormat = "dd-MM-yyyy";

    public static String getDateNow() {
        DateFormat df = new SimpleDateFormat(dateFormat, new Locale("ru"));
        Date nowCalendar = Calendar.getInstance().getTime();
        return df.format(nowCalendar);
    }
}
