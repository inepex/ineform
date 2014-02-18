package com.inepex.ineForm.shared;

import static junit.framework.Assert.*;

import org.junit.Test;

public class PhoneNumberLogicTest {

	@Test
	public void testSample() {
		{
			String[] phone = PhoneNumberLogic.parsePhoneString(PhoneNumberLogic.sample1);
			assertEquals("234", phone[0]);
			assertNull(phone[1]);
			assertEquals("232124", phone[2]);
		}
		
		{
			String[] phone = PhoneNumberLogic.parsePhoneString(PhoneNumberLogic.sample2);
			assertEquals("23", phone[0]);
			assertEquals("342", phone[1]);
			assertEquals("2342343242", phone[2]);
		}
	}
}
