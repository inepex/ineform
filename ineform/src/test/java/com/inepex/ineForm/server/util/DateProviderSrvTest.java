package com.inepex.ineForm.server.util;

import org.junit.Test;

import com.inepex.ineFrame.server.util.CETDateProviderSrv;
import com.inepex.ineFrame.server.util.EnumBasedSessionScopedDateProvider;
import com.inepex.ineFrame.server.util.TimeZoneEnum;

public class DateProviderSrvTest {

    @Test
    public void testCETDateProvider() {
        CETDateProviderSrv dateProvider = new CETDateProviderSrv();

        CETDateTestCases.cetSummerEquality(dateProvider);
        CETDateTestCases.cetTimeEquality(dateProvider);
        CETDateTestCases.gmtPlus2SummerCompenzation(dateProvider);
        CETDateTestCases.gmtPlus2TimeCompenzation(dateProvider);
    }

    @Test
    public void testEnumBasedateProvider() {
        EnumBasedSessionScopedDateProvider dateProvider = new EnumBasedSessionScopedDateProvider();
        dateProvider.setTimeZoneEnumAndTimeZone(TimeZoneEnum.Europe_Budapest);

        CETDateTestCases.cetSummerEquality(dateProvider);
        CETDateTestCases.cetTimeEquality(dateProvider);
        CETDateTestCases.gmtPlus2SummerCompenzation(dateProvider);
        CETDateTestCases.gmtPlus2TimeCompenzation(dateProvider);
    }
}
