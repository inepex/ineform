package com.inepex.ineFrame.server.util;

import java.util.Calendar;

public class CalendarHelper {

    public static void resetCalendarHourMinSec(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }
}
