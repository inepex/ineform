package com.inepex.ineom.shared.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class DoubleUtilTest {
	
	@Test
	public void doubleToLongBits() {
		assertEquals(0L, Double.doubleToLongBits(0.0));
		assertEquals(0L, Double.doubleToLongBits(0.0+0.0));
		assertEquals(-0L, Double.doubleToLongBits(0.0));
		
		for(double d=-10.0; d<10; d+=0.1) {
			assertEquals(Double.doubleToLongBits(d), Double.doubleToLongBits(d));
		}
	}
	
	@Test
	public void stubToMaxDigitsTest() {
		assertEquals(0.0, DoubleUtil.stubToMaxDigits(0, 0.0), 0);
		assertEquals(0.0, DoubleUtil.stubToMaxDigits(1, 0.0), 0);
		assertEquals(0.0, DoubleUtil.stubToMaxDigits(2, 0.0), 0);
		assertEquals(0.0, DoubleUtil.stubToMaxDigits(3, 0.0), 0);
		assertEquals(0.0, DoubleUtil.stubToMaxDigits(4, 0.0), 0);
		
		assertEquals(10.0, DoubleUtil.stubToMaxDigits(0, 10.0), 0);
		assertEquals(10.0, DoubleUtil.stubToMaxDigits(10, 10.0), 0);
		assertEquals(10.0, DoubleUtil.stubToMaxDigits(200, 10.0), 0);
		
		assertEquals(0.0, DoubleUtil.stubToMaxDigits(0, 0.1234), 0);
		assertEquals(0.1, DoubleUtil.stubToMaxDigits(1, 0.1234), 0);
		assertEquals(0.12, DoubleUtil.stubToMaxDigits(2, 0.1234), 0);
		assertEquals(0.123, DoubleUtil.stubToMaxDigits(3, 0.1234), 0);
		assertEquals(0.1234, DoubleUtil.stubToMaxDigits(4, 0.1234), 0);
		assertEquals(0.1234, DoubleUtil.stubToMaxDigits(5, 0.1234), 0);
		assertEquals(0.1234, DoubleUtil.stubToMaxDigits(6, 0.1234), 0);
		
		assertEquals(0.0, DoubleUtil.stubToMaxDigits(0, -0.1234), 0);
		assertEquals(-0.1, DoubleUtil.stubToMaxDigits(1, -0.1234), 0);
		assertEquals(-0.12, DoubleUtil.stubToMaxDigits(2, -0.1234), 0);
		assertEquals(-0.123, DoubleUtil.stubToMaxDigits(3, -0.1234), 0);
		assertEquals(-0.1234, DoubleUtil.stubToMaxDigits(4, -0.1234), 0);
		assertEquals(-0.1234, DoubleUtil.stubToMaxDigits(5, -0.1234), 0);
		assertEquals(-0.1234, DoubleUtil.stubToMaxDigits(6, -0.1234), 0);
		
		assertEquals(1.0, DoubleUtil.stubToMaxDigits(0, 1.1234), 0);
		assertEquals(100.1, DoubleUtil.stubToMaxDigits(1, 100.1234), 0);
		assertEquals(1000.12, DoubleUtil.stubToMaxDigits(2, 1000.1234), 0);
		assertEquals(10000.123, DoubleUtil.stubToMaxDigits(3, 10000.1234), 0);
		assertEquals(100000.1234, DoubleUtil.stubToMaxDigits(4, 100000.1234), 0);
		assertEquals(1000000.1234, DoubleUtil.stubToMaxDigits(5, 1000000.1234), 0);
		assertEquals(10000000000.1234, DoubleUtil.stubToMaxDigits(6, 10000000000.1234), 0);
		
		assertEquals(-1.0, DoubleUtil.stubToMaxDigits(0, -1.1234), 0);
		assertEquals(-10.1, DoubleUtil.stubToMaxDigits(1, -10.1234), 0);
		assertEquals(-1000.12, DoubleUtil.stubToMaxDigits(2, -1000.1234), 0);
		assertEquals(-10000.123, DoubleUtil.stubToMaxDigits(3, -10000.1234), 0);
		assertEquals(-100000.1234, DoubleUtil.stubToMaxDigits(4, -100000.1234), 0);
		assertEquals(-1000000.1234, DoubleUtil.stubToMaxDigits(5, -1000000.1234), 0);
		assertEquals(-10000000000.1234, DoubleUtil.stubToMaxDigits(6, -10000000000.1234), 0);
	}
}

