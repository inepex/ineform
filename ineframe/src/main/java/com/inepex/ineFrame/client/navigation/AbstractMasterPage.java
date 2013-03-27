package com.inepex.ineFrame.client.navigation;

import java.util.Map;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.inepex.ineFrame.client.page.InePage;

public abstract class AbstractMasterPage implements MasterPage {
	
	protected final MasterPage.View view;
	protected final EventBus eventBus;
	
	public AbstractMasterPage(MasterPage.View view, EventBus eventBus) {
		this.view = view;
		this.eventBus = eventBus;
	}
	
	protected abstract void showPage(InePlace place, InePage page);
	protected abstract void setUpPageStyle(InePage page);
	
	@Override
	public void render(final InePlace place, Map<String, String> urlParams) {
		final InePage page = place.getAssociatedPage();
		if(page==null)
			return;
		
		page.setCurrentPlace(place);
		setUpPageStyle(page);
		
		try {
			page.setUrlParameters(urlParams, new InePage.UrlParamsParsedCallback() {
				
				@Override
				public void onUrlParamsParsed() {
					onUrlParamsParsed(null);					
				}

				@Override
				public void onUrlParamsParsed(String redirectToToken) {
					showPage(place, page);
					page.onShow();
				}
			});
		} catch (Exception e) {
			eventBus.fireEvent(new PlaceRequestEvent(NavigationProperties.pageNotFoundPlace));
			e.printStackTrace();
		}
	}

	@Override
	public IsWidget getView(){
		return view.asWidget();
	}
}
