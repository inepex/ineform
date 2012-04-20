package com.inepex.ineForm.server.util;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {
	
	@Test
	public void splitToCharBlocksTest(){
		String s = StringUtil.splitToCharBlocks("0123456789", 3, "-");
		Assert.assertEquals("012-345-678-9", s);
	}
}
