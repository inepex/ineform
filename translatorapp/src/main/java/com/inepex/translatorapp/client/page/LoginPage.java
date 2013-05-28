package com.inepex.translatorapp.client.page;

import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.LoginBox;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.PlaceToken;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.translatorapp.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.translatorapp.shared.TXT;


public class LoginPage extends FlowPanelBasedPage {

	@Inject
	LoginPage(AuthManager authManager,HistoryProvider historyProvider, EventBus eventBus, IneDispatch ineDispatch) {
		
		mainPanel.add(new CMLoginBox(authManager, historyProvider, eventBus, ineDispatch));
	}
	
	@Override
	protected void onShow(boolean isFirstShow) {
	}

	private class CMLoginBox extends LoginBox {

		// TODO: stay signed in functionality not fully implemented yet!
		private CheckBox staySignedIn = new CheckBox("Stay signed in");
		
		protected CMLoginBox(AuthManager authManager,
				HistoryProvider historyProvider, EventBus eventBus, IneDispatch ineDispatch) {
			super(authManager, historyProvider, eventBus, ineDispatch);
			getElement().getStyle().setFloat(Float.RIGHT);
		}

		@Override
		protected void doLoggedinLogic(AuthStatusResultBase base) {
			if(authManager.getLastAuthStatusResult().getRoles().contains(TXT.Roles.developer)
					|| authManager.getLastAuthStatusResult().getRoles().contains(TXT.Roles.translator))
				eventBus.fireEvent(new PlaceRequestEvent(AppPlaceHierarchyProvider.LOGGEDIN));
			else
				eventBus.fireEvent(new PlaceRequestEvent(
					new PlaceToken(AppPlaceHierarchyProvider.LOGGEDIN)
					.appendChild(AppPlaceHierarchyProvider.INACTIVE).toString()));
		}

		@Override
		protected HasValue<Boolean> getCheckBox() {
			return staySignedIn;
		}

		@Override
		protected IsWidget getCheckBoxAsWidget() {
			return staySignedIn;
		}
		
	}
	
}
