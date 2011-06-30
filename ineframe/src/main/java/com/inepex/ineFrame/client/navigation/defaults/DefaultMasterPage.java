package com.inepex.ineFrame.client.navigation.defaults;

import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.MasterPage;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer;
import com.inepex.ineFrame.client.page.InePage;

@Singleton
public class DefaultMasterPage extends HandlerAwareFlowPanel implements MasterPage, IsWidget{

	private final MenuRenderer menuRenderer;
	private final FlowPanel contentPanel;
	
	private final AsyncStatusIndicator statusIndicator;
	
	@Inject
	public DefaultMasterPage(AsyncStatusIndicator statusIndicator, MenuRenderer menuRenderer) {
		this.statusIndicator=statusIndicator;
		this.menuRenderer=menuRenderer;
		this.contentPanel=new FlowPanel();
		
		add((Widget)menuRenderer.getView());
		add(contentPanel);
	}
	
	@Override
	public void render(final InePlace place, Map<String, String> urlParams) {
		
		final InePage page = place.getAssociatedPage();
		page.setCurrentPlace(place);
		
		try {
			menuRenderer.realizeNewPlace(place);
			contentPanel.clear();
			
			page.setUrlParameters(urlParams, new InePage.UrlParamsParsedCallback() {
				
				@Override
				public void onUrlParamsParsed() {
					contentPanel.add(page.asWidget());
					page.onShow();
					
				}
			});
		} catch (Exception e) {
			statusIndicator.onGeneralFailure(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void renderForbidden(InePlace place) {
		menuRenderer.realizeNewPlace(place);
		contentPanel.clear();
		
		contentPanel.add(new HTML("<h2>access denied</h2>"));
	}
}
