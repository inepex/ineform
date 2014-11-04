package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.navigation.NavigationDrawer;
import com.inepex.ineFrame.shared.IneformAsyncCallback;

public class DefaultNavigationDrawer extends FlowPanel implements NavigationDrawer {

	@Override
	public Widget asWidget() {
		return this;
	}

	public DefaultNavigationDrawer() {
		add(new Label("NavigationDrawer"));
	}

	@Override
	public void disableAliasMode(IneformAsyncCallback<Void> callback, String name, String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableAliasMode(IneformAsyncCallback<Void> callback, String name, String email) {
		// TODO Auto-generated method stub
		
	}
}
