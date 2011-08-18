package com.inepex.ineFrame.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.guice.GuiceStandardDispatchServlet;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provider;
import com.inepex.ineFrame.shared.dispatch.Loggable;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.server.MultiLangDescStore;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.kvo.ExposedDescStore;

public abstract class AbstractGuiceDispatch extends GuiceStandardDispatchServlet
											implements UserHasRequiredRoleVerifier
													 , IneFrameInitializer{

	private static final long serialVersionUID = 1L;	
	private static final Logger logger = LoggerFactory
										 .getLogger(AbstractGuiceDispatch.class);
	
	
	private final I18nStore_Server serverI18n;
	private final Provider<CurrentLang> currentLangProvider;
	private final MultiLangDescStore multiLangDescStore;
	
	public abstract void doLogAction(Loggable loggable, HttpServletRequest request);
	
	public AbstractGuiceDispatch(Dispatch dispatch
							   , Provider<CurrentLang> currentLangProvider
							   , I18nStore_Server serverI18n
							   , DescriptorStore multiLangDescStore) {
		super(dispatch);
		this.currentLangProvider = currentLangProvider;
		this.serverI18n = serverI18n;
		this.multiLangDescStore = (MultiLangDescStore)multiLangDescStore;
	}
	
	@Override
	public Result execute(Action<?> action) throws DispatchException {
		logger.debug("Executing action {}", action);
		return super.execute(action);
	}
	
	@Override
	public void init() throws ServletException {
		setupDefaults();
		// TODO use DI here also!
		new LocalizationInitializer(serverI18n, this, currentLangProvider)
			.doInitialize();
		setupDescriptorStores();
		
		
		//TODO is this the right place for expose?
		ExposedDescStore.setExposedStore(multiLangDescStore);
		super.init();
	}


	private void setupDescriptorStores() {
		// TODO make this independent from number of languages
		ClientDescriptorStore engDescStore = new ClientDescriptorStore();
		currentLangProvider.get().setLangOverride("en");
		registerAssists(engDescStore);
		multiLangDescStore.addStore("en", engDescStore);
		
		ClientDescriptorStore hunDescStore = new ClientDescriptorStore();
		currentLangProvider.get().setLangOverride("hu");
		registerAssists(hunDescStore);
		multiLangDescStore.addStore("hu", hunDescStore);
		
		currentLangProvider.get().setLangOverride(null);
	}

}
