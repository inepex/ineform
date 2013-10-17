package com.inepex.ineom.server;

import org.junit.Assert;
import org.junit.Test;

import com.inepex.ineom.shared.assistedobject.KeyValueObject;

public class JavaPropHandlerTest {

	private static final String group1 = "group1";
	private static final String group2 = "group2";
	private static final String key1 = "key1";
	private static final String key2 = "key2";
	private static final String value1 = "value1";
	private static final String value2 = "value2";
	
	@Test
	public void test(){
		KeyValueObject kvo = new KeyValueObject();
		JavaPropHandler propHandler = new JavaPropHandler();
		propHandler.setProp(kvo, group1, key1, value1);
		propHandler.setProp(kvo, group1, key2, value2);
		
		Assert.assertEquals(value1, propHandler.getStringProp(kvo, group1, key1));
		Assert.assertEquals(value2, propHandler.getStringProp(kvo, group1, key2));
	}
	
}
