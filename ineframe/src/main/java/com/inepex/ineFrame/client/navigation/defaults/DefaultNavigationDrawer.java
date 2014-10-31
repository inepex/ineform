package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.navigation.NavigationDrawer;
import com.inepex.ineFrame.client.util.DesignConstants;

public class DefaultNavigationDrawer extends FlowPanel implements NavigationDrawer {

	@Override
	public Widget asWidget() {
		return this;
	}

	public DefaultNavigationDrawer() {
		add(new Label("NavigationDrawer"));
		setSize(DesignConstants.navigationDrawerWidth(), "100%");
		asWidget().getElement().setId("NavigationDrawer");
		setStyleName(Res.INST.get().style().NavigationDrawer());
	}
}
