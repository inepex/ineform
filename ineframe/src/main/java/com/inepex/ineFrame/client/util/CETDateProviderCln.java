package com.inepex.ineFrame.client.util;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.i18n.client.constants.TimeZoneConstants;
import com.inepex.ineFrame.shared.util.DateHelper;
import com.inepex.ineFrame.shared.util.DateProvider;

public class CETDateProviderCln implements DateProvider {
	private final static TimeZoneConstants tsc = GWT.create(TimeZoneConstants.class);
	private final static TimeZone tz = TimeZone.createTimeZone(tsc.europeBudapest());

	public CETDateProviderCln() {
	}
	
	@SuppressWarnings("deprecation")
	@Override
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
