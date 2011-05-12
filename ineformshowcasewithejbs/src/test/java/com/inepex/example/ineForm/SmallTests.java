package com.inepex.example.ineForm;

import org.junit.Test;


public class SmallTests {

	@Test
	public void nullCastTest(){
		Long b = new Long(1L);
		
		b = (Long) null;
		System.out.println(b);
		
		boolean isBoolean = isBoolean(b);
		System.out.println(isBoolean);
	}
	
	private boolean  isBoolean(Object b) {
		return b instanceof Boolean;
	}
}
