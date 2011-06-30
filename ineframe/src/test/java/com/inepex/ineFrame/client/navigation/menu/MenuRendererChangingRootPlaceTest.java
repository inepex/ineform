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

/**
 * MenuRendererTest
 *
 */
public class MenuRendererChangingRootPlaceTest {
	
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
		
		renderer.realizeNewPlace("plainChild", phProvider.plainPlace);
		
		verify(view, times(1)).clearView();
		
		//4 menu item
		verify(view, times(4)).createTab(anyString(), anyInt());
		verify(view, times(4)).createTab(anyString(), eq(0));
		
		//plain place
		verify(tabs[0], never()).addChild(any(MenuRenderer.View.Tab.class));
		verify(tabs[0], times(1)).setClickable(false);
		verify(tabs[0], never()).setClickable(true);
		verify(tabs[0], times(1)).setEnabled(true);
		verify(tabs[0], never()).setEnabled(false);
		verify(tabs[0], times(1)).setItemVisible(false);
		verify(tabs[0], never()).setItemVisible(true);
		verify(tabs[0], times(1)).setSelected(true);
		verify(tabs[0], never()).setSelected(false);
		
		//has menu name
		verify(tabs[1], never()).addChild(any(MenuRenderer.View.Tab.class));
		verify(tabs[1], times(1)).setClickable(true);
		verify(tabs[1], never()).setClickable(false);
		verify(tabs[1], times(1)).setEnabled(true);
		verify(tabs[1], never()).setEnabled(false);
		verify(tabs[1], times(1)).setItemVisible(true);
		verify(tabs[1], never()).setItemVisible(false);
		verify(tabs[1], times(1)).setSelected(false);
		verify(tabs[1], never()).setSelected(true);
		
		//only visible when...
		verify(tabs[2], never()).addChild(any(MenuRenderer.View.Tab.class));
		verify(tabs[2], times(1)).setClickable(false);
		verify(tabs[2], never()).setClickable(true);
		verify(tabs[2], times(1)).setEnabled(true);
		verify(tabs[2], never()).setEnabled(false);
		verify(tabs[2], times(1)).setItemVisible(false);
		verify(tabs[2], never()).setItemVisible(true);
		verify(tabs[2], times(1)).setSelected(false);
		verify(tabs[2], never()).setSelected(true);
		
		//only visible when... and has name
		verify(tabs[3], never()).addChild(any(MenuRenderer.View.Tab.class));
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
		
		renderer.realizeNewPlace("onlyVisibleWhenActiveAndHasName", phProvider.onlyVisibleWhenActiveAndHasName);
		
		verify(view, times(1)).clearView();
		
		//4 menu item
		verify(view, times(4)).createTab(anyString(), anyInt());
		verify(view, times(4)).createTab(anyString(), eq(0));
		
		//plain place
		verify(tabs[0], never()).addChild(any(MenuRenderer.View.Tab.class));
		verify(tabs[0], times(1)).setClickable(false);
		verify(tabs[0], never()).setClickable(true);
		verify(tabs[0], times(1)).setEnabled(true);
		verify(tabs[0], never()).setEnabled(false);
		verify(tabs[0], times(1)).setItemVisible(false);
		verify(tabs[0], never()).setItemVisible(true);
		verify(tabs[0], times(1)).setSelected(false);
		verify(tabs[0], never()).setSelected(true);
		
		//has menu name
		verify(tabs[1], never()).addChild(any(MenuRenderer.View.Tab.class));
		verify(tabs[1], times(1)).setClickable(true);
		verify(tabs[1], never()).setClickable(false);
		verify(tabs[1], times(1)).setEnabled(true);
		verify(tabs[1], never()).setEnabled(false);
		verify(tabs[1], times(1)).setItemVisible(true);
		verify(tabs[1], never()).setItemVisible(false);
		verify(tabs[1], times(1)).setSelected(false);
		verify(tabs[1], never()).setSelected(true);
		
		//only visible when...
		verify(tabs[2], never()).addChild(any(MenuRenderer.View.Tab.class));
		verify(tabs[2], times(1)).setClickable(false);
		verify(tabs[2], never()).setClickable(true);
		verify(tabs[2], times(1)).setEnabled(true);
		verify(tabs[2], never()).setEnabled(false);
		verify(tabs[2], times(1)).setItemVisible(false);
		verify(tabs[2], never()).setItemVisible(true);
		verify(tabs[2], times(1)).setSelected(false);
		verify(tabs[2], never()).setSelected(true);
		
		//only visible when... and has name
		verify(tabs[3], never()).addChild(any(MenuRenderer.View.Tab.class));
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
							.setShowChildreWhenActive(false)
							.setMenuName("Name of menu");
		
		@Override
		public void createPlaceHierarchy() {
			realRoot
				.addChildGC("parent1", parentPlace)
					.addChildGC("parent2", parentPlace)
						.addChildGC("parent3", parentPlace)
							.addChildGC("plainChild", plainPlace)
								.addChild("youCanNotSeeInMenuBar", new SimpleCachingPlace(new DummyPageProvider()))
								.getParent()
							.addChild("hasMenuName", namedPlace)
							.addChild("onlyVisibleWhenActive", visibleWhenActive)
							.addChildGC("onlyVisibleWhenActiveAndHasName", onlyVisibleWhenActiveAndHasName)
								.addChild("youCanNotSeeInMenuBarTOO", new SimpleCachingPlace(new DummyPageProvider()))
								.getParent()
							.getParent()
						.getParent()
					.getParent()
				.getParent();
						
		}

		@Override
		public PlaceNode getCurrentRoot() {
			return createCurrentRootCached(realRoot.findNodeByHierarchicalId("parent1/parent2/parent3"));
		}
	}
}
