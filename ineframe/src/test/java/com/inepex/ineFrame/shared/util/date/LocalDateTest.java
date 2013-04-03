package com.inepex.ineFrame.shared.util.date;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

public class LocalDateTest {

	static long cetDateOf(int year, int month, int date) {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("CET"));
		c.set(year, month, date);
		return c.getTimeInMillis();
	}
	
	private void assertDate(UntrasformedLocalDate locDate, int year, int month, int date) {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("CET"));
		c.setTimeInMillis(locDate.getTime());
		Assert.assertEquals(year, locDate.getCalendarStyleYear());
		Assert.assertEquals(month, locDate.getCalendarStyleMonth());
		Assert.assertEquals(date, locDate.getCalendarStyleDate());
	}
	
	@Test
	public void test_firstMS_noon() {
		UntrasformedLocalDate date = new UntrasformedLocalDate(cetDateOf(2012, 2, 30));
		date.setToFirstMSOfDay().setToNoon();
		assertDate(date, 2012,2,30);
	}
	
	@Test
	public void addMonth_febr29(){
		{
			UntrasformedLocalDate date = new UntrasformedLocalDate(cetDateOf(2012, 2, 30));
			date.addMonthAbstract(-1);
			assertDate(date, 2012,1,29);
		}
		
		{
			UntrasformedLocalDate date = new UntrasformedLocalDate(cetDateOf(2012, 0, 31));
			date.addMonthAbstract(1);
			assertDate(date, 2012,1,29);
		}
	}
	
	@Test
	public void addMonth_endOfMonthCorrigation() {
		UntrasformedLocalDate date = new UntrasformedLocalDate(cetDateOf(2011, 2, 31));
		date.addMonthAbstract(-1);
		assertDate(date, 2011,1,28);
		
		date.addMonthAbstract(1);
		assertDate(date, 2011, 2, 31);
	}
	
	@Test
	public void addMonth_basic(){
		{
			UntrasformedLocalDate date = new UntrasformedLocalDate(cetDateOf(2012, 6, 3));
			date.addMonthAbstract(-1);
			assertDate(date, 2012,5,3);
		}
		
		{
			UntrasformedLocalDate date = new UntrasformedLocalDate(cetDateOf(2012, 6, 3));
			date.addMonthAbstract(0);
			assertDate(date, 2012,6,3);
		}
		
		{
			UntrasformedLocalDate date = new UntrasformedLocalDate(cetDateOf(2012, 6, 3));
			date.addMonthAbstract(1);
			assertDate(date, 2012,7,3);
		}
	}
	
	@Test
	public void addMonth_firstDay(){
		{
			UntrasformedLocalDate date = new UntrasformedLocalDate(cetDateOf(2012, 1, 1));
			date.addMonthAbstract(-1);
			assertDate(date, 2012,0,1);
		}
		
		{
			UntrasformedLocalDate date = new UntrasformedLocalDate(cetDateOf(2012, 1, 1));
			date.addMonthAbstract(1);
			assertDate(date, 2012,2,1);
		}
	}
	
	@Test
	public void addMonth_yearChange(){
		{
			UntrasformedLocalDate date = new UntrasformedLocalDate(cetDateOf(2012, 0, 1));
			date.addMonthAbstract(-1);
			assertDate(date, 2011,11,1);
		}
		
		{
			UntrasformedLocalDate date = new UntrasformedLocalDate(cetDateOf(2012, 0, 1));
			date.addMonthAbstract(-13);
			assertDate(date, 2010,11,1);
		}
	}
}
