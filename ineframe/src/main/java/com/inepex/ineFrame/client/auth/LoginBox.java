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
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase;
import com.inepex.ineFrame.client.auth.AbstractAuthManager.AuthActionCallback;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.PlaceHandler;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.widgets.CaptchaWidget;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.auth.CaptchaInfoAction;
import com.inepex.ineFrame.shared.auth.CaptchaInfoResult;

public abstract class LoginBox extends HandlerAwareComposite {

	protected final VerticalPanel vp; 
	protected final TextBox userName;
	protected final PasswordTextBox password;
	protected final Label captchaLabel;
	protected final CaptchaWidget captchaWidget;
	protected final Button loginButton;

	protected final AuthManager authManager;
	protected final HistoryProvider historyProvider;
	protected final EventBus eventBus;
	protected final IneDispatch ineDispatch;
	
	protected LoginBox(AuthManager authManager, HistoryProvider historyProvider, EventBus eventBus, IneDispatch ineDispatch) {
		this.authManager = authManager;
		this.historyProvider=historyProvider;
		this.eventBus=eventBus;
		this.ineDispatch=ineDispatch;
	
		vp= new VerticalPanel();
		vp.add(new Label(IneFrameI18n.USERNAME()));
		userName= new TextBox();
		vp.add(userName);
		vp.add(new Label(IneFrameI18n.PASSWORD()));
		password= new PasswordTextBox();
		vp.add(password);
		captchaLabel=new Label(IneFrameI18n.CAPTCHA());
		vp.add(captchaLabel);
		captchaWidget=new CaptchaWidget();
		vp.add(captchaWidget);
		loginButton= new Button(IneFrameI18n.LOGIN());
		vp.add(loginButton);
		
		initWidget(vp);		
	}
	

	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(userName.addKeyPressHandler(new PassEnterPressHandler()));
		registerHandler(password.addKeyPressHandler(new PassEnterPressHandler()));
		registerHandler(captchaWidget.addKeyPressHandler(new PassEnterPressHandler()));
		registerHandler(loginButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				doLogin();
			}
		}));
		
		setVisible(false);
		ineDispatch.execute(new CaptchaInfoAction(), new IneDispatchBase.SuccessCallback<CaptchaInfoResult>() {

			@Override
			public void onSuccess(CaptchaInfoResult result) {
				if(result.isNeedCaptcha()) {
					captchaLabel.setVisible(true);
					captchaWidget.setVisible(true);
				} else {
					captchaLabel.setVisible(false);
					captchaWidget.setVisible(false);
				}
				
				setVisible(true);
			}
		});
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
		authManager.doLogin(userName.getText(), password.getText(), captchaWidget.getCaptchaText(),
				new LoginCallback());
	}
	
	protected abstract void doLoggedinLogic(AuthStatusResultBase result);
	
	protected void doRedirectLogic(AuthStatusResultBase result) {
		PlaceRequestEvent pre = new PlaceRequestEvent();
		pre.setHierarchicalTokensWithParam(historyProvider.getToken().substring(
				historyProvider.getToken().indexOf(NavigationProperties.REDIRECT)
					+NavigationProperties.REDIRECT.length()
					+PlaceHandler.EQUALS_SIGN.length()
				, historyProvider.getToken().length()));
		modifiyRedirectPlaceRequestEvent(result, pre);
		eventBus.fireEvent(pre);
	}

	protected void modifiyRedirectPlaceRequestEvent(AuthStatusResultBase result, PlaceRequestEvent pre) {
	}

	class LoginCallback implements AuthActionCallback {
		@Override
		public void onAuthCheckDone(AuthStatusResultBase result) {
			if(result.isSuccess()) {
				if(historyProvider.getToken().contains((NavigationProperties.REDIRECT))) {
					doRedirectLogic(result);
				} else {
					doLoggedinLogic(result);
				}
				
				userName.setValue("");
				password.setValue("");
				captchaWidget.reloadCaptcha();
				captchaLabel.setVisible(false);
				captchaWidget.setVisible(false);
				
			} else {
				password.setValue("");
				captchaWidget.reloadCaptcha();
				
				if(result.isNeedCaptcha()) {
					captchaLabel.setVisible(true);
					captchaWidget.setVisible(true);
				} else {
					captchaLabel.setVisible(false);
					captchaWidget.setVisible(false);
				}
				
				//TODO validate message
				//TODO captcha case
				Window.alert("Invalid user or password (or captcha)!");
			}
		}
	}
}
