package com.inepex.ineFrame.client.navigation;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;

public interface MasterPage {
	
	public static interface View {
		public IsWidget asWidget();
		public void toggleNavigationDrawer();
		public void openNavigationDrawer();
		public void closeNavigationDrawer();
		public boolean isNavigationDrawerOpen();
		public void toggleMessagePanel();
		public void showMessagePanel();
		public void hideMessagePanel();
		public boolean isMessagePanelShown();
	}
	
	void render(InePlace place, Map<String, String> urlParams);
	public IsWidget getView();
}