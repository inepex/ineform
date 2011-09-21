package com.inepex.ineFrame.client.navigation;

import java.util.Map;

import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.page.InePage;
import com.inepex.ineFrame.client.page.defaults.DummyPage;

public abstract class AbstractMasterPage implements MasterPage {
	
	protected final AsyncStatusIndicator statusIndicator;
	
	public AbstractMasterPage(AsyncStatusIndicator statusIndicator) {
		this.statusIndicator=statusIndicator;
	}
	
	protected abstract void showPage(InePage page);
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
					showPage(page);
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
		showPage(new DummyPage("<h2>access denied</h2>"));
	}
}
