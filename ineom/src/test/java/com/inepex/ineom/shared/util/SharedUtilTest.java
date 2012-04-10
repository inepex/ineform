package com.inepex.ineom.shared.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class SharedUtilTest {

	private static final String input="<b>hello hello</b><br /><tag i='-12' bla=bla>bla-bla bla</tag>\n" +
			"<b>hello hello</b><br /><tag i='-12' bla=bla>bla-bla bla</tag>";
	private static final String result="<b>hello&nbsp;hello</b><br /><tag i='-12' bla=bla>bla&#8209;bla&nbsp;bla</tag>\n" +
			"<b>hello&nbsp;hello</b><br /><tag i='-12' bla=bla>bla&#8209;bla&nbsp;bla</tag>";
	
	@Test
	public void escapeHtmlSpacesTest() {
		assertNull(SharedUtil.escapeHtmlSpaces(null));
		assertEquals("", SharedUtil.escapeHtmlSpaces(""));
		assertEquals("<tag info='there is no escape in the tag' nr='-12'>", 
				SharedUtil.escapeHtmlSpaces("<tag info='there is no escape in the tag' nr='-12'>"));
		assertEquals("full&#8209;escaped&nbsp;string",
				SharedUtil.escapeHtmlSpaces("full-escaped string"));
		assertEquals(result, SharedUtil.escapeHtmlSpaces(input));
		
	}
}
