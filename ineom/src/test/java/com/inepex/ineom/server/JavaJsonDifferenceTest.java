package com.inepex.ineom.server;

import org.junit.Test;

public class JavaJsonDifferenceTest {

	@Test
	public void basic(){
		String difference = new JavaJsonDifference().difference("{ \"prop1\" : \"val1\"}", "{ \"prop1\" : \"val2\"}");
		System.out.println(difference);
	}
}
