package com.inepex.ineFrame.client.navigation;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class PlaceHandlerHelperTest {
	
	@Test
	public void testRemoveParam() {
		String orig="home/devices?deviceGroups=1&chart=false&details=true/device?deviceId=100000/general";
		
		assertEquals(orig, PlaceHandlerHelper.removeParam(orig, "cantfound"));
		assertEquals(orig, PlaceHandlerHelper.removeParam(orig, "general"));
		assertEquals(orig, PlaceHandlerHelper.removeParam(orig, "device"));
		assertEquals(orig, PlaceHandlerHelper.removeParam(orig, "ch"));
		assertEquals(orig, PlaceHandlerHelper.removeParam(orig, "e"));
		assertEquals(orig, PlaceHandlerHelper.removeParam(orig, "true"));
		
		assertEquals("home/devices?deviceGroups=1&chart=false&details=true/device/general", PlaceHandlerHelper.removeParam(orig, "deviceId"));
		assertEquals("home/devices?deviceGroups=1&chart=false/device?deviceId=100000/general", PlaceHandlerHelper.removeParam(orig, "details"));
		assertEquals("home/devices?deviceGroups=1&details=true/device?deviceId=100000/general", PlaceHandlerHelper.removeParam(orig, "chart"));
		assertEquals("home/devices?chart=false&details=true/device?deviceId=100000/general", PlaceHandlerHelper.removeParam(orig, "deviceGroups"));
	}
	
	@Test
	public void testCreateSubMenuToken() {
		assertEquals("a", PlaceHandlerHelper.createSubMenuToken("", "a"));
		assertEquals("a/b", PlaceHandlerHelper.createSubMenuToken("", "a", "b"));
		
		assertEquals("a/b/c", PlaceHandlerHelper.createSubMenuToken("a/b", "c"));
		assertEquals("a/b/c/d", PlaceHandlerHelper.createSubMenuToken("a/b", "c", "d"));
		
	}
	
	@Test
	public void testCreateSameLevelMenuToken() {
		assertEquals("a", PlaceHandlerHelper.createSameLevelMenuToken("anyToken", "a"));
		assertEquals("newToken", PlaceHandlerHelper.createSameLevelMenuToken("anyToken", "newToken"));
		
		assertEquals("a/b", PlaceHandlerHelper.createSameLevelMenuToken("anyToken", "a", "b"));
		assertEquals("newToken/newToken2", PlaceHandlerHelper.createSameLevelMenuToken("anyToken", "newToken", "newToken2"));
		
		assertEquals("token/token2/newToken", PlaceHandlerHelper.createSameLevelMenuToken("token/token2/token3", "newToken"));
	}
	
	@Test
	public void testAppendChild() {
		assertEquals("childToken", PlaceHandlerHelper.appendChild("", "childToken"));
		assertEquals("mainToken/childToken", PlaceHandlerHelper.appendChild("mainToken", "childToken"));
		assertEquals("mainToken?p=a/childToken", PlaceHandlerHelper.appendChild("mainToken?p=a", "childToken"));
		assertEquals("mainToken/childToken", PlaceHandlerHelper.appendChild("mainToken/", "childToken"));
		assertEquals("mainToken?p=a/childToken", PlaceHandlerHelper.appendChild("mainToken?p=a/", "childToken"));
		
		assertEquals("mainToken?p=a/childToken?a=34", PlaceHandlerHelper.appendChild("mainToken?p=a/", "childToken?a=34"));
	}
	
	@Test
	public void testAppendParam() {
		assertEquals("token?p=12", PlaceHandlerHelper.appendParam("token", "p", "12"));
		assertEquals("token?a=anyValue&p=12", PlaceHandlerHelper.appendParam("token?a=anyValue", "p", "12"));
		assertEquals("mainToken/token?p=12", PlaceHandlerHelper.appendParam("mainToken/token", "p", "12"));
		assertEquals("mainToken/token?a=anyValue&p=12", PlaceHandlerHelper.appendParam("mainToken/token?a=anyValue", "p", "12"));
		
		//when param is already set
		assertEquals("token?p=12", PlaceHandlerHelper.appendParam("token?p=10", "p", "12"));
		assertEquals("token?p=12&a=anyValue", PlaceHandlerHelper.appendParam("token?p=10&a=anyValue", "p", "12"));
		assertEquals("token?param=12", PlaceHandlerHelper.appendParam("token?param=10", "param", "12"));
		assertEquals("token?param=12&a=anyValue", PlaceHandlerHelper.appendParam("token?param=10&a=anyValue", "param", "12"));
		assertEquals("token?a=1&param=12&b=2", PlaceHandlerHelper.appendParam("token?a=1&param=10&b=2", "param", "12"));
		
		//when name of param is part of other param's name (pre-, post-, infix)
		assertEquals("token?xxxparam=300&xxx=10", PlaceHandlerHelper.appendParam("token?xxxparam=300", "xxx", "10"));
		assertEquals("token?paramxxx=300&xxx=10", PlaceHandlerHelper.appendParam("token?paramxxx=300", "xxx", "10"));
		assertEquals("token?pxxxaram=300&xxx=10", PlaceHandlerHelper.appendParam("token?pxxxaram=300", "xxx", "10"));
		assertEquals("token?xyx=300&y=10", PlaceHandlerHelper.appendParam("token?xyx=300&y=12", "y", "10"));
		assertEquals("token?yx=300&y=10", PlaceHandlerHelper.appendParam("token?yx=300&y=12", "y", "10"));
		assertEquals("token?xy=300&y=10", PlaceHandlerHelper.appendParam("token?xy=300&y=12", "y", "10"));
	}

	@Test
	public void getPlacePartTest() {
		assertEquals("", PlaceHandlerHelper.getPlacePart(""));
		assertEquals("token", PlaceHandlerHelper.getPlacePart("token"));
		assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1/token2"));
		assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1/token2/"));
		
		assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1/token2?id=2"));
		assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1/token2?id=2&c=asd123"));
		
		assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1?id=2/token2"));
		assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1?id=2&c=asd123/token2"));
		assertEquals("token1/token2", PlaceHandlerHelper.getPlacePart("token1?id=2&c=asd123/token2?id=2&c=asd123"));
		assertEquals("token1/token2/token3", PlaceHandlerHelper.getPlacePart("token1?id=2&c=asd123/token2?id=2&c=asd123/token3"));
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
		assertEquals(map1.size(), map2.size());
		
		//keys
		Collection<String> keys1 = new ArrayList<String>(map1.keySet());
		for(String key : map2.keySet()) {
			assertTrue(key, keys1.remove(key));
		}
		assertEquals(0, keys1.size());
	
		//values
		for(String key : map2.keySet())
			assertEquals(map1.get(key), map2.get(key));
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
	
	@Test
	public void testCreateParentLevelToken(){
		assertEquals("a/b?id=0", PlaceHandlerHelper.createParentLevelMenuToken("a/b?id=0/c"));
		assertEquals("", PlaceHandlerHelper.createParentLevelMenuToken("a"));
	}
	
	@Test
	public void levelOfChangeTest(){
		assertEquals(-1, PlaceHandlerHelper.levelOfChange(Arrays.asList("a"), Arrays.asList("a")));
		assertEquals(0, PlaceHandlerHelper.levelOfChange(Arrays.asList("a"), Arrays.asList("b")));
		assertEquals(1, PlaceHandlerHelper.levelOfChange(Arrays.asList("a", "b"), Arrays.asList("a")));
		assertEquals(1, PlaceHandlerHelper.levelOfChange(Arrays.asList("a"), Arrays.asList("a", "b")));
		assertEquals(2, PlaceHandlerHelper.levelOfChange(Arrays.asList("a", "b", "c"), Arrays.asList("a", "b")));
		assertEquals(0, PlaceHandlerHelper.levelOfChange(Arrays.asList("a", "b", "c"), Arrays.asList("b")));
		
	}
	
	@Test
	public void findActualLevelWithParamsTest(){
		assertEquals("home/devices?deviceGroups=1&chart=false&details=true/device?deviceId=100000", 
				PlaceHandlerHelper.findActualLevelWithParams(
						"home/devices?deviceGroups=1&chart=false&details=true/device?deviceId=100000/general", 
						"home/devices/device?deviceId=100000"));
	}
	
	@Test
	public void findActualLevelWithParamsTest2(){
		assertEquals("home/devices?deviceGroups=1&chart=false&details=true/device?deviceId=100000", 
				PlaceHandlerHelper.findActualLevelWithParams(
						"home/devices?deviceGroups=1&chart=false&details=true/route?deviceId=100000&when=rt", 
						"home/devices/device?deviceId=100000"));
	}
}
