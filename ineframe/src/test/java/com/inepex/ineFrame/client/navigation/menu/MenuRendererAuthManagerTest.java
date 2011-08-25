package com.inepex.ineFrame.client.navigation.menu;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
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
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.DefaultPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View.Tab;
import com.inepex.ineFrame.client.navigation.places.DummyPageProvider;
import com.inepex.ineFrame.client.navigation.places.SimpleCachingPlace;
import com.inepex.ineom.shared.util.SharedUtil;

public class MenuRendererAuthManagerTest {
	
	@Test
	public void testAllTabsAreVisible() { 
		final int tabCount = 3;
	
		AuthManager authManager = mock(AuthManager.class);
		when(authManager.doUserHaveAnyOfRoles((String[])anyVararg())).thenReturn(true);
		
		EventBus eventBus = mock(EventBus.class);
		final MenuRenderer.View.Tab[] tabs = new MenuRenderer.View.Tab[tabCount];
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
		
		MenuRenderer renderer = new MenuRenderer(phProvider, eventBus, view, authManager);
		
		phProvider.plainPlace1.setHierarchicalToken("MenuParent/place1");
		renderer.realizeNewPlaceOnMenu(phProvider.plainPlace1, null);
		
		verify(view, times(1)).clearView();
		
		//4 menu item
		verify(view, times(tabCount)).createTab(anyString(), eq(0));
		
		//place1
		verify(tabs[0], times(1)).setClickable(false);
		verify(tabs[0], never()).setClickable(true);
		verify(tabs[0], times(1)).setEnabled(true);
		verify(tabs[0], never()).setEnabled(false);
		verify(tabs[0], times(1)).setItemVisible(true);
		verify(tabs[0], never()).setItemVisible(false);
		verify(tabs[0], times(1)).setSelected(true);
		verify(tabs[0], never()).setSelected(false);
		
		//place2
		verify(tabs[1], times(1)).setClickable(true);
		verify(tabs[1], never()).setClickable(false);
		verify(tabs[1], times(1)).setEnabled(true);
		verify(tabs[1], never()).setEnabled(false);
		verify(tabs[1], times(1)).setItemVisible(true);
		verify(tabs[1], never()).setItemVisible(false);
		verify(tabs[1], times(1)).setSelected(false);
		verify(tabs[1], never()).setSelected(true);
		
		//place3
		verify(tabs[2], times(1)).setClickable(true);
		verify(tabs[2], never()).setClickable(false);
		verify(tabs[2], times(1)).setEnabled(true);
		verify(tabs[2], never()).setEnabled(false);
		verify(tabs[2], times(1)).setItemVisible(true);
		verify(tabs[2], never()).setItemVisible(false);
		verify(tabs[2], times(1)).setSelected(false);
		verify(tabs[2], never()).setSelected(true);
	}
	
