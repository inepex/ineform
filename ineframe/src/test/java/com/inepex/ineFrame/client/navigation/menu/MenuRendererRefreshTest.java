package com.inepex.ineFrame.client.navigation.menu;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.navigation.DefaultPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.places.DummyPageProvider;
import com.inepex.ineFrame.client.navigation.places.SimpleCachingPlace;
import com.inepex.ineom.shared.util.SharedUtil;

public class MenuRendererRefreshTest extends MenuRendererTestBase {


	PlainPlaceHierarchyProv phProvider;
	
	@Before
	public void init(){
		phProvider = new PlainPlaceHierarchyProv();
		phProvider.createPlaceHierarchy();
		initMocks(phProvider);
	}
	
	/**
	 * load a place, then load its sibling
	 */
	@Test
	public void testChangeOnSameLevel(){
		PlainPlaceHierarchyProv phProvider = new PlainPlaceHierarchyProv();
		phProvider.createPlaceHierarchy();
		
		MenuRenderer renderer = new MenuRenderer(phProvider, eventBus, view, new NoAuthManager());
		
		phProvider._111.setHierarchicalToken("0/1/11/111");
		renderer.realizeNewPlaceOnMenu(phProvider._111, null);
		
		//1
		verifyTab(tabs[0], true, true, true);
		
		//11
		verifyTab(tabs[1], true, true, true);
		
		//111
		verifyTab(tabs[2], true, true, true);
		
		//112
		verifyTab(tabs[3], true, true, false);
		
		//load sibling page
		i = 0;
		tabs = new MenuRenderer.View.Tab[5];
		tabNames = new String[5];
		phProvider._112.setHierarchicalToken("0/1/11/112");
		renderer.realizeNewPlaceOnMenu(phProvider._112, null);
		
		//111
		verifyTab(tabs[0], true, true, false);
		
		//112
		verifyTab(tabs[1], true, true, true);

	}
	
	private class PlainPlaceHierarchyProv extends DefaultPlaceHierarchyProvider {

		public final InePlace parentPlace = new SimpleCachingPlace(new DummyPageProvider());
		public final InePlace _1 = new SimpleCachingPlace(new DummyPageProvider()).setMenuName("1");
		public final InePlace _11 = new SimpleCachingPlace(new DummyPageProvider()).setMenuName("11");
		public final InePlace _111 = new SimpleCachingPlace(new DummyPageProvider()).setMenuName("111");
		public final InePlace _112 = new SimpleCachingPlace(new DummyPageProvider()).setMenuName("112");
		
		@Override
		public void createPlaceHierarchy() {
			placeRoot
				.addChildGC("0", parentPlace)
					.addChildGC("1", _1)
						.addChildGC("11", _11)
							.addChild("111", _111)
							.addChild("112", _112);						
		}
		

		@Override
		public List<String> getCurrentMenuRoot() {
			return SharedUtil.Li("0");
		}
	}
	
	
}
