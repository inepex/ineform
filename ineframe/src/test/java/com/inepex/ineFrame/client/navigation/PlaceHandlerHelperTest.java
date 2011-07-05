package com.inepex.ineFrame.client.navigation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

public class PlaceHandlerHelperTest {
	
	@Test
	public void testAppendChild() {
		Assert.assertEquals("childToken", PlaceHandlerHelper.appendChild("", "childToken"));
		Assert.assertEquals("mainToken/childToken", PlaceHandlerHelper.appendChild("mainToken", "childToken"));
		Assert.assertEquals("mainToken?p=a/childToken", PlaceHandlerHelper.appendChild("mainToken?p=a", "childToken"));
		Assert.assertEquals("mainToken/childToken", PlaceHandlerHelper.appendChild("mainToken/", "childToken"));
		Assert.assertEquals("mainToken?p=a/childToken", PlaceHandlerHelper.appendChild("mainToken?p=a/", "childToken"));
		
		Assert.assertEquals("mainToken?p=a/childToken?a=34", PlaceHandlerHelper.appendChild("mainToken?p=a/", "childToken?a=34"));
	}
	
	@Test
	public void testAppendParam() {
		Assert.assertEquals("token?p=12", PlaceHandlerHelper.appendParam("token", "p", "12"));
		Assert.assertEquals("token?a=anyValue&p=12", PlaceHandlerHelper.appendParam("token?a=anyValue", "p", "12"));
		Assert.assertEquals("mainToken/token?p=12", PlaceHandlerHelper.appendParam("mainToken/token", "p", "12"));
		Assert.assertEquals("mainToken/token?a=anyValue&p=12", PlaceHandlerHelper.appendParam("mainToken/token?a=anyValue", "p", "12"));
		
		//when param is already set
		Assert.assertEquals("token?p=12", PlaceHandlerHelper.appendParam("token?p=10", "p", "12"));
		Assert.assertEquals("token?p=12&a=anyValue", PlaceHandlerHelper.appendParam("token?p=10&a=anyValue", "p", "12"));
		
		Assert.assertEquals("token?param=12", PlaceHandlerHelper.appendParam("token?param=10", "param", "12"));
		Assert.assertEquals("token?param=12&a=anyValue", PlaceHandlerHelper.appendParam("token?param=10&a=anyValue", "param", "12"));
		
		Assert.assertEquals("token?a=1&param=12&b=2", PlaceHandlerHelper.appendParam("token?a=1&param=10&b=2", "param", "12"));
	}

	@Test
	public void getPlacePartTest() {
		Assert.assertEquals("", PlaceHandlerHelper.getPlacePart(""));
		Assert.assertEquals("token", PlaceHandlerHelper.getPlacePart("token"));
		Assert.assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1/token2"));
		Assert.assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1/token2/"));
		
		Assert.assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1/token2?id=2"));
		Assert.assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1/token2?id=2&c=asd123"));
		
		Assert.assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1?id=2/token2"));
		Assert.assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1?id=2&c=asd123/token2"));
		Assert.assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1?id=2&c=asd123/token2?id=2&c=asd123"));
		Assert.assertEquals("token1/token2/token3", PlaceHandlerHelper.getPlacePart("token1?id=2&c=asd123/token2?id=2&c=asd123/token3"));
	}
	
	@Test
	public void getUrlParametersTest() {
		test(new TreeMap<String, String>(), PlaceHandlerHelper.getUrlParameters(""));
		test(new TreeMap<String, String>(), PlaceHandlerHelper.getUrlParameters("token"));
		test(new TreeMap<String, String>(), PlaceHandlerHelper.getUrlParameters("token1/token2"));
		
		test(map("id", "2"), PlaceHandlerHelper.getUrlParameters("token?id=2"));
		
		test(map("id", "2"), PlaceHandlerHelper.getUrlParameters("token1/token2?id=2"));
		test(map("id", "2"), PlaceHandlerHelper.getUrlParameters("token1?id=2/token2"));
		
		test(map("id", "2", "c", "asd123"), PlaceHandlerHelper.getUrlParameters("token1/token2?id=2&c=asd123"));
		test(map("id", "2", "c", "asd123"), PlaceHandlerHelper.getUrlParameters("token1?id=2&c=asd123/token2"));
		test(map("id", "2", "c", "asd123"), PlaceHandlerHelper.getUrlParameters("token1?id=2/token2?c=asd123"));
		test(map("id", "2", "c", "asd123"), PlaceHandlerHelper.getUrlParameters("token1?c=asd123/token2?id=2"));
		
		test(map("id", "2"), PlaceHandlerHelper.getUrlParameters("token1?id=1/token2?id=2"));
	}
	
	private void test(Map<String, String> map1, Map<String, String> map2) {
		Assert.assertEquals(map1.size(), map2.size());
		
		//keys
		Collection<String> keys1 = new ArrayList<String>(map1.keySet());
		for(String key : map2.keySet()) {
			Assert.assertTrue(key, keys1.remove(key));
		}
		Assert.assertEquals(0, keys1.size());
	
		//values
		for(String key : map2.keySet())
			Assert.assertEquals(map1.get(key), map2.get(key));
	}
	
	private Map<String, String> map(String... keyVals) {
		if(keyVals == null || keyVals.length==0 || keyVals.length%2 != 0)
			throw new IllegalArgumentException();
		
		TreeMap<String, String> map = new TreeMap<String, String>();
		for(int i=0; i<keyVals.length/2; i++) {
			map.put(keyVals[2*i], keyVals[2*i+1]);
		}
		
		return map;
	}
}
