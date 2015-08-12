package com.inepex.ineFrame.shared.util;

import java.util.TimeZone;

import org.junit.Assert;

import com.google.gwt.dev.util.collect.HashSet;
import com.inepex.ineFrame.server.util.SimpleTimeZoneEnum;

public class SimpleTimeZoneEnumTest {

    // @Test
    public void ceckTimeZones() {
        HashSet<String> availableIds = new HashSet<String>();
        for (String str : TimeZone.getAvailableIDs()) {
            availableIds.add(str);
        }

        for (SimpleTimeZoneEnum tz : SimpleTimeZoneEnum.values()) {
            if (!availableIds.contains(tz.tzId)) {
                Assert.fail("Invalid timezone: " + tz.tzId);
            }

            TimeZone javaTimeZone = TimeZone.getTimeZone(tz.tzId);

            int javaOffsetInMin = javaTimeZone.getRawOffset() / 60000;
            int gwtConstOffset = getGwtconstOffsetInMin(tz.tzJson);

            Assert.assertEquals(
                "Invalid offset for: " + tz.displayName,
                javaOffsetInMin,
                gwtConstOffset);

            String pref = namePrefixFromOffset(javaOffsetInMin);
            Assert.assertEquals(
                "Invalid displ name: " + tz.displayName,
                pref,
                tz.displayName.substring(0, tz.displayName.indexOf(")") + 1));
        }
    }

    // @Test
    public void testNamePref() {
        Assert.assertEquals("(GMT)", namePrefixFromOffset(0));
        Assert.assertEquals("(GMT-01:00)", namePrefixFromOffset(-60));
        Assert.assertEquals("(GMT-02:30)", namePrefixFromOffset(-120 - 30));
        Assert.assertEquals("(GMT+01:00)", namePrefixFromOffset(60));
        Assert.assertEquals("(GMT+02:30)", namePrefixFromOffset(120 + 30));
        Assert.assertEquals("(GMT+13:00)", namePrefixFromOffset(13 * 60));
        Assert.assertEquals("(GMT-13:00)", namePrefixFromOffset(-13 * 60));
    }

    private String namePrefixFromOffset(int offSetInMin) {
        if (offSetInMin == 0)
            return "(GMT)";

        String sign = (offSetInMin < 0) ? "-" : "+";
        int absOffSet = Math.abs(offSetInMin);
        String hour = "" + (absOffSet / 60);
        if (hour.length() < 2)
            hour = "0" + hour;

        String min = "" + (absOffSet % 60);

        if (min.length() < 2)
            min = "0" + min;

        return "(GMT" + sign + hour + ":" + min + ")";
    }

    // @Test
    public void testGetGwtconstOffsetInMin() {
        Assert
            .assertEquals(
                120,
                getGwtconstOffsetInMin("{\"transitions\": [], \"names\": [\"CAT\", \"Central Africa Time\"], \"id\": \"Africa/Harare\", \"std_offset\": 120}"));
        Assert
            .assertEquals(
                -120,
                getGwtconstOffsetInMin("{\"transitions\": [], \"names\": [\"ex\"], \"id\": \"Ex\", \"std_offset\": -120}"));
    }

    private int getGwtconstOffsetInMin(String tzJson) {
        int from = tzJson.lastIndexOf(":") + 2;
        int to = tzJson.lastIndexOf("}");

        return Integer.parseInt(tzJson.substring(from, to));
    }
}
