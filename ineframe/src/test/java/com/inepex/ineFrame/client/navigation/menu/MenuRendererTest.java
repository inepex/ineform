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
import com.inepex.ineFrame.client.navigation.DefaultPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.defaults.DummyPageProvider;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View.Tab;
import com.inepex.ineFrame.client.navigation.places.SimpleCachingPlace;
import com.inepex.ineom.shared.util.SharedUtil;

public class MenuRendererTest {

	/**
	 * display nothing (a first level place is selected)
	 * 
	 */
	@Test
	public void testFirstLevelPlace() { 
	
		EventBus eventBus = mock(EventBus.class);
		MenuRenderer.View view = mock(MenuRenderer.View.class);
		
		PlainPlaceHierarchyProv phProvider = new PlainPlaceHierarchyProv();
		phProvider.createPlaceHierarchy();
		
		MenuRenderer renderer = new MenuRenderer(phProvider, eventBus, view);
		
		renderer.realizeNewPlace(phProvider.parentPlace);
		
		verify(view, times(1)).clearView();
		verify(view, never()).createTab(anyString(), anyInt());
	}
	
	/**
	 * display 4 tabs setting selected, visible... proterties fine
	 */
	@Test
	public void testPlainPlaceIsSelected() { 
	
		EventBus eventBus = mock(EventBus.class);
		final MenuRenderer.View.Tab[] tabs = new MenuRenderer.View.Tab[4];
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
		
		PlainPlaceHierarchyProv phProvider = new PlainPlaceHierarchyProv();
		phProvider.createPlaceHierarchy();
		
		MenuRenderer renderer = new MenuRenderer(phProvider, eventBus, view);
		
		renderer.realizeNewPlace(phProvider.plainPlace);
		
		verify(view, times(1)).clearView();
		
		//4 menu item
		verify(view, times(4)).createTab(anyString(), anyInt());
		verify(view, times(4)).createTab(anyString(), eq(0));
		
		//plain place
		verify(tabs[0], times(1)).setClickable(false);
		verify(tabs[0], never()).setClickable(true);
		verify(tabs[0], times(1)).setEnabled(true);
		verify(tabs[0], never()).setEnabled(false);
		verify(tabs[0], times(1)).setItemVisible(false);
		verify(tabs[0], never()).setItemVisible(true);
		verify(tabs[0], times(1)).setSelected(true);
		verify(tabs[0], never()).setSelected(false);
		
		//has menu name
		verify(tabs[1], times(1)).setClickable(true);
		verify(tabs[1], never()).setClickable(false);
		verify(tabs[1], times(1)).setEnabled(true);
		verify(tabs[1], never()).setEnabled(false);
		verify(tabs[1], times(1)).setItemVisible(true);
		verify(tabs[1], never()).setItemVisible(false);
		verify(tabs[1], times(1)).setSelected(false);
		verify(tabs[1], never()).setSelected(true);
		
		//only visible when...
		verify(tabs[2], times(1)).setClickable(false);
		verify(tabs[2], never()).setClickable(true);
		verify(tabs[2], times(1)).setEnabled(true);
		verify(tabs[2], never()).setEnabled(false);
		verify(tabs[2], times(1)).setItemVisible(false);
		verify(tabs[2], never()).setItemVisible(true);
		verify(tabs[2], times(1)).setSelected(false);
		verify(tabs[2], never()).setSelected(true);
		
		//only visible when... and has name
		verify(tabs[3], times(1)).setClickable(false);
		verify(tabs[3], never()).setClickable(true);
		verify(tabs[3], times(1)).setEnabled(true);
		verify(tabs[3], never()).setEnabled(false);
		verify(tabs[3], times(1)).setItemVisible(false);
		verify(tabs[3], never()).setItemVisible(true);
		verify(tabs[3], times(1)).setSelected(false);
		verify(tabs[3], never()).setSelected(true);
	}
	
