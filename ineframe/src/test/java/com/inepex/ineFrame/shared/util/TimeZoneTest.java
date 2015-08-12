package com.inepex.ineFrame.shared.util;

import java.util.HashSet;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

import com.inepex.ineFrame.server.util.TimeZoneEnum;

public class TimeZoneTest {

    @Test
    public void testTimeZones() {
        HashSet<String> availableIds = new HashSet<String>();
        for (String str : TimeZone.getAvailableIDs()) {
            availableIds.add(str);
        }

        for (TimeZoneEnum tz : TimeZoneEnum.values()) {
            if (!availableIds.contains(tz.tzId)) {
                Assert.fail("Invalid timezone: " + tz.tzId);
            }
        }
    }

}
