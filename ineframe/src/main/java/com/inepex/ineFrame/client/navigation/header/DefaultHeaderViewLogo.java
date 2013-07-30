package com.inepex.ineFrame.client.navigation.header;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;

public class DefaultHeaderViewLogo implements HeaderViewLogo {

	@Override
	public Widget createLogo() {
		Image logo = new Image(ResourceHelper.getRes().logo());
		logo.setStyleName(ResourceHelper.getRes().style().logo());
		return logo;
	}

}
