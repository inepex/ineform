package com.inepex.ineom.shared.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SharedUtilTest {

    private static final String input =
        "<b>hello hello</b><br /><tag i='-12' bla=bla>bla-bla bla</tag>\n"
            + "<b>hello hello</b><br /><tag i='-12' bla=bla>bla-bla bla</tag>";
    private static final String result =
        "<b>hello&nbsp;hello</b><br /><tag i='-12' bla=bla>bla&#8209;bla&nbsp;bla</tag>\n"
            + "<b>hello&nbsp;hello</b><br /><tag i='-12' bla=bla>bla&#8209;bla&nbsp;bla</tag>";

    @Test
    public void escapeHtmlSpacesTest() {
        assertNull(SharedUtil.escapeHtmlSpaces(null));
        assertEquals("", SharedUtil.escapeHtmlSpaces(""));
        assertEquals(
            "<tag info='there is no escape in the tag' nr='-12'>",
            SharedUtil.escapeHtmlSpaces("<tag info='there is no escape in the tag' nr='-12'>"));
        assertEquals(
            "full&#8209;escaped&nbsp;string",
            SharedUtil.escapeHtmlSpaces("full-escaped string"));
        assertEquals(result, SharedUtil.escapeHtmlSpaces(input));

    }

    @Test
    public void isValidIpTest() {
        assertTrue(SharedUtil.isValidIP(null, true));
        assertTrue(SharedUtil.isValidIP("", true));
        assertTrue(SharedUtil.isValidIP("0.0.0.0", true));
        assertTrue(SharedUtil.isValidIP("1.1.1.1", true));
        assertTrue(SharedUtil.isValidIP("8.8.8.8", true));
        assertTrue(SharedUtil.isValidIP("80.80.80.80", true));
        assertTrue(SharedUtil.isValidIP("180.180.180.180", true));
        assertTrue(SharedUtil.isValidIP("255.255.255.255", true));

        assertFalse(SharedUtil.isValidIP(null, false));
        assertFalse(SharedUtil.isValidIP("", false));
        assertFalse(SharedUtil.isValidIP("1.1.1", true));
        assertFalse(SharedUtil.isValidIP("1.1.1000", true));
        assertFalse(SharedUtil.isValidIP("1.1.1.01", true));
        assertFalse(SharedUtil.isValidIP("1.1.1a1", true));
        assertFalse(SharedUtil.isValidIP("1.1.1.270", true));
        assertFalse(SharedUtil.isValidIP("1.1.1.-1", true));

    }
}
