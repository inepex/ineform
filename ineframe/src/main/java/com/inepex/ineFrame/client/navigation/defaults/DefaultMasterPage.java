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
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer;
import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineFrame.client.page.InePage;

@Singleton
public class DefaultMasterPage extends HandlerAwareFlowPanel implements MasterPage, IsWidget{

	private final MenuRenderer menuRenderer;
	
	private final AsyncStatusIndicator statusIndicator;
	
	@Inject
	public DefaultMasterPage(AsyncStatusIndicator statusIndicator, MenuRenderer menuRenderer) {
		this.statusIndicator=statusIndicator;
		this.menuRenderer=menuRenderer;
		
		this.add((Widget)menuRenderer.getView());
	}
	
	@Override
	public void render(final InePlace place, Map<String, String> urlParams) {
		
		final FlowPanel fp = menuRenderer.realizeNewPlace(place);
		fp.addStyleName(ResourceHelper.getRes().style().pageContent());
		
		if(place instanceof ParamPlace && ((ParamPlace) place).getSelectorWidget()!=null) {
			((ParamPlace) place).getSelectorWidget().realizeUrlParams(urlParams);
		}
		
		final InePage page = place.getAssociatedPage();
		if(page==null)
			return;
		
		page.setCurrentPlace(place);
		
		try {
			page.setUrlParameters(urlParams, new InePage.UrlParamsParsedCallback() {
				
				@Override
				public void onUrlParamsParsed() {
					fp.add(page.asWidget());
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
		menuRenderer.realizeNewPlace(place).add(new HTML("<h2>access denied</h2>"));
	}
}