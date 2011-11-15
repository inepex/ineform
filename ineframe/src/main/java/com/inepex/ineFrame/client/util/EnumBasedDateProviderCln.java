package com.inepex.ineFrame.client.util;

import java.util.Date;

import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.i18n.client.constants.TimeZoneConstants;
import com.google.gwt.user.client.Cookies;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.server.util.TimeZoneEnum;
import com.inepex.ineFrame.shared.util.DateHelper;
import com.inepex.ineFrame.shared.util.DateProvider;


/**
 * 
 * WARNING! the the time zone was set will be stored in cookie
 * 
 * Based on {@link TimeZoneEnum}'s tzJson field or GWT's {@link TimeZoneConstants}
 * 
 */
@Singleton
public class EnumBasedDateProviderCln implements DateProvider {
	
	public static final String TIMEZONE_COOKIE_ID="EnumBasedDateProviderCln_timezoneJson";
	private TimeZone tz = null; 

	@Inject
	EnumBasedDateProviderCln(){
		String tzJson = Cookies.getCookie(TIMEZONE_COOKIE_ID);
		if(tzJson!=null)
			setTimeZoneEnumAndTimeZone(tzJson);
	}

	/**
	 * 
	 * @param tzJson - one of {@link TimeZoneEnum}'s tzJson field or GWT's {@link TimeZoneConstants}
	 */
	public void setTimeZoneEnumAndTimeZone(String tzJson) {
		this.tz=TimeZone.createTimeZone(tzJson);
		
		Cookies.setCookie(TIMEZONE_COOKIE_ID,
				tzJson, DateHelper.addDaysSafe(new Date(), 50));
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
