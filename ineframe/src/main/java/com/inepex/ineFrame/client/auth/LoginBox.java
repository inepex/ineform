package com.inepex.ineFrame.client.auth;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.inepex.ineFrame.client.auth.AbstractAuthManager.AuthActionCallback;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.PlaceHandler;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;

public abstract class LoginBox extends HandlerAwareComposite {

	protected final VerticalPanel vp; 
	protected final TextBox userName;
	protected final PasswordTextBox password;
	protected final Button loginButton;

	protected final AuthManager authManager;
	protected final HistoryProvider historyProvider;
	protected final EventBus eventBus;
	
	protected LoginBox(AuthManager authManager, HistoryProvider historyProvider, EventBus eventBus) {
		this.authManager = authManager;
		this.historyProvider=historyProvider;
		this.eventBus=eventBus;
	
		vp= new VerticalPanel();
		
		//TODO get string from i18n
		vp.add(new Label("User:"));
		userName= new TextBox();
		vp.add(userName);
		vp.add(new Label("Password:"));
		password= new PasswordTextBox();
		vp.add(password);
		loginButton= new Button("Log in!");
		vp.add(loginButton);
		
		initWidget(vp);
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(userName.addKeyPressHandler(new PassEnterPressHandler()));
		registerHandler(password.addKeyPressHandler(new PassEnterPressHandler()));
		registerHandler(loginButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				doLogin();
			}
		}));
	}
	
	/**
	 * event.getCharCode() does not work
	 * If there is any problem with keypress, the KeyUp implementation bellow should be used
	 */
	class PassEnterPressHandler implements KeyPressHandler {
		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode()) {
				doLogin();
			}			
		}
	}
	
// If keyPres does not work, 
//	class PassKeyUpHandler implements KeyUpHandler {
//		@Override
//		public void onKeyUp(KeyUpEvent event) {
//			System.out.println((int)event.getNativeKeyCode());
//			if (KeyCodes.KEY_ENTER == event.getNativeKeyCode()) {
//				doLogin();
//			}			
//		}
//	}
	
	private void doLogin(){
		authManager.doLogin(userName.getText(), password.getText(),
				new LoginCallback());
	}
	
	protected abstract void doLoggedinLogic();

	class LoginCallback implements AuthActionCallback {
		@Override
		public void onAuthCheckDone() {
			if(authManager.isUserLoggedIn()) {
				if(historyProvider.getToken().contains((NavigationProperties.REDIRECT))) {
					PlaceRequestEvent pre = new PlaceRequestEvent();
					pre.setHierarchicalTokensWithParam(historyProvider.getToken().substring(
							historyProvider.getToken().indexOf(NavigationProperties.REDIRECT)
								+NavigationProperties.REDIRECT.length()
								+PlaceHandler.EQUALS_SIGN.length()
							, historyProvider.getToken().length()));
					eventBus.fireEvent(pre);
				} else {
					doLoggedinLogic();
				}
			} else {
				//TODO validate message
				Window.alert("Invalid user or password!");
			}
		}
	}
}