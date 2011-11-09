package com.inepex.ineFrame.client.util;

import java.util.Date;

import com.google.gwt.i18n.client.TimeZone;
import com.google.inject.Singleton;
import com.inepex.ineFrame.shared.util.DateHelper;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineFrame.shared.util.TimeZoneEnum;


/**
 * WARNING! set the time zone with setTimeZoneEnumAndTimeZone(TimeZoneEnum timeZoneEnum) method!!!
 * if you forget it, the time counting methods will throw null pointer exceptions!
 * 
 */
@Singleton
public class EnumBasedDateProviderCln implements DateProvider {
	
	private TimeZoneEnum timeZoneEnum;
	private TimeZone tz = null; 

	public void setTimeZoneEnumAndTimeZone(TimeZoneEnum timeZoneEnum) {
		this.timeZoneEnum = timeZoneEnum;
		this.tz=TimeZone.createTimeZone(timeZoneEnum.timeZoneId);
	}
	
	public TimeZoneEnum getTimeZoneEnum() {
		return timeZoneEnum;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public Date getDate(Long dateInUTC) {
		Date date = new Date(dateInUTC);
		Long corectedDate =
				dateInUTC
				+ (-1*tz.getOffset(date) + date.getTimezoneOffset()) * DateHelper.minuteInMs;
		
		return new Date(corectedDate);
	}

	
	@Override
	@SuppressWarnings("deprecation")
	public Date whatMeansTyped(Long dateInUTC) {
		Date date = new Date(dateInUTC);
		Long corectedDate = 
			dateInUTC
			- (date.getTimezoneOffset() - tz.getOffset(date)) * DateHelper.minuteInMs;
		
		return new Date(corectedDate);
	}
}
