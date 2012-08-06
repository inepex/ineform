package com.inepex.ineFrame.shared.util;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateHelperTest {
	
	@Test 
	public void approachDurationTest() {
		
		for(long magnitude : new long[]{1, 10, DateHelper.secondInMs, DateHelper.secondInMs*5, 
				DateHelper.secondInMs*10, DateHelper.minuteInMs, DateHelper.minuteInMs*5, 
				DateHelper.minuteInMs*10, DateHelper.minuteInMs*15, DateHelper.dayInMs}) {
			Assert.assertEquals(1, DateHelper.divAndRoundToAvoidNull(magnitude, 0L));
		}
		
		for(long magnitude : new long[]{
				DateHelper.secondInMs, DateHelper.minuteInMs, DateHelper.dayInMs,
				DateHelper.secondInMs*5, DateHelper.minuteInMs*5, DateHelper.dayInMs*5,
				DateHelper.secondInMs*10, DateHelper.minuteInMs*10, DateHelper.dayInMs*10}) {
			
			for(long duration : new long []{
					0, 
					magnitude, 
					Math.round(magnitude*1.4), 
					Math.round(magnitude*0.1), 
					Math.round(magnitude*0.5), 
					Math.round(magnitude*0.7)}) {
				Assert.assertEquals(1, DateHelper.divAndRoundToAvoidNull(magnitude, duration));
			}
			
			Assert.assertEquals(2, DateHelper.divAndRoundToAvoidNull(magnitude, magnitude+magnitude/2));
			Assert.assertEquals(2, DateHelper.divAndRoundToAvoidNull(magnitude, Math.round(magnitude*1.7)));
			Assert.assertEquals(2, DateHelper.divAndRoundToAvoidNull(magnitude, Math.round(magnitude*2)));
			Assert.assertEquals(2, DateHelper.divAndRoundToAvoidNull(magnitude, Math.round(magnitude*2.1)));
			Assert.assertEquals(2, DateHelper.divAndRoundToAvoidNull(magnitude, Math.round(magnitude*2.4)));
			
			Assert.assertEquals(5, DateHelper.divAndRoundToAvoidNull(magnitude, magnitude*5));
			Assert.assertEquals(10, DateHelper.divAndRoundToAvoidNull(magnitude, magnitude*10));
			Assert.assertEquals(31, DateHelper.divAndRoundToAvoidNull(magnitude, magnitude*31));
		}
	}

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
	
	/**
	 * test year change
	 */
	@Test
	public void addMonthTestYearChange(){
		Date date = new Date(2012-1900, 0, 1);
		Date result = DateHelper.addMonth(date, -1);
		Assert.assertEquals(11, result.getMonth());
	}
	
	/**
	 * test year change 2
	 */
	@Test
	public void addMonthTestYearChange2(){
		Date date = new Date(2012-1900, 0, 1);
		Date result = DateHelper.addMonth(date, -13);
		Assert.assertEquals(11, result.getMonth());
	}
}

