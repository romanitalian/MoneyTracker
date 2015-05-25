package net.romanitalian.moneytrackerapp.utils;

import java.util.Calendar;
import java.util.Date;

public class Udate {
    final public static String dateFormat = "dd-MM-yyyy";

    public static Date getDateNow() {
//        DateFormat df = new SimpleDateFormat(dateFormat, new Locale("ru"));
        Date nowCalendar = Calendar.getInstance().getTime();
        return nowCalendar;
//        return df.format(nowCalendar);
    }
}
