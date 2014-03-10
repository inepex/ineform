package com.inepex.ineFrame.client;

import java.util.Date;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.inepex.ineFrame.client.async.DefaultFailedHandler;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineFrame.client.async.SimpleFailureStatusIndicator;
import com.inepex.ineFrame.client.auth.AbstractAuthManager.AuthActionCallback;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;
import com.inepex.ineFrame.client.misc.WindowResizeEvent;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.navigation.PlaceHandlerHelper;
import com.inepex.ineFrame.shared.GetDescStore;
import com.inepex.ineFrame.shared.GetDescStoreResult;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.util.date.DateHelper;
import com.inepex.inei18n.client.I18nStore_Client;
import com.inepex.inei18n.shared.ClientI18nProvider;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieAction;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieResult;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore.Marker;

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
	protected final I18nStore_Client clientI18nStore;
	
	protected final DescriptorStore descStore;

	protected final QueryCounter queryCounter = new QueryCounter();
	
	protected final HistoryProvider historyProvider;
	
	private IneDispatch dispatch;
	
	public IneFrameEntryPoint(DispatchAsync dispatchAsync, EventBus eventBus, AuthManager authManager, 
			DescriptorStore descStore, HistoryProvider historyProvider, I18nStore_Client clientI18nStore) {
		this.dispatchAsync = dispatchAsync;
		this.eventBus = eventBus;
		this.clientI18nStore=clientI18nStore;
		this.authManager = authManager;
		this.descStore = descStore;
		this.historyProvider = historyProvider;
	}

	@Override
	public void onModuleLoad() {
		if (dispatchAsync == null)
			throw new RuntimeException("dispatchAsync is not set! Set it in the contsructor of the derived class," + " or use GIN!");
		clientI18nStore.registerModule(new IneFrameI18n(new ClientI18nProvider<IneFrameI18n>()));
		registerAdditionalI18nModules();

		// query auth status
		if (!(authManager instanceof NoAuthManager)){
			queryCounter.incQueries();
			authManager.checkAuthStatus(new AuthStatusCallback());
		} else {
			queryI18nAndInvokeOnIneModuleLoad(true);
		}
		
		Window.addResizeHandler(new ResizeHandler() {
			
			@Override
			public void onResize(ResizeEvent event) {
				eventBus.fireEvent(new WindowResizeEvent(event.getWidth(), event.getHeight()));
			}
		});
	}
	
	private void printLoadTimeAndCallModuleLoad(){
		if (!GWT.isProdMode())
		{
			long appLoadTimeMillis = System.currentTimeMillis() - new Double(getNavigationStartTime()).longValue();		
			System.out.println("App loaded in " + appLoadTimeMillis + " ms");
		}
		onIneModuleLoad();
	}
	
	private void queryDescriptorStore() {
		queryCounter.incQueries();
		
		//query server side descriptor store
		GetDescStore getDescriptorStore = new GetDescStore();
		getIneDispatch().execute(getDescriptorStore, new GetDescriptorStoreCallback());
	}

	protected void queryI18nAndInvokeOnIneModuleLoad(boolean loadLangFromCookie) {
		queryCounter.incQueries();
		
		// query i18n		
		String langFromUrl = PlaceHandlerHelper.getUrlParameters(historyProvider.getToken()).get(IFConsts.LANG);
		GetI18nModulesAndSetCurrentLangFromCookieAction i18nAction = clientI18nStore.getModuleQueryAction(loadLangFromCookie, langFromUrl);
		getIneDispatch().execute(i18nAction, new I18nCallback(), new InitialStatusIndicator());
	}
	
	protected IneDispatch getIneDispatch(){
		if (dispatch == null)
			dispatch = new IneDispatch(dispatchAsync, new InitialStatusIndicator(), eventBus,
				new DefaultFailedHandler());
		return dispatch;
	}
	
	protected class GetDescriptorStoreCallback extends SuccessCallback<GetDescStoreResult>{
		@Override
		public void onSuccess(GetDescStoreResult result) {
			registerDescriptors(Marker.precached, result);
			
			queryCounter.decQueries();
			
			if(queryCounter.isAllQueriesResponseArrived())
				printLoadTimeAndCallModuleLoad();
		}
	}

	protected class I18nCallback extends SuccessCallback<GetI18nModulesAndSetCurrentLangFromCookieResult> {
		
		public I18nCallback() {
		}
		
		@Override
		public void onSuccess(GetI18nModulesAndSetCurrentLangFromCookieResult result) {
			clientI18nStore.onModulesQueriedSuccess(result);
			Cookies.setCookie(I18nStore_Client.LANG_COOKIE_ID, result.getCurrentLang(), new Date(System.currentTimeMillis()+DateHelper.dayInMs*50));
			
			queryCounter.decQueries();
			
			//query descriptor store
			queryDescriptorStore();
			
			if(queryCounter.isAllQueriesResponseArrived())
				printLoadTimeAndCallModuleLoad();
		}
	}

	private class AuthStatusCallback implements AuthActionCallback {
		@Override
		public void onAuthCheckDone(AuthStatusResultBase result) {
			//the loggedin user's language management is not from cookie
			queryI18nAndInvokeOnIneModuleLoad(result==null || !result.isSuccess());
			
			queryCounter.decQueries();
			
		}
	}

	protected class InitialStatusIndicator extends SimpleFailureStatusIndicator {
		
		public InitialStatusIndicator() {
		}
		
		@Override
		protected void onAnyFailure(String message) {
			Window.alert("IneFormEntryPoint:InitialStatusIndicator:"+message);
		}
	}

	private void registerDescriptors(
			Marker marker, GetDescStoreResult result) {
		if(result.getObjectDescs()!=null) {
			for(ObjectDesc od : result.getObjectDescs())
				descStore.registerObjectDesc(marker, od);
			
		}
		
		if(result.getNames().size()>0) {
			for(int i=0; i<result.getNames().size(); i++) {
				descStore.addNamedTypedDesc(marker, result.getOdNames().get(i), result.getNames().get(i),
						result.getTypedDescrptors().get(i));
			}
		}
	}
	
	protected class QueryCounter{
		private int queryCounter = 0;
		
		public QueryCounter() {
		}
		
		public void incQueries(){
			queryCounter++;
		}
		
		public void decQueries(){
			queryCounter--;
		}
		
		public boolean isAllQueriesResponseArrived(){
			if(queryCounter == 0)
				return true;
			else
				return false;
		}
	} 
	
	public static native double getNavigationStartTime() /*-{
	  return $wnd.performance.timing.navigationStart;
	}-*/;
	
	public static native void setAnalyticsAccount(String accountID) /*-{
	$wnd._gaq.push([ '_setAccount', accountID ]);
	}-*/;
	
	public static native void trackPageview() /*-{
	$wnd._gaq.push([ '_trackPageview' ]);
	}-*/;
}
