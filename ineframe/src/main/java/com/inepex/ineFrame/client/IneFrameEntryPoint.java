package com.inepex.ineFrame.client;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineFrame.client.async.SimpleFailureStatusIndicator;
import com.inepex.ineFrame.client.auth.AbstractAuthManager.AuthActionCallback;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.inei18n.client.I18nStore_Client;
import com.inepex.inei18n.shared.ClientI18nProvider;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieAction;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieResult;

/**
 * Base EntryPoint class for IneFrame or IneForm based projects. In IneForm it
 * is needed for I18n support. <br/>
 * 
 * @author istvanszoboszlai
 * 
 */
public abstract class IneFrameEntryPoint implements EntryPoint {

	protected abstract void registerAdditionalI18nModules();

	public abstract void onIneModuleLoad();

	protected final AuthManager authManager;

	protected final DispatchAsync dispatchAsync;
	protected final EventBus eventBus;
	protected final I18nStore_Client clientI18nStore = new I18nStore_Client();

	public IneFrameEntryPoint(DispatchAsync dispatchAsync, EventBus eventBus, AuthManager authManager) {
		this.dispatchAsync = dispatchAsync;
		this.eventBus = eventBus;
		this.authManager=authManager;
	}

	@Override
	public void onModuleLoad() {
		if (dispatchAsync == null)
			throw new RuntimeException("dispatchAsync is not set! Set it in the contsructor of the derived class," + " or use GIN!");
		clientI18nStore.registerModule(new IneFrameI18n(new ClientI18nProvider<IneFrameI18n>()));
		registerAdditionalI18nModules();

		// query auth status
		if (!(authManager instanceof NoAuthManager))
			authManager.checkAuthStatus(new AuthStatusCallback());
		else {
			queryI18nAndInvokeOnIneModuleLoad(true);
		}
	}
	
	private void queryI18nAndInvokeOnIneModuleLoad(boolean loadLangFromCookie) {
		// query i18n
		IneDispatch dispatch = new IneDispatch(dispatchAsync, new InitialStatusIndicator(), eventBus);
		GetI18nModulesAndSetCurrentLangFromCookieAction i18nAction = clientI18nStore.getModuleQueryAction(loadLangFromCookie);
		dispatch.execute(i18nAction, new I18nCallback(), new InitialStatusIndicator());
	}

	private class I18nCallback extends SuccessCallback<GetI18nModulesAndSetCurrentLangFromCookieResult> {
		@Override
		public void onSuccess(GetI18nModulesAndSetCurrentLangFromCookieResult result) {
			clientI18nStore.onModulesQueriedSuccess(result);
			
			onIneModuleLoad();
		}
	}

	private class AuthStatusCallback implements AuthActionCallback {
		@Override
		public void onAuthCheckDone(AuthStatusResultBase result) {
			//the loggedin user's language management is not from cookie
			queryI18nAndInvokeOnIneModuleLoad(result==null || !result.isSuccess());
		}
	}

	class InitialStatusIndicator extends SimpleFailureStatusIndicator {
		@Override
		protected void onAnyFailure(String message) {
			Window.alert("IneFormEntryPoin:InitialStatusIndicator:"+message);
		}
	}

}
