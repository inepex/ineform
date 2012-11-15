package com.inepex.ineFrame.client.navigation;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.page.InePage;
import com.inepex.ineFrame.client.page.defaults.DummyPage;

public abstract class AbstractMasterPage implements MasterPage {
	
	protected final AsyncStatusIndicator statusIndicator;
	protected final MasterPage.View view;
	
	public AbstractMasterPage(AsyncStatusIndicator statusIndicator, MasterPage.View view) {
		this.statusIndicator=statusIndicator;
		this.view = view;
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
			statusIndicator.onGeneralFailure(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void renderForbidden(InePlace place) {
		InePage forbiddenPage = new DummyPage("<h2>access denied</h2>");
		forbiddenPage.onShow();
		showPage(place, forbiddenPage);
	}
	
	@Override
	public IsWidget getView(){
		return view.asWidget();
	}
}
