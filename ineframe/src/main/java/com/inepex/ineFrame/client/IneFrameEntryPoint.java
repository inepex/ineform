package com.inepex.ineFrame.client;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.EntryPoint;
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
import com.inepex.ineFrame.shared.GetDescStore;
import com.inepex.ineFrame.shared.GetDescStoreResult;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.util.DateHelper;
import com.inepex.inei18n.client.I18nStore_Client;
import com.inepex.inei18n.shared.ClientI18nProvider;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieAction;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieResult;
import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.TypedDescriptorMap;

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
	
	protected final DescriptorStore descStore;

	private final QueryCounter queryCounter = new QueryCounter();
	
	public IneFrameEntryPoint(DispatchAsync dispatchAsync, EventBus eventBus, AuthManager authManager, 
			DescriptorStore descStore) {
		this.dispatchAsync = dispatchAsync;
		this.eventBus = eventBus;
		this.authManager = authManager;
		this.descStore = descStore;
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
		
		//query descriptor store
		queryDescriptorStore();
		
		Window.addResizeHandler(new ResizeHandler() {
			
			@Override
			public void onResize(ResizeEvent event) {
				eventBus.fireEvent(new WindowResizeEvent(event.getWidth(), event.getHeight()));
			}
		});
	}
	
	private void queryDescriptorStore() {
		queryCounter.incQueries();
		
		//query server side descriptor store
		IneDispatch dispatch = new IneDispatch(dispatchAsync, new InitialStatusIndicator(), eventBus,
				new DefaultFailedHandler());
		GetDescStore getDescriptorStore = new GetDescStore();
		dispatch.execute(getDescriptorStore, new GetDescriptorStoreCallback());
	}

	private void queryI18nAndInvokeOnIneModuleLoad(boolean loadLangFromCookie) {
		queryCounter.incQueries();
		
		// query i18n
		IneDispatch dispatch = new IneDispatch(dispatchAsync, new InitialStatusIndicator(), eventBus,
				new DefaultFailedHandler());
		GetI18nModulesAndSetCurrentLangFromCookieAction i18nAction = clientI18nStore.getModuleQueryAction(loadLangFromCookie);
		dispatch.execute(i18nAction, new I18nCallback(), new InitialStatusIndicator());
	}
	
	private class GetDescriptorStoreCallback extends SuccessCallback<GetDescStoreResult>{
		@Override
		public void onSuccess(GetDescStoreResult result) {
			registerDescriptors(result.getObjectDescs()
							  , result.getAllTypedDescMap());
			
			queryCounter.decQueries();
			
			if(queryCounter.isAllQueriesResponseArrived())
				onIneModuleLoad();
		}
	}

	private class I18nCallback extends SuccessCallback<GetI18nModulesAndSetCurrentLangFromCookieResult> {
		@Override
		public void onSuccess(GetI18nModulesAndSetCurrentLangFromCookieResult result) {
			clientI18nStore.onModulesQueriedSuccess(result);
			Cookies.setCookie(I18nStore_Client.LANG_COOKIE_ID, result.currentLang, DateHelper.addDaysSafe(new Date(), 50));
			
			queryCounter.decQueries();
			
			if(queryCounter.isAllQueriesResponseArrived())
				onIneModuleLoad();
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

	class InitialStatusIndicator extends SimpleFailureStatusIndicator {
		@Override
		protected void onAnyFailure(String message) {
			Window.alert("IneFormEntryPoin:InitialStatusIndicator:"+message);
		}
	}

	private void registerDescriptors(
			Map<String, ObjectDesc> objectDescs,
			Map<String, TypedDescriptorMap<? extends DescriptorBase>> allTypedDescMap) {
		registerObjectDescs(objectDescs);
		registerTypedDescs(allTypedDescMap);
	}
	
	private void registerTypedDescs(Map<String, TypedDescriptorMap<? extends DescriptorBase>> allTypedDescMap) {
		
		Iterator<String> allTypedDescIt = allTypedDescMap.keySet().iterator();
		
		while(allTypedDescIt.hasNext()){
			TypedDescriptorMap<? extends DescriptorBase> typedDesc = allTypedDescMap.get( allTypedDescIt.next() );
			registerDefaultDescs(typedDesc.getDefaultDescriptors());
			registerNamedDescs(typedDesc.getNamedDescriptors());
		}
		
	}

	private void registerObjectDescs(Map<String, ObjectDesc> objectDescs){
		Iterator<String> odIt = objectDescs.keySet().iterator();
		while(odIt.hasNext())
			descStore.registerObjectDesc(objectDescs.get(odIt.next()));
	}
	
	private <D extends DescriptorBase> void registerDefaultDescs(Map<String, D> descs){
		Iterator<String> descIt = descs.keySet().iterator();
		while(descIt.hasNext()){
			String descName = descIt.next();
			D desc = descs.get(descName);
			descStore.addDefaultTypedDesc(descName, desc);
		}
	}
	
	private <D extends DescriptorBase> void registerNamedDescs(Map<String, Map<String, D>> namedDescs){
		Iterator<String> nDescIt = namedDescs.keySet().iterator();
		while(nDescIt.hasNext()){
			String objDescName = nDescIt.next();
			Map<String, D> nRDescs = namedDescs.get(objDescName);
			
			Iterator<String> nRDescsIt = nRDescs.keySet().iterator();
			
			while(nRDescsIt.hasNext()){
				String namedDescName = nRDescsIt.next();
				D namedDesc = nRDescs.get(namedDescName);
				
				descStore.addNamedTypedDesc(objDescName, namedDescName, namedDesc);
			}
			
		}
	}
	
	private class QueryCounter{
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
}
