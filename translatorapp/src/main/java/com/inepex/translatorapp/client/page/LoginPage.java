package com.inepex.translatorapp.client.page;

import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.widget.LoginBox;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.UserLoggedInEvent;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.PlaceToken;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.translatorapp.shared.Consts;


public class LoginPage extends FlowPanelBasedPage {

	private final Anchor regLink;
	
	private final EventBus eventBus;
	
	@Inject
	LoginPage(AuthManager authManager,HistoryProvider historyProvider, EventBus eventBus, IneDispatch ineDispatch,
			DescriptorStore descStore, FormFactory formFactory) {
		this.eventBus=eventBus;
		
		FlowPanel leftPanel = new FlowPanel();
		leftPanel.getElement().getStyle().setPosition(Position.ABSOLUTE);
		
		HTML welcomeText = new HTML(translatorappI18n.welcomeText());
		welcomeText.setWidth("600px");
		leftPanel.add(welcomeText);
		
		regLink = new Anchor(translatorappI18n.regAnchor());
		leftPanel.add(regLink);
		
		mainPanel.add(leftPanel);
		
		mainPanel.add(new TranslatorLoginBox(authManager, historyProvider, eventBus, ineDispatch,
				descStore, formFactory));
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		
		registerHandler(regLink.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new PlaceRequestEvent(AppPlaceHierarchyProvider.REGISTER));
			}
		}));
	}
	

	@Override
	protected void onShow(boolean isFirstShow) {
	}
	
	
	private class TranslatorLoginBox extends LoginBox {

		// TODO: stay signed in functionality not fully implemented yet!
		private CheckBox staySignedIn = new CheckBox("Stay signed in");
		
		protected TranslatorLoginBox(AuthManager authManager, 
				   HistoryProvider historyProvider, 
				   EventBus eventBus,
				   IneDispatch ineDispatch,
				   DescriptorStore descriptorStore,
				   FormFactory formFactory) {
			super(authManager, historyProvider, eventBus, ineDispatch, descriptorStore, formFactory);
			getElement().getStyle().setFloat(Float.RIGHT);
		}

		@Override
		protected void doLoggedinLogic(AuthStatusResultBase base) {
			eventBus.fireEvent(new UserLoggedInEvent());
			if(authManager.getLastAuthStatusResult().getRoles().contains(Consts.Roles.developer)
					|| authManager.getLastAuthStatusResult().getRoles().contains(Consts.Roles.translator))
				eventBus.fireEvent(new PlaceRequestEvent(AppPlaceHierarchyProvider.LOGGEDIN));
			else
				eventBus.fireEvent(new PlaceRequestEvent(
					new PlaceToken(AppPlaceHierarchyProvider.LOGGEDIN)
					.appendChild(AppPlaceHierarchyProvider.INACTIVE).toString()));			
		}
		
		protected void doRedirectLogic(AuthStatusResultBase result) {
			eventBus.fireEvent(new UserLoggedInEvent());
			super.doRedirectLogic(result);
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
//
//	private class TranslatorLoginBox extends LoginBox {
//
//		// TODO: stay signed in functionality not fully implemented yet!
//		private CheckBox staySignedIn = new CheckBox("Stay signed in");
//		
//		protected TranslatorLoginBox(AuthManager authManager,
//				HistoryProvider historyProvider, EventBus eventBus, IneDispatch ineDispatch) {
//			super(authManager, historyProvider, eventBus, ineDispatch);
//			getElement().getStyle().setFloat(Float.RIGHT);
//		}
//
//		@Override
//		protected void doLoggedinLogic(AuthStatusResultBase base) {
//			if(authManager.getLastAuthStatusResult().getRoles().contains(Consts.Roles.developer)
//					|| authManager.getLastAuthStatusResult().getRoles().contains(Consts.Roles.translator))
//				eventBus.fireEvent(new PlaceRequestEvent(AppPlaceHierarchyProvider.LOGGEDIN));
//			else
//				eventBus.fireEvent(new PlaceRequestEvent(
//					new PlaceToken(AppPlaceHierarchyProvider.LOGGEDIN)
//					.appendChild(AppPlaceHierarchyProvider.INACTIVE).toString()));
//		}
//
//		@Override
//		protected HasValue<Boolean> getCheckBox() {
//			return staySignedIn;
//		}
//
//		@Override
//		protected IsWidget getCheckBoxAsWidget() {
//			return staySignedIn;
//		}
//		
//	}
}
