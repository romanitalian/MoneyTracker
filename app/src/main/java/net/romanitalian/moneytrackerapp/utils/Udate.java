package net.romanitalian.moneytrackerapp.utils;

import java.util.Calendar;
import java.util.Date;

public class Udate {
    final public static String dateFormat = "dd-MM-yyyy";

    public static Date getDateNow() {
        Date nowCalendar = Calendar.getInstance().getTime();
        return nowCalendar;
    }
}
