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
import com.inepex.inei18n.shared.GetI18nModulesAction;
import com.inepex.inei18n.shared.GetI18nModulesResult;

/**
 * Base EntryPoint class for IneFrame or IneForm based projects. In IneForm it
 * is needed for I18n support. <br/>
 * 
 * @author istvanszoboszlai
 * 
 */
public abstract class IneFrameEntryPoint implements EntryPoint {

	protected I18nStore_Client clientI18nStore = new I18nStore_Client();

	protected abstract void registerAdditionalI18nModules();

	public abstract void onIneModuleLoad();

	protected final AuthManager authManager;

	protected final DispatchAsync dispatchAsync;
	protected final EventBus eventBus;

	private boolean i18nQueried = false;
	private boolean authStatusQueried = false;

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

		// query i18n
		IneDispatch dispatch = new IneDispatch(dispatchAsync, new InitialStatusIndicator(), eventBus);
		GetI18nModulesAction i18nAction = clientI18nStore.getModuleQueryAction();
		dispatch.execute(i18nAction, new I18nCallback(), new InitialStatusIndicator());

		// query auth status
		if (!(authManager instanceof NoAuthManager))
			authManager.checkAuthStatus(new AuthStatusCallback());
		else
			authStatusQueried = true;
	}

	private class I18nCallback extends SuccessCallback<GetI18nModulesResult> {
		@Override
		public void onSuccess(GetI18nModulesResult result) {
			clientI18nStore.onModulesQueriedSuccess(result);
			i18nQueried = true;
			proceedLoadIfAllDone();
		}
	}

	private class AuthStatusCallback implements AuthActionCallback {
		@Override
		public void onAuthCheckDone(AuthStatusResultBase result) {
			authStatusQueried = true;
			proceedLoadIfAllDone();
		}
	}

	private void proceedLoadIfAllDone() {
		if (i18nQueried && authStatusQueried)
			onIneModuleLoad();
	}

	class InitialStatusIndicator extends SimpleFailureStatusIndicator {
		@Override
		protected void onAnyFailure(String message) {
			Window.alert("IneFormEntryPoin:InitialStatusIndicator:"+message);
		}
	}

}
