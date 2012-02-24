package com.inepex.ineFrame.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.guice.GuiceStandardDispatchServlet;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

import com.google.inject.Provider;
import com.inepex.ineFrame.shared.dispatch.Loggable;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.server.MultiLangDescStore;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public abstract class AbstractGuiceDispatch extends GuiceStandardDispatchServlet
											implements UserHasRequiredRoleVerifier
													 , IneInitializer{

	private static final long serialVersionUID = 1L;	
	
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
		return super.execute(action);
	}
	
	@Override
	public void init() throws ServletException {
		setupDefaults();
		// TODO use DI here also!
		new LocalizationInitializer(serverI18n, this, currentLangProvider).doInitialize();
		setupDescriptorStores();
		
		super.init();
	}


	private void setupDescriptorStores() {
		
		for (String lang : serverI18n.getAllLangs()) {
			ClientDescriptorStore localizedDescStore = new ClientDescriptorStore();
			currentLangProvider.get().setLangOverride(lang);
			registerAssists(localizedDescStore);
			multiLangDescStore.addStore(lang, localizedDescStore);
		}
		
		currentLangProvider.get().setLangOverride(null);
	}

}
