package com.inepex.example.ContactManager.client.page;

import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.LoginBox;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;


public class LoginPage extends FlowPanelBasedPage {

	@Inject
	LoginPage(AuthManager authManager,HistoryProvider historyProvider, EventBus eventBus) {
		
		mainPanel.add(new CMLoginBox(authManager, historyProvider, eventBus));
		mainPanel.add(new HTML("<h2>To log in select one user:</h2>"));
		mainPanel.add(new HTML("<b>john.black@inepex.com</b><br /><i>pass123</i><br /><br />"));
		mainPanel.add(new HTML("<b>barbara.green@inepex.com</b><br /><i>pass123</i><br /><br />"));
		mainPanel.add(new HTML("<b>adam.white@inepex.com</b><br /><i>pass123</i><br /><br />"));
	}
	
	@Override
	protected void onShow(boolean isFirstShow) {
	}

	private class CMLoginBox extends LoginBox {

		protected CMLoginBox(AuthManager authManager,
				HistoryProvider historyProvider, EventBus eventBus) {
			super(authManager, historyProvider, eventBus);
			getElement().getStyle().setFloat(Float.RIGHT);
		}

		@Override
		protected void doLoggedinLogic() {
			eventBus.fireEvent(new PlaceRequestEvent(AppPlaceHierarchyProvider.LOGGEDIN));
		}
		
	}
	
}