	/**
	 * display 4 tabs setting selected, visible... proterties fine
	 * 
	 */
	@Test
	public void testOnlyVisiblePlaceIsSelected() { 
	
		EventBus eventBus = mock(EventBus.class);
		final MenuRenderer.View.Tab[] tabs = new MenuRenderer.View.Tab[4];
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
		
		PlainPlaceHierarchyProv phProvider = new PlainPlaceHierarchyProv();
		phProvider.createPlaceHierarchy();
		
		MenuRenderer renderer = new MenuRenderer(phProvider, eventBus, view);
		
		renderer.realizeNewPlace(phProvider.onlyVisibleWhenActiveAndHasName);
		
		verify(view, times(1)).clearView();
		
		//4 menu item
		verify(view, times(4)).createTab(anyString(), anyInt());
		verify(view, times(4)).createTab(anyString(), eq(0));
		
		//plain place
		verify(tabs[0], times(1)).setClickable(false);
		verify(tabs[0], never()).setClickable(true);
		verify(tabs[0], times(1)).setEnabled(true);
		verify(tabs[0], never()).setEnabled(false);
		verify(tabs[0], times(1)).setItemVisible(false);
		verify(tabs[0], never()).setItemVisible(true);
		verify(tabs[0], times(1)).setSelected(false);
		verify(tabs[0], never()).setSelected(true);
		
		//has menu name
		verify(tabs[1], times(1)).setClickable(true);
		verify(tabs[1], never()).setClickable(false);
		verify(tabs[1], times(1)).setEnabled(true);
		verify(tabs[1], never()).setEnabled(false);
		verify(tabs[1], times(1)).setItemVisible(true);
		verify(tabs[1], never()).setItemVisible(false);
		verify(tabs[1], times(1)).setSelected(false);
		verify(tabs[1], never()).setSelected(true);
		
		//only visible when...
		verify(tabs[2], times(1)).setClickable(false);
		verify(tabs[2], never()).setClickable(true);
		verify(tabs[2], times(1)).setEnabled(true);
		verify(tabs[2], never()).setEnabled(false);
		verify(tabs[2], times(1)).setItemVisible(false);
		verify(tabs[2], never()).setItemVisible(true);
		verify(tabs[2], times(1)).setSelected(false);
		verify(tabs[2], never()).setSelected(true);
		
		//only visible when... and has names
		verify(tabs[3], times(1)).setClickable(false);
		verify(tabs[3], never()).setClickable(true);
		verify(tabs[3], times(1)).setEnabled(true);
		verify(tabs[3], never()).setEnabled(false);
		verify(tabs[3], times(1)).setItemVisible(true);
		verify(tabs[3], never()).setItemVisible(false);
		verify(tabs[3], times(1)).setSelected(true);
		verify(tabs[3], never()).setSelected(false);
	}

	private class PlainPlaceHierarchyProv extends DefaultPlaceHierarchyProvider {

		public final InePlace parentPlace = new SimpleCachingPlace(new DummyPageProvider());
		
		public final InePlace plainPlace = new SimpleCachingPlace(new DummyPageProvider());
		
		public final InePlace namedPlace = new SimpleCachingPlace(new DummyPageProvider())
							.setMenuName("Has menu name");
		
		public final InePlace visibleWhenActive = new SimpleCachingPlace(new DummyPageProvider())
							.setOnlyVisibleWhenActive(true);
		
		public final InePlace onlyVisibleWhenActiveAndHasName = new SimpleCachingPlace(new DummyPageProvider())
							.setOnlyVisibleWhenActive(true)
							.setShowChildrenWhenActive(false)
							.setMenuName("Name of menu");
		
		@Override
		public void createPlaceHierarchy() {
			placeRoot
				.addChildGC("MenuParent", parentPlace)
					.addChildGC("plainChild", plainPlace)
						.addChild("youCanNotSeeInMenuBar", new SimpleCachingPlace(new DummyPageProvider()))
						.getParent()
					.addChild("hasMenuName", namedPlace)
					.addChild("onlyVisibleWhenActive", visibleWhenActive)
					.addChildGC("onlyVisibleWhenActiveAndHasName", onlyVisibleWhenActiveAndHasName)
						.addChild("youCanNotSeeInMenuBarTOO", new SimpleCachingPlace(new DummyPageProvider()))
						.getParent()
					.getParent();
		}

		@Override
		public List<String> getCurrentMenuRoot() {
			return SharedUtil.Li("MenuParent");
		}
	}
}
