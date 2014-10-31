package com.inepex.example.ContactManager.client.page;

import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.LoginBox;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.defaults.DefaultIneFrameMasterPage;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;


public class LoginPage extends FlowPanelBasedPage {
	
	@Inject
	LoginPage(AuthManager authManager,HistoryProvider historyProvider, EventBus eventBus, IneDispatch ineDispatch, final DefaultIneFrameMasterPage.View masterPage) {		
		
		
		final Button btn = new Button("show messagePanel");
		btn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				masterPage.toggleMessagePanel();
				btn.setText(masterPage.isMessagePanelShown() ? "hide messagePanel" : "show MessagePanel");
			}
		});
		
		mainPanel.add(new CMLoginBox(authManager, historyProvider, eventBus, ineDispatch));
		mainPanel.add(new HTML("<h2>To log in select one user:</h2>"));
		mainPanel.add(new HTML("<b>john.black@inepex.com</b><br /><i>pass123</i><br /><br />"));
		mainPanel.add(new HTML("<b>barbara.green@inepex.com</b><br /><i>pass123</i><br /><br />"));
		mainPanel.add(new HTML("<b>adam.white@inepex.com</b><br /><i>pass123</i><br /><br />"));
		mainPanel.add(btn);
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
			eventBus.fireEvent(new PlaceRequestEvent(AppPlaceHierarchyProvider.LOGGEDIN));
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
