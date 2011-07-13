package com.inepex.ineFrame.client.navigation.menu;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.shared.EventBus;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.navigation.DefaultPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.defaults.DummyPageProvider;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View.Tab;
import com.inepex.ineFrame.client.navigation.places.SimpleCachingPlace;
import com.inepex.ineom.shared.util.SharedUtil;

public class MenuRendererShowChildreWhenActiveTest {

	/**
	 * display nothing (a first level place is selected)
	 * 
	 */
	@Test
	public void testFirstLevelPlace() { 
	
		EventBus eventBus = mock(EventBus.class);
		MenuRenderer.View view = mock(MenuRenderer.View.class);
		
		PlaceHierarchyProv phProvider = new PlaceHierarchyProv();
		phProvider.createPlaceHierarchy();
		
		MenuRenderer renderer = new MenuRenderer(phProvider, eventBus, view, new NoAuthManager());
		
		phProvider.parentPlace.setHierarchicalToken("MenuParent");
		renderer.realizeNewPlace(phProvider.parentPlace);
		
		verify(view, times(1)).clearView();
		verify(view, never()).createTab(anyString(), anyInt());
	}
	
	/**
	 * displays 4 tabs setting selected, visible... proterties fine
	 * 
	 * and displays 1 child tab additional
	 * 
	 */
	@Test
	public void testPlainPlaceIsSelected() { 
	
		EventBus eventBus = mock(EventBus.class);
		final MenuRenderer.View.Tab[] tabs = new MenuRenderer.View.Tab[5];
		MenuRenderer.View view = mock(MenuRenderer.View.class);
		when(view.createTab(anyString(), anyInt())).thenAnswer(new Answer<MenuRenderer.View.Tab>() {

			int i = 0;
			
			@Override
			public Tab answer(InvocationOnMock invocation) throws Throwable {
				MenuRenderer.View.Tab tab = mock(MenuRenderer.View.Tab.class);
				tabs[i++]=tab;
				return tab;
			}
		});
		
		PlaceHierarchyProv phProvider = new PlaceHierarchyProv();
		phProvider.createPlaceHierarchy();
		
		MenuRenderer renderer = new MenuRenderer(phProvider, eventBus, view,new NoAuthManager());
		
		phProvider.plainPlace.setHierarchicalToken("MenuParent/plainChild");
		renderer.realizeNewPlace(phProvider.plainPlace);
		
		//4 menu item
		verify(view, times(5)).createTab(anyString(), anyInt());
		verify(view, times(4)).createTab(anyString(), eq(0));
		verify(view, times(1)).createTab(anyString(), eq(1));
		
		//visibleItem1
		verify(tabs[4], times(1)).setClickable(true);
		verify(tabs[4], never()).setClickable(false);
		verify(tabs[4], times(1)).setEnabled(true);
		verify(tabs[4], never()).setEnabled(false);
		verify(tabs[4], times(1)).setItemVisible(true);
		verify(tabs[4], never()).setItemVisible(false);
		verify(tabs[4], times(1)).setSelected(false);
		verify(tabs[4], never()).setSelected(true);
	}
	
	/**
	 * display 4 tabs setting selected, visible... proterties fine
	 * showing children
	 * 
	 */
	@Test
	public void testOnlyVisiblePlaceIsSelected() { 
	
		EventBus eventBus = mock(EventBus.class);
		final MenuRenderer.View.Tab[] tabs = new MenuRenderer.View.Tab[6];
		MenuRenderer.View view = mock(MenuRenderer.View.class);
		when(view.createTab(anyString(), anyInt())).thenAnswer(new Answer<MenuRenderer.View.Tab>() {

			int i = 0;
			
			@Override
			public Tab answer(InvocationOnMock invocation) throws Throwable {
				MenuRenderer.View.Tab tab = mock(MenuRenderer.View.Tab.class);
				tabs[i++]=tab;
				return tab;
			}
		});
		
		PlaceHierarchyProv phProvider = new PlaceHierarchyProv();
		phProvider.createPlaceHierarchy();
		
		MenuRenderer renderer = new MenuRenderer(phProvider, eventBus, view, new NoAuthManager());
		
		phProvider.onlyVisibleWhenActiveAndHasName.setHierarchicalToken("MenuParent/onlyVisibleWhenActiveAndHasName");
		renderer.realizeNewPlace(phProvider.onlyVisibleWhenActiveAndHasName);
		
		verify(view, times(1)).clearView();
		
		//4 menu item
		verify(view, times(6)).createTab(anyString(), anyInt());
		verify(view, times(4)).createTab(anyString(), eq(0));
		verify(view, times(2)).createTab(anyString(), eq(1));
		
		//visibleItem2
		verify(tabs[4], times(1)).setClickable(true);
		verify(tabs[4], never()).setClickable(false);
		verify(tabs[4], times(1)).setEnabled(true);
		verify(tabs[4], never()).setEnabled(false);
		verify(tabs[4], times(1)).setItemVisible(true);
		verify(tabs[4], never()).setItemVisible(false);
		verify(tabs[4], times(1)).setSelected(false);
		verify(tabs[4], never()).setSelected(true);
		
		//visibleItem3
		verify(tabs[5], times(1)).setClickable(true);
		verify(tabs[5], never()).setClickable(false);
		verify(tabs[5], times(1)).setEnabled(true);
		verify(tabs[5], never()).setEnabled(false);
		verify(tabs[5], times(1)).setItemVisible(true);
		verify(tabs[5], never()).setItemVisible(false);
		verify(tabs[5], times(1)).setSelected(false);
		verify(tabs[5], never()).setSelected(true);
		
	}
	
	private class PlaceHierarchyProv extends DefaultPlaceHierarchyProvider {

		public final InePlace parentPlace = new SimpleCachingPlace(new DummyPageProvider());
		
		public final InePlace plainPlace = new SimpleCachingPlace(new DummyPageProvider())
								.setShowChildrenWhenActive(true)
								;
		
		public final InePlace namedPlace = new SimpleCachingPlace(new DummyPageProvider())
							.setMenuName("Has menu name");
		
		public final InePlace visibleWhenActive = new SimpleCachingPlace(new DummyPageProvider())
							.setOnlyVisibleWhenActive(true);
		
		public final InePlace onlyVisibleWhenActiveAndHasName = new SimpleCachingPlace(new DummyPageProvider())
							.setOnlyVisibleWhenActive(true)
							.setShowChildrenWhenActive(true)
							.setMenuName("Name of menu")
							;
		
		@Override
		public void createPlaceHierarchy() {
			placeRoot
				.addChildGC("MenuParent", parentPlace)
					.addChildGC("plainChild", plainPlace)
						.addChild("visibleItem1", new SimpleCachingPlace(new DummyPageProvider())
							.setMenuName("Visible 1"))
						.getParent()
					.addChild("hasMenuName", namedPlace)
					.addChild("onlyVisibleWhenActive", visibleWhenActive)
					.addChildGC("onlyVisibleWhenActiveAndHasName", onlyVisibleWhenActiveAndHasName)
						.addChild("visibleItem2", new SimpleCachingPlace(new DummyPageProvider())
							.setMenuName("visible2"))
						.addChildGC("visibleItem3", new SimpleCachingPlace(new DummyPageProvider())
							.setMenuName("visible3"))
							.addChild("invisibleToo", new SimpleCachingPlace(new DummyPageProvider())
								.setMenuName("invisibleToo1"))
							.getParent()
						.getParent()
					.getParent();
		}

		@Override
		public List<String> getCurrentMenuRoot() {
			return SharedUtil.Li("MenuParent");
		}
	}
}
