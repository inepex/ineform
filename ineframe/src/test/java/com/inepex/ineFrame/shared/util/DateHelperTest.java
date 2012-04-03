package com.inepex.ineFrame.shared.util;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateHelperTest {

	/**
	 * leap year test
	 */
	@Test
	public void addMonthTestLeapYear(){
		Date date = new Date(2012-1900, 2, 30);
		Date result = DateHelper.addMonth(date, -1);
		Assert.assertEquals(1, result.getMonth());
	}
	
	/**
	 * normal test
	 */
	@Test
	public void addMonthTestBasic(){
		Date date = new Date(2012-1900, 6, 3);
		Date result = DateHelper.addMonth(date, -1);
		Assert.assertEquals(5, result.getMonth());
	}
	
	/**
	 * add 0
	 */
	@Test
	public void addMonthTestAdd0(){
		Date date = new Date(2012-1900, 6, 3);
		Date result = DateHelper.addMonth(date, 0);
		Assert.assertEquals(6, result.getMonth());
	}
	
}
