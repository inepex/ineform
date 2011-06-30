package com.inepex.ineFrame.client.navigation.menu;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.shared.EventBus;
import com.inepex.ineFrame.client.navigation.DefaultPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.PlaceNode;
import com.inepex.ineFrame.client.navigation.defaults.DummyPageProvider;
import com.inepex.ineFrame.client.navigation.defaults.SimpleCachingPlace;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View.Tab;

public class MenuRendererShowChildreWhenActiveTest {
	
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
		
		MenuRenderer renderer = new MenuRenderer(phProvider, eventBus, view);
		
		renderer.realizeNewPlace("plainChild", phProvider.plainPlace);
		
		//4 menu item
		verify(view, times(5)).createTab(anyString(), anyInt());
		verify(view, times(4)).createTab(anyString(), eq(0));
		verify(view, times(1)).createTab(anyString(), eq(1));
		
		//plain place
		verify(tabs[0], times(1)).addChild(eq(tabs[4]));
		
		//visibleItem1
		verify(tabs[4], never()).addChild(any(MenuRenderer.View.Tab.class));
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
		final MenuRenderer.View.Tab[] tabs = new MenuRenderer.View.Tab[7];
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
		
		MenuRenderer renderer = new MenuRenderer(phProvider, eventBus, view);
		
		renderer.realizeNewPlace("onlyVisibleWhenActiveAndHasName", phProvider.onlyVisibleWhenActiveAndHasName);
		
		verify(view, times(1)).clearView();
		
		//4 menu item
		verify(view, times(7)).createTab(anyString(), anyInt());
		verify(view, times(4)).createTab(anyString(), eq(0));
		verify(view, times(3)).createTab(anyString(), eq(1));
		
		//only visible when... and has name
		verify(tabs[3], times(1)).addChild(eq(tabs[4]));
		verify(tabs[3], times(1)).addChild(eq(tabs[5]));
		
		//visibleItem2
		verify(tabs[4], never()).addChild(any(MenuRenderer.View.Tab.class));
		verify(tabs[4], times(1)).setClickable(true);
		verify(tabs[4], never()).setClickable(false);
		verify(tabs[4], times(1)).setEnabled(true);
		verify(tabs[4], never()).setEnabled(false);
		verify(tabs[4], times(1)).setItemVisible(true);
		verify(tabs[4], never()).setItemVisible(false);
		verify(tabs[4], times(1)).setSelected(false);
		verify(tabs[4], never()).setSelected(true);
		
		//visibleItem3
		verify(tabs[5], never()).addChild(any(MenuRenderer.View.Tab.class));
		verify(tabs[5], times(1)).setClickable(true);
		verify(tabs[5], never()).setClickable(false);
		verify(tabs[5], times(1)).setEnabled(true);
		verify(tabs[5], never()).setEnabled(false);
		verify(tabs[5], times(1)).setItemVisible(true);
		verify(tabs[5], never()).setItemVisible(false);
		verify(tabs[5], times(1)).setSelected(false);
		verify(tabs[5], never()).setSelected(true);
		
		//invisible
		verify(tabs[6], never()).addChild(any(MenuRenderer.View.Tab.class));
		verify(tabs[6], times(1)).setClickable(false);
		verify(tabs[6], never()).setClickable(true);
		verify(tabs[6], times(1)).setEnabled(true);
		verify(tabs[6], never()).setEnabled(false);
		verify(tabs[6], times(1)).setItemVisible(false);
		verify(tabs[6], never()).setItemVisible(true);
		verify(tabs[6], times(1)).setSelected(false);
		verify(tabs[6], never()).setSelected(true);
		
	}
	
	private class PlaceHierarchyProv extends DefaultPlaceHierarchyProvider {

		public final InePlace parentPlace = new SimpleCachingPlace(new DummyPageProvider());
		
		public final InePlace plainPlace = new SimpleCachingPlace(new DummyPageProvider())
								.setShowChildreWhenActive(true)
								;
		
		public final InePlace namedPlace = new SimpleCachingPlace(new DummyPageProvider())
							.setMenuName("Has menu name");
		
		public final InePlace visibleWhenActive = new SimpleCachingPlace(new DummyPageProvider())
							.setOnlyVisibleWhenActive(true);
		
		public final InePlace onlyVisibleWhenActiveAndHasName = new SimpleCachingPlace(new DummyPageProvider())
							.setOnlyVisibleWhenActive(true)
							.setShowChildreWhenActive(true)
							.setMenuName("Name of menu")
							;
		
		@Override
		public void createPlaceHierarchy() {
			realRoot
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
						.addChild("insivisble", new SimpleCachingPlace(new DummyPageProvider())
							.setOnlyVisibleWhenActive(true))
						.getParent()
					.getParent();
		}

		@Override
		public PlaceNode getCurrentRoot() {
			return createCurrentRootCached(realRoot.findNodeById("MenuParent"));
		}
	}
}
