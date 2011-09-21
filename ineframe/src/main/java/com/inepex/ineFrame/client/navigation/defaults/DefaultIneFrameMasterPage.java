package com.inepex.ineFrame.client.navigation.defaults;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.navigation.AbstractMasterPage;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.header.IneFrameHeader;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer;
import com.inepex.ineFrame.client.page.InePage;

@Singleton
public class DefaultIneFrameMasterPage extends AbstractMasterPage {

	public static interface View {
	}
	
	private final MenuRenderer menuRenderer;
	private final IneFrameHeader header;
	
	@Inject
	public DefaultIneFrameMasterPage(AsyncStatusIndicator statusIndicator, MenuRenderer menuRenderer,
			IneFrameHeader header) {
		super(statusIndicator);
		this.menuRenderer=menuRenderer;
		this.header=header;
	}
	
	@Override
	public void render(InePlace place, Map<String, String> urlParams) {
		header.refresh();
		menuRenderer.realizeNewPlaceOnMenu(place, urlParams);
		super.render(place, urlParams);
	}
	
	@Override
	protected void setUpPageStyle(InePage page) {
		page.asWidget().addStyleName(ResourceHelper.getRes().style().pageContent());
	}

	@Override
	protected void showPage(InePage page) {
		menuRenderer.showPage(page);
	}

}
