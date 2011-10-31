package com.inepex.ineFrame.client.navigation.defaults;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.navigation.MasterPage;
import com.inepex.ineFrame.client.navigation.header.IneFrameHeader;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer;
import com.inepex.ineFrame.client.page.InePage;

@Singleton
public class NoPaddingMasterPage extends DefaultIneFrameMasterPage{

	@Inject
	public NoPaddingMasterPage(AsyncStatusIndicator statusIndicator, MenuRenderer menuRenderer, IneFrameHeader header,
			MasterPage.View view) {
		super(statusIndicator, menuRenderer, header, view);
	}

	@Override
	protected void setUpPageStyle(InePage page) {
		//nothing to do
	}
	
}
