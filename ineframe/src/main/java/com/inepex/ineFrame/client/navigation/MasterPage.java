package com.inepex.ineFrame.client.navigation;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;

public interface MasterPage {
	
	public static interface View {
		public IsWidget asWidget();
	}
	
	void render(InePlace place, Map<String, String> urlParams);
	public IsWidget getView();
}