package com.inepex.ineFrame.client.navigation.menu;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Image;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View.Tab;

public class MenuRendererTestBase {
	EventBus eventBus;
	MenuRenderer.View view;
	MenuRenderer renderer;
	MenuRenderer.View.Tab[] tabs;
	String[] tabNames;
	
	int i = 0;
	
	public void initMocks(PlaceHierarchyProvider phProvider){
		eventBus = mock(EventBus.class);
		view = mock(MenuRenderer.View.class);
		tabs = new MenuRenderer.View.Tab[5];
		tabNames = new String[5];
		
		renderer = new MenuRenderer(phProvider, eventBus, view, new NoAuthManager());
		
		when(view.createTab(anyString(), Mockito.any(Image.class), anyInt())).thenAnswer(new Answer<MenuRenderer.View.Tab>() {
			
			@Override
			public Tab answer(InvocationOnMock invocation) throws Throwable {
				MenuRenderer.View.Tab tab = mock(MenuRenderer.View.Tab.class);
				tabNames[i] = (String)(invocation.getArguments()[0]);
				tabs[i++]=tab;				
				return tab;
			}
		});
	}
	
	public void verifyTab(MenuRenderer.View.Tab tab, boolean clickable, boolean visible, boolean selected){
		
		if (clickable){
			verify(tab, times(1)).setClickable(true);
			verify(tab, never()).setClickable(false);
		} else {
			verify(tab, times(1)).setClickable(false);
			verify(tab, never()).setClickable(true);
		}
		
		if (visible){
			verify(tab, times(1)).setItemVisible(true);
			verify(tab, never()).setItemVisible(false);
		} else {
			verify(tab, times(1)).setItemVisible(false);
			verify(tab, never()).setItemVisible(true);
		}
		
		if (selected){
			verify(tab, times(1)).setSelected(true);
			verify(tab, never()).setSelected(false);
		} else {
			verify(tab, times(1)).setSelected(false);
			verify(tab, never()).setSelected(true);
		}		

	}
}
