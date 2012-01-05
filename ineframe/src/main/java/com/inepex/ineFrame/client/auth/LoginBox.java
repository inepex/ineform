package com.inepex.ineFrame.client.auth;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SubmitButton;
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

	protected final VerticalPanel vp = new VerticalPanel();
	protected FormPanel formPanel;
	protected final VerticalPanel formContent = new VerticalPanel();
	protected TextBox userName;
	protected PasswordTextBox password;
	protected Label captchaLabel;
	protected CaptchaWidget captchaWidget;
	protected Button loginButton;

	protected final AuthManager authManager;
	protected final HistoryProvider historyProvider;
	protected final EventBus eventBus;
	protected final IneDispatch ineDispatch;
	
	protected LoginBox(AuthManager authManager, HistoryProvider historyProvider, EventBus eventBus, IneDispatch ineDispatch) {
		this.authManager = authManager;
		this.historyProvider=historyProvider;
		this.eventBus=eventBus;
		this.ineDispatch=ineDispatch;
	
		createUI();
		initWidget(vp);		
	}
	
	/**
	 * this method initializes the protected fields: labels and button 
	 */
	protected void createUI() {
		Element usernameEl = DOM.getElementById("username");
		Element passwordEl = DOM.getElementById("password");
		ButtonElement submitEl = (ButtonElement) Document.get().getElementById("loginSubmit");
		Element formEl = DOM.getElementById("loginform"); 
		
		userName = (usernameEl == null ? new TextBox() : TextBox.wrap(usernameEl));
		password = (passwordEl == null ? new PasswordTextBox() : PasswordTextBox.wrap(passwordEl));
		loginButton = (submitEl == null ? new SubmitButton() : Button.wrap(submitEl));
		loginButton.setText(IneFrameI18n.LOGIN());
		
		formContent.add(new Label(IneFrameI18n.USERNAME()));
		formContent.add(userName);
		formContent.add(new Label(IneFrameI18n.PASSWORD()));
		formContent.add(password);
		captchaLabel=new Label(IneFrameI18n.CAPTCHA());
		formContent.add(captchaLabel);
		captchaWidget=new CaptchaWidget();
		formContent.add(captchaWidget);
		formContent.add(loginButton);
		
		formPanel = (formEl == null ? new FormPanel() : FormPanel.wrap(formEl));
		formEl.getStyle().setDisplay(Display.BLOCK);
		formPanel.add(formContent);
		vp.add(formPanel);
	}
	

	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(formPanel.addSubmitHandler(new SubmitHandler() {
			
			@Override
			public void onSubmit(SubmitEvent event) {
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
	
	private void doLogin(){
		loginButton.setEnabled(false);
		loginButton.setText(IneFrameI18n.LOGGINGIN());
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
	
	/**
	 * override to display more sophisticated errors
	 * 
	 */
	protected void onInvalidLogin() {
		Window.alert("Invalid user or password (or captcha)!");
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
				
				Scheduler.get().scheduleFinally(new Scheduler.ScheduledCommand() {
					
					@Override
					public void execute() {
						userName.setValue("");
						password.setValue("");
						captchaWidget.reloadCaptcha();
						captchaLabel.setVisible(false);
						captchaWidget.setVisible(false);
						loginButton.setEnabled(true);
						loginButton.setText(IneFrameI18n.LOGIN());
					}
				});
				
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
				
				loginButton.setEnabled(true);
				loginButton.setText(IneFrameI18n.LOGIN());
				
				onInvalidLogin();
			}
		}
	}
}
