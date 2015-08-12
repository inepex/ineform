package com.inepex.ineFrame.shared.util.date;

import org.junit.Assert;
import org.junit.Test;

public class DateHelperTest {

    @Test
    public void approachDurationTest() {

        for (long magnitude : new long[] {
            1,
            10,
            DateHelper.secondInMs,
            DateHelper.secondInMs * 5,
            DateHelper.secondInMs * 10,
            DateHelper.minuteInMs,
            DateHelper.minuteInMs * 5,
            DateHelper.minuteInMs * 10,
            DateHelper.minuteInMs * 15,
            DateHelper.dayInMs }) {
            Assert.assertEquals(1, DateHelper.divAndRoundToAvoidNull(magnitude, 0L));
        }

        for (long magnitude : new long[] {
            DateHelper.secondInMs,
            DateHelper.minuteInMs,
            DateHelper.dayInMs,
            DateHelper.secondInMs * 5,
            DateHelper.minuteInMs * 5,
            DateHelper.dayInMs * 5,
            DateHelper.secondInMs * 10,
            DateHelper.minuteInMs * 10,
            DateHelper.dayInMs * 10 }) {

            for (long duration : new long[] {
                0,
                magnitude,
                Math.round(magnitude * 1.4),
                Math.round(magnitude * 0.1),
                Math.round(magnitude * 0.5),
                Math.round(magnitude * 0.7) }) {
                Assert.assertEquals(1, DateHelper.divAndRoundToAvoidNull(magnitude, duration));
            }

            Assert.assertEquals(
                2,
                DateHelper.divAndRoundToAvoidNull(magnitude, magnitude + magnitude / 2));
            Assert.assertEquals(
                2,
                DateHelper.divAndRoundToAvoidNull(magnitude, Math.round(magnitude * 1.7)));
            Assert.assertEquals(
                2,
                DateHelper.divAndRoundToAvoidNull(magnitude, Math.round(magnitude * 2)));
            Assert.assertEquals(
                2,
                DateHelper.divAndRoundToAvoidNull(magnitude, Math.round(magnitude * 2.1)));
            Assert.assertEquals(
                2,
                DateHelper.divAndRoundToAvoidNull(magnitude, Math.round(magnitude * 2.4)));

            Assert.assertEquals(5, DateHelper.divAndRoundToAvoidNull(magnitude, magnitude * 5));
            Assert.assertEquals(10, DateHelper.divAndRoundToAvoidNull(magnitude, magnitude * 10));
            Assert.assertEquals(31, DateHelper.divAndRoundToAvoidNull(magnitude, magnitude * 31));
        }
    }

    @Test
    public void diffDaysInclusiveTest() {
        {
            UserDate date1 = new UserDate(null, LocalDateTest.cetDateOf(2013, 2, 27));
            UserDate date2 = new UserDate(null, LocalDateTest.cetDateOf(2013, 2, 27));

            Assert.assertEquals(1, DateHelper.diffDaysInclusive(date1, date2));
        }

        {
            UserDate date1 = new UserDate(null, LocalDateTest.cetDateOf(2013, 2, 27));
            UserDate date2 = new UserDate(null, LocalDateTest.cetDateOf(2013, 2, 29));

            Assert.assertEquals(3, DateHelper.diffDaysInclusive(date1, date2));
        }

        {
            // clock setting caused by Daylight Savings Time changing is on
            // 2013.03.31 in CET
            UserDate date1 = new UserDate(null, LocalDateTest.cetDateOf(2013, 2, 30));
            UserDate date2 = new UserDate(null, LocalDateTest.cetDateOf(2013, 3, 2));

            Assert.assertEquals(4, DateHelper.diffDaysInclusive(date1, date2));
        }

    }
}
