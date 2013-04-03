package com.inepex.ineFrame.shared.util;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.inepex.ineFrame.shared.util.date.DateHelper;
import com.inepex.ineFrame.test.DefaultIneFrameClientSideTestBase;

public class DateHelperFormatDurationTest extends DefaultIneFrameClientSideTestBase {

	public DateHelperFormatDurationTest() {
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void formatTest(){
		Assert.assertEquals("1d 1h 17m 0s", DateHelper.formatDuration(new Date(2010 - 1900, 11, 11, 9, 22).getTime() - 
								new Date(2010 - 1900, 11, 10, 8, 5).getTime(), true));
		
		//hide sec
		Assert.assertEquals("1d 1h 17m", DateHelper.formatDuration(new Date(2010 - 1900, 11, 11, 9, 22).getTime() - 
				new Date(2010 - 1900, 11, 10, 8, 5).getTime(), false));
		
		//auto hide day 
		Assert.assertEquals("1h 17m", DateHelper.formatDuration(new Date(2010 - 1900, 11, 10, 9, 22).getTime() - 
				new Date(2010 - 1900, 11, 10, 8, 5).getTime(), false));
	}
	
	
}
