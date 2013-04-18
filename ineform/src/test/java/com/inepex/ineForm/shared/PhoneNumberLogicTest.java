package com.inepex.ineForm.shared;

import org.junit.Test;
import static junit.framework.Assert.*;

public class PhoneNumberLogicTest {

	@Test
	public void testSample() {
		{
			Long[] phone = PhoneNumberLogic.parsePhoneString(PhoneNumberLogic.sample1);
			assertEquals(234, phone[0].longValue());
			assertNull(phone[1]);
			assertEquals(232124, phone[2].longValue());
		}
		
		{
			Long[] phone = PhoneNumberLogic.parsePhoneString(PhoneNumberLogic.sample2);
			assertEquals(23, phone[0].longValue());
			assertEquals(342, phone[1].longValue());
			assertEquals(2342343242L, phone[2].longValue());
		}
	}
}
