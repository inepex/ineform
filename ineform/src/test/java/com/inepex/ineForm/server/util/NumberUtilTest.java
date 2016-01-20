package com.inepex.ineForm.server.util;

import junit.framework.Assert;

import org.junit.Test;

import com.inepex.ineFrame.server.NumberUtilSrv;

public class NumberUtilTest {

    public void formatNumberGroupThousandsTest() {
        String res = new NumberUtilSrv().formatNumberGroupThousands(10000001);
        System.out.println(res);
        Assert.assertEquals("10,000,001", res);
    }

    @Test
    public void testFractional() {
        Assert.assertEquals(new NumberUtilSrv().formatNumberToFractial(1), "1");
        Assert.assertEquals(new NumberUtilSrv().formatNumberToFractial(1.1), "1,1");
        Assert.assertEquals(new NumberUtilSrv().formatNumberToFractial(1.11), "1,11");
        Assert.assertEquals(new NumberUtilSrv().formatNumberToFractial(1.111), "1,111");
        Assert.assertEquals(new NumberUtilSrv().formatNumberToFractial(1.1111), "1,1111");
        Assert.assertEquals(new NumberUtilSrv().formatNumberToFractial(1.22222222221), "1,222222");
    }

    @Test
    public void testCustomFractional() {
        Assert.assertEquals(new NumberUtilSrv().formatNumberToFractial(1, 0), "1");
        Assert.assertEquals(new NumberUtilSrv().formatNumberToFractial(1.1, 0), "1");
        Assert.assertEquals(new NumberUtilSrv().formatNumberToFractial(1.2, 0), "1");
        Assert.assertEquals(new NumberUtilSrv().formatNumberToFractial(1, 2), "1,00");
        Assert.assertEquals(new NumberUtilSrv().formatNumberToFractial(1.1, 2), "1,10");
        Assert.assertEquals(new NumberUtilSrv().formatNumberToFractial(1.11, 2), "1,11");
        Assert.assertEquals(new NumberUtilSrv().formatNumberToFractial(1.1111, 3), "1,111");
        Assert
            .assertEquals(new NumberUtilSrv().formatNumberToFractial(1.22222222221, 6), "1,222222");
    }
}
