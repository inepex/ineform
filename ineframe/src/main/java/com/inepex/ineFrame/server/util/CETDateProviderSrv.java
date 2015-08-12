package com.inepex.ineFrame.server.util;

import java.util.Date;
import java.util.TimeZone;

import com.inepex.ineFrame.shared.util.date.DateHelper;
import com.inepex.ineFrame.shared.util.date.DateProvider;

public class CETDateProviderSrv implements DateProvider {

    private static final TimeZone tz = TimeZone.getTimeZone("CET");

    @SuppressWarnings("deprecation")
    @Override
    public Date getDate(Long dateInUTC) {
        Date date = new Date(dateInUTC);
        Long corectedDate =
            dateInUTC + date.getTimezoneOffset() * DateHelper.minuteInMs + tz.getOffset(dateInUTC);

        return new Date(corectedDate);
    }

    @Override
    @SuppressWarnings("deprecation")
    public Date whatMeansTyped(Long dateInUTC) {
        Date date = new Date(dateInUTC);
        Long corectedDate =
            dateInUTC - date.getTimezoneOffset() * DateHelper.minuteInMs - tz.getOffset(dateInUTC);

        return new Date(corectedDate);
    }
}
