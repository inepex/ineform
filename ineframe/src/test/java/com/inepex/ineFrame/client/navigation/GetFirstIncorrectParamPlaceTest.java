package com.inepex.ineFrame.client.navigation;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.inepex.ineFrame.client.navigation.places.DummyPageProvider;
import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineFrame.client.navigation.places.SimpleCachingPlace;
import com.inepex.ineFrame.client.page.InePage;

public class GetFirstIncorrectParamPlaceTest {
	
	@Test
	public void test() {
		TestPlaceHierarchyProv prov = new TestPlaceHierarchyProv();
		prov.createPlaceHierarchy();
		
		//no dynamic params by selection
		Assert.assertNull(PlaceHandlerHelper.getFirstIncorrectParamPlace("", prov.getPlaceRoot()));
		Assert.assertNull(PlaceHandlerHelper.getFirstIncorrectParamPlace("A", prov.getPlaceRoot()));
		Assert.assertNull(PlaceHandlerHelper.getFirstIncorrectParamPlace("A/a", prov.getPlaceRoot()));
		Assert.assertNull(PlaceHandlerHelper.getFirstIncorrectParamPlace("A/d/1", prov.getPlaceRoot()));
		
		//dynamic params are set
		Assert.assertNull(PlaceHandlerHelper.getFirstIncorrectParamPlace("A/b?b=1", prov.getPlaceRoot()));
		Assert.assertNull(PlaceHandlerHelper.getFirstIncorrectParamPlace("A/c?param=1", prov.getPlaceRoot()));
		Assert.assertNull(PlaceHandlerHelper.getFirstIncorrectParamPlace("A/d/2?paramOfPlace2=1", prov.getPlaceRoot()));
		
		Assert.assertEquals("A/b", PlaceHandlerHelper.getFirstIncorrectParamPlace("A/b", prov.getPlaceRoot()));
		Assert.assertEquals("A/b", PlaceHandlerHelper.getFirstIncorrectParamPlace("A/b/ooo", prov.getPlaceRoot()));
		Assert.assertEquals("A/b?b=1/ooo", PlaceHandlerHelper.getFirstIncorrectParamPlace("A/b?b=1/ooo", prov.getPlaceRoot()));
		
		Assert.assertEquals("A/d/2", PlaceHandlerHelper.getFirstIncorrectParamPlace("A/d/2", prov.getPlaceRoot()));
	}

	private class TestPlaceHierarchyProv extends DefaultPlaceHierarchyProvider {

		@Override
		public void createPlaceHierarchy() {
			placeRoot
				.addChildGC("A", new SimpleCachingPlace(new DummyPageProvider()))
					.addChild("a", new SimpleCachingPlace(new DummyPageProvider()))
					.addChildGC("b", new SimpleParamPlace("b"))
						.addChild("ooo", new SimpleParamPlace("oooP"))
						.getParent()
					.addChild("c", new SimpleParamPlace("param"))
					.addChildGC("d", new SimpleCachingPlace(new DummyPageProvider()))
						.addChild("1", new SimpleCachingPlace(new DummyPageProvider()))
						.addChild("2", new SimpleParamPlace("paramOfPlace2"))
						.addChild("3", new SimpleCachingPlace(new DummyPageProvider()))
						.getParent()
					.addChild("e", new SimpleCachingPlace(new DummyPageProvider()))
					.getParent()
				.addChild("B", new SimpleCachingPlace(new DummyPageProvider()))
				.addChild("C", new SimpleCachingPlace(new DummyPageProvider()));
		}

		@Override
		public List<String> getCurrentMenuRoot() {
			throw new RuntimeException();
		}
	}
	
	private class SimpleParamPlace extends ParamPlace {

		private final String paramName;
		
		SimpleParamPlace(String token) {
			this.paramName=token;
		}
		
		@Override
		public boolean notifyParamChangedReturnIsParamSet(
				Map<String, String> urlParams) {
			return urlParams.containsKey(paramName);
		}
		
		@Override
		public String getChildToken() {
			throw new RuntimeException();
		}

		@Override
		public ParamPlacePresenter getSelectorPresenter() {
			throw new RuntimeException();
		}

		@Override
		public InePage getAssociatedPage() {
			throw new RuntimeException();
		}
	
	}
}
