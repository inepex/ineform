package com.inepex.ineFrame.client.navigation.defaults;

import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.MasterPage;
import com.inepex.ineFrame.client.navigation.header.IneFrameHeader;
import com.inepex.ineFrame.client.page.InePage;

public abstract class AbstractMasterPage implements MasterPage {
	
	protected final AsyncStatusIndicator statusIndicator;
	
	public AbstractMasterPage(AsyncStatusIndicator statusIndicator) {
		this.statusIndicator=statusIndicator;
	}
	

	protected abstract FlowPanel getPanel();
	
	@Override
	public void render(final InePlace place, Map<String, String> urlParams) {
		getPanel().clear();
		
		final InePage page = place.getAssociatedPage();
		if(page==null)
			return;
		
		page.setCurrentPlace(place);
		page.asWidget().addStyleName(ResourceHelper.getRes().style().pageContent());
		
		try {
			page.setUrlParameters(urlParams, new InePage.UrlParamsParsedCallback() {
				
				@Override
				public void onUrlParamsParsed() {
					getPanel().add(page.asWidget());
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
		getPanel().clear();
		getPanel().add(new HTML("<h2>access denied</h2>"));
	}
}
