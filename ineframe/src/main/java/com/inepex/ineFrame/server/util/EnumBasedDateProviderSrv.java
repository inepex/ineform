package com.inepex.ineFrame.server.util;

import java.util.Date;
import java.util.TimeZone;

import com.google.inject.servlet.SessionScoped;
import com.inepex.ineFrame.shared.util.DateHelper;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineFrame.shared.util.TimeZoneEnum;

/**
 * WARNING! set the time zone with setTimeZoneEnumAndTimeZone(TimeZoneEnum timeZoneEnum) method!!!
 * if you forget it, the time counting methods will throw null pointer exceptions!
 * 
 */
@SessionScoped
public class EnumBasedDateProviderSrv implements DateProvider {
	
	private TimeZoneEnum timeZoneEnum;
	private TimeZone tz=null;
	
	public void setTimeZoneEnumAndTimeZone(TimeZoneEnum timeZoneEnum) {
		this.timeZoneEnum = timeZoneEnum;
		tz=TimeZone.getTimeZone(timeZoneEnum.timeZoneId);
	}
	
	public TimeZoneEnum getTimeZoneEnum() {
		return timeZoneEnum;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Date getDate(Long dateInUTC) {
		Date date = new Date(dateInUTC);
		Long corectedDate =
				dateInUTC
				+ date.getTimezoneOffset() * DateHelper.minuteInMs
				+ tz.getOffset(dateInUTC);
		
		return new Date(corectedDate);
	}

	
	@Override
	@SuppressWarnings("deprecation")
	public Date whatMeansTyped(Long dateInUTC) {
		Date date = new Date(dateInUTC);
		Long corectedDate = 
			dateInUTC
			- date.getTimezoneOffset() * DateHelper.minuteInMs
			- tz.getOffset(dateInUTC);
		
		return new Date(corectedDate);
	}
}
