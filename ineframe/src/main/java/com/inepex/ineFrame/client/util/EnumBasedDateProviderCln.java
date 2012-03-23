package com.inepex.ineFrame.client.util;

import java.util.Date;

import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.i18n.client.constants.TimeZoneConstants;
import com.google.inject.Singleton;
import com.inepex.ineFrame.server.util.TimeZoneEnum;
import com.inepex.ineFrame.shared.util.DateHelper;
import com.inepex.ineFrame.shared.util.DateProvider;


/**
 * 
 * WARNING! you should initialize before using (and everytime when windows is reloaded)
 * 
 * Based on {@link TimeZoneEnum}'s tzJson field or GWT's {@link TimeZoneConstants}
 * 
 */
@Singleton
public class EnumBasedDateProviderCln implements DateProvider {
	
	private TimeZone tz = null; 

	/**
	 * 
	 * @param tzJson - one of {@link TimeZoneEnum}'s tzJson field or GWT's {@link TimeZoneConstants}
	 */
	public void setTimeZoneJson(String tzJson) {
		this.tz=TimeZone.createTimeZone(tzJson);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public Date getDate(Long dateInUTC) {
		if(tz==null)
			throw new IllegalStateException("EnumBasedDateProviderCln is not initialized");
		
		Date date = new Date(dateInUTC);
		Long corectedDate =
				dateInUTC
				+ (-1*tz.getOffset(date) + date.getTimezoneOffset()) * DateHelper.minuteInMs;
		
		return new Date(corectedDate);
	}

	
	@Override
	@SuppressWarnings("deprecation")
	public Date whatMeansTyped(Long dateInUTC) {
		if(tz==null)
			throw new IllegalStateException("EnumBasedDateProviderCln is not initialized");
		
		Date date = new Date(dateInUTC);
		Long corectedDate = 
			dateInUTC
			- (date.getTimezoneOffset() - tz.getOffset(date)) * DateHelper.minuteInMs;
		
		return new Date(corectedDate);
	}
}
