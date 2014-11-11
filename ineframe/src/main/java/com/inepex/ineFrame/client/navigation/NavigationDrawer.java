package com.inepex.ineFrame.client.navigation;

import com.google.gwt.user.client.ui.IsWidget;

public interface NavigationDrawer extends IsWidget {
	public void disableAliasMode(String name, String email);
	public void enableAliasMode(String name, String email);
	public void onDisplayed();	
	public void onHidden();		
	
}