	@Test
	public void testBaseRole() { 
		final int tabCount = 2;
	
		AuthManager authManager = mock(AuthManager.class);
		when(authManager.doUserHaveAnyOfRoles((String[])anyVararg())).thenAnswer(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				if(invocation.getArguments()==null || invocation.getArguments().length==0)
					return false;
				
				Object arg0 = invocation.getArguments()[0];
				if(arg0==null)
					return false;
				
				String[] userRoles;
				if(arg0 instanceof String)
					userRoles = new String[]{(String) arg0};
				else
					userRoles= (String[]) arg0;
				
				for(String role : userRoles) {
					if("baseRole".equals(role))
						return true;
				}
				
				return false;
			}
		});
		
		EventBus eventBus = mock(EventBus.class);
		final MenuRenderer.View.Tab[] tabs = new MenuRenderer.View.Tab[tabCount];
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
		
		MenuRenderer renderer = new MenuRenderer(phProvider, eventBus, view, authManager);
		
		phProvider.plainPlace1.setHierarchicalToken("MenuParent/place1");
		renderer.realizeNewPlaceOnMenu(phProvider.plainPlace1, null);
		
		verify(view, times(1)).clearView();
		
		//4 menu item
		verify(view, times(tabCount)).createTab(anyString(), eq(0));
		
		//place1
		verify(tabs[0], times(1)).setClickable(false);
		verify(tabs[0], never()).setClickable(true);
		verify(tabs[0], times(1)).setEnabled(true);
		verify(tabs[0], never()).setEnabled(false);
		verify(tabs[0], times(1)).setItemVisible(true);
		verify(tabs[0], never()).setItemVisible(false);
		verify(tabs[0], times(1)).setSelected(true);
		verify(tabs[0], never()).setSelected(false);
		
		//place2
		verify(tabs[1], times(1)).setClickable(true);
		verify(tabs[1], never()).setClickable(false);
		verify(tabs[1], times(1)).setEnabled(true);
		verify(tabs[1], never()).setEnabled(false);
		verify(tabs[1], times(1)).setItemVisible(true);
		verify(tabs[1], never()).setItemVisible(false);
		verify(tabs[1], times(1)).setSelected(false);
		verify(tabs[1], never()).setSelected(true);
	}
	
	@Test
	public void testOtherRole() { 
		final int tabCount = 1;
	
		AuthManager authManager = mock(AuthManager.class);
		when(authManager.doUserHaveAnyOfRoles((String[])anyVararg())).thenAnswer(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				if(invocation.getArguments()==null || invocation.getArguments().length==0)
					return false;
				
				for(int i=0; i<invocation.getArguments().length; i++) {
					String role = (String) invocation.getArguments()[i];
					if("otherRole".equals(role))
						return true;
				}
				
				return false;
			}
		});
		
		EventBus eventBus = mock(EventBus.class);
		final MenuRenderer.View.Tab[] tabs = new MenuRenderer.View.Tab[tabCount];
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
		
		MenuRenderer renderer = new MenuRenderer(phProvider, eventBus, view, authManager);
		
		phProvider.plainPlace1.setHierarchicalToken("MenuParent/place1");
		renderer.realizeNewPlaceOnMenu(phProvider.plainPlace1, null);
		
		verify(view, times(1)).clearView();
		
		//4 menu item
		verify(view, times(tabCount)).createTab(anyString(), eq(0));
		
		//place2
		verify(tabs[0], times(1)).setClickable(true);
		verify(tabs[0], never()).setClickable(false);
		verify(tabs[0], times(1)).setEnabled(true);
		verify(tabs[0], never()).setEnabled(false);
		verify(tabs[0], times(1)).setItemVisible(true);
		verify(tabs[0], never()).setItemVisible(false);
		verify(tabs[0], times(1)).setSelected(false);
		verify(tabs[0], never()).setSelected(true);
	}

	private class PlainPlaceHierarchyProv extends DefaultPlaceHierarchyProvider {

		public final InePlace parentPlace = new SimpleCachingPlace(new DummyPageProvider());
		
		public final InePlace plainPlace1 = new SimpleCachingPlace(new DummyPageProvider())
				.setMenuName("place1")
				.addAllowedRoles("baseRole");
		
		public final InePlace plainPlace2 = new SimpleCachingPlace(new DummyPageProvider())
			.setMenuName("place2")
			.addAllowedRoles("baseRole", "otherRole");
		
		public final InePlace plainPlace3 = new SimpleCachingPlace(new DummyPageProvider())
			.setMenuName("place3");
		
		@Override
		public void createPlaceHierarchy() {
			placeRoot
				.addChildGC("MenuParent", parentPlace)
					.addChild("place1", plainPlace1)
					.addChild("place2", plainPlace2)
					.addChild("place3", plainPlace3)
					.getParent();
		}

		@Override
		public List<String> getCurrentMenuRoot() {
			return SharedUtil.Li("MenuParent");
		}
	}
}
