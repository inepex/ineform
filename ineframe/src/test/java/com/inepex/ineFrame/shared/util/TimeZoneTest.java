package com.inepex.ineFrame.shared.util;

import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dev.util.collect.HashSet;

public class TimeZoneTest {

	@Test
	public void testTimeZones() {
		HashSet<String> availableIds = new HashSet<String>();
		for(String str : TimeZone.getAvailableIDs()) {
			availableIds.add(str);
		}
		
		for(TimeZoneEnum tz : TimeZoneEnum.values()) {
			if(!availableIds.contains(tz.tzId)) {
				Assert.fail("Invalid timezone: "+tz.tzId);
			}
		}
	}
	
}
