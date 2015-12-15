package com.inepex.ineFrame.shared.util.date;

import java.util.Date;

public interface DateProvider {

    public Date getDate(Long dateInUTC);

    public Date whatMeansTyped(Long localDate);

    public int getTimeZoneOffset();

}
