package com.inepex.ineForm.server.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

import com.inepex.ineFrame.server.util.CETDateProviderSrv;
import com.inepex.ineFrame.shared.util.DateProvider;

public class CETDateProviderSrvTest {
	
	DateProvider dateProvider = new CETDateProviderSrv();
	
	@Test
	public void cetTimeEquality() {
		TimeZone cetTimeZone = TimeZone.getTimeZone("CET");
		Assert.assertNotNull(cetTimeZone);
		Assert.assertEquals("CET", cetTimeZone.getID());
		
		TimeZone.setDefault(cetTimeZone);
		
		Calendar c = Calendar.getInstance();
		c.set(2011, 2, 19, 20, 20, 0); //2011.03.19 20:20:0
		Date d = c.getTime();
		
		Assert.assertEquals(d.getTime(), 
				dateProvider.getDate(d.getTime()).getTime());
		
		Assert.assertEquals(d.getTime(), 
				dateProvider.whatMeansTyped(d.getTime()).getTime());
	}
	
	@Test
	public void cetSummerEquality() {
		
		TimeZone cetTimeZone = TimeZone.getTimeZone("CET");
		Assert.assertNotNull(cetTimeZone);
		Assert.assertEquals("CET", cetTimeZone.getID());
		
		TimeZone.setDefault(cetTimeZone);
		
		Calendar summerDate = Calendar.getInstance();
		summerDate.set(2011, 4, 19, 20, 20, 0); //2011.05.19 20:20:0
		Date sd = summerDate.getTime();
		
		Assert.assertEquals(sd.getTime(), 
				dateProvider.getDate(sd.getTime()).getTime());
		
		Assert.assertEquals(sd.getTime(), 
				dateProvider.whatMeansTyped(sd.getTime()).getTime());
	}
	
	@Test
	public void gmtPlus2TimeCompenzation() {
		TimeZone gmtP2TimeZone = TimeZone.getTimeZone("Etc/GMT+2");
		Assert.assertNotNull(gmtP2TimeZone);
		Assert.assertEquals("Etc/GMT+2", gmtP2TimeZone.getID());
		
		TimeZone cetTimeZone = TimeZone.getTimeZone("CET");
		Assert.assertNotNull(cetTimeZone);
		Assert.assertEquals("CET", cetTimeZone.getID());
		
		TimeZone.setDefault(gmtP2TimeZone);
		
		SimpleDateFormat cetFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		cetFormat.setTimeZone(cetTimeZone);
		
		SimpleDateFormat gmtP2Format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		gmtP2Format.setTimeZone(gmtP2TimeZone);
		
		Calendar c = Calendar.getInstance(cetTimeZone);
		c.set(2011, 2, 19, 20, 20, 0); //2011.03.19 20:20:0

		Assert.assertEquals("2011.03.19 20:20:00", cetFormat.format(c.getTime()));
		Assert.assertEquals(cetFormat.format(c.getTime()), 
				gmtP2Format.format(dateProvider.getDate(c.getTime().getTime())));
		
		c = Calendar.getInstance();
		c.set(2011, 2, 19, 20, 20, 0); //2011.03.19 20:20:0
		
		Assert.assertEquals("2011.03.19 20:20:00", gmtP2Format.format(c.getTime()));
		Assert.assertEquals(gmtP2Format.format(c.getTime()), 
				cetFormat.format(dateProvider.whatMeansTyped(c.getTime().getTime())));
	}
	
	@Test
	public void gmtPlus2SummerCompenzation() {
		TimeZone gmtP2TimeZone = TimeZone.getTimeZone("Etc/GMT+2");
		Assert.assertNotNull(gmtP2TimeZone);
		Assert.assertEquals("Etc/GMT+2", gmtP2TimeZone.getID());
		
		TimeZone cetTimeZone = TimeZone.getTimeZone("CET");
		Assert.assertNotNull(cetTimeZone);
		Assert.assertEquals("CET", cetTimeZone.getID());
		
		TimeZone.setDefault(gmtP2TimeZone);
		
		SimpleDateFormat cetFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		cetFormat.setTimeZone(cetTimeZone);
		
		SimpleDateFormat gmtP2Format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		gmtP2Format.setTimeZone(gmtP2TimeZone);
		
		Calendar c = Calendar.getInstance(cetTimeZone);
		c.set(2011, 4, 19, 20, 20, 0); //2011.05.19 20:20:0

		Assert.assertEquals("2011.05.19 20:20:00", cetFormat.format(c.getTime()));
		Assert.assertEquals(cetFormat.format(c.getTime()), 
				gmtP2Format.format(dateProvider.getDate(c.getTime().getTime())));
		
		c = Calendar.getInstance();
		c.set(2011, 4, 19, 20, 20, 0); //2011.05.19 20:20:0
		
		Assert.assertEquals("2011.05.19 20:20:00", gmtP2Format.format(c.getTime()));
		Assert.assertEquals(gmtP2Format.format(c.getTime()), 
				cetFormat.format(dateProvider.whatMeansTyped(c.getTime().getTime())));
	}

}
