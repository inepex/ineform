package com.inepex.ineFrame.client.util;

import java.util.Date;

import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.user.client.Cookies;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.shared.util.DateHelper;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineFrame.shared.util.TimeZoneEnum;


/**
 * 
 * WARNING! set the time zone setting will be stored in cookie
 * 
 */
@Singleton
public class EnumBasedDateProviderCln implements DateProvider {
	
	public static final String TIMEZONE_COOKIE_ID="EnumBasedDateProviderCln_last_used_timezone";
	
	private TimeZoneEnum timeZoneEnum;
	private TimeZone tz = null; 

	@Inject
	EnumBasedDateProviderCln(){
		try {
			String tzFromCookie = Cookies.getCookie(TIMEZONE_COOKIE_ID);
			if(tzFromCookie!=null)
				setTimeZoneEnumAndTimeZone(TimeZoneEnum.valueOf(tzFromCookie));
				
		} catch (IllegalArgumentException e) {
			//cookie is invalid?
			Cookies.removeCookie(TIMEZONE_COOKIE_ID);
		}
	}
	
	public void setTimeZoneEnumAndTimeZone(TimeZoneEnum timeZoneEnum) {
		this.timeZoneEnum = timeZoneEnum;
		this.tz=TimeZone.createTimeZone(timeZoneEnum.tzJson);
		
		Cookies.setCookie(TIMEZONE_COOKIE_ID,
				timeZoneEnum.name(), DateHelper.addDaysSafe(new Date(), 50));
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
