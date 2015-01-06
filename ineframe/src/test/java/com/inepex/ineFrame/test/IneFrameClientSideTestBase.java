package com.inepex.ineFrame.test;

import java.util.List;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Provider;
import com.inepex.ineFrame.server.IneInitializer;
import com.inepex.ineFrame.server.MockCurrentLang;
import com.inepex.ineFrame.server.MockI18n;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.descriptorstore.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptorstore.TreeDescriptorStoreMapCreator;

public abstract class IneFrameClientSideTestBase implements IneInitializer {
	public static class MockCurrentLangProvider implements Provider<CurrentLang> {
		@Override
		public CurrentLang get() {
			return new MockCurrentLang();
		}
	}
	
	private final I18nStore_Server serverI18n;
	private final Provider<CurrentLang> currentLangProvider;
	private final ClientDescriptorStore descStore;
	
	public IneFrameClientSideTestBase() {
		GWTMockUtilities.disarm();
		
		serverI18n = new I18nStore_Server();
		currentLangProvider = new MockCurrentLangProvider();
		descStore = new ClientDescriptorStore(new TreeDescriptorStoreMapCreator());
		
		setupLocalization();
		
		registerAssists(descStore);
	}
	
	public ClientDescriptorStore getDescStore() {
		return descStore;
	}
	
	private void setupLocalization() {
		new TestLocalizationInitializer(serverI18n, this, currentLangProvider)
			.doInitialize();
	}
	
	public abstract List<Class<? extends ServerI18nProvider>> listUsedI18nClasses();
	
	@Override
	public final void registerAdditionalI18nModules(I18nStore_Server serverI18n,
			Provider<CurrentLang> currentLangProvider) {
		List<Class<? extends ServerI18nProvider>> modules = listUsedI18nClasses();
		if(modules!=null) {
			for(Class<? extends ServerI18nProvider> mod : modules) {
				serverI18n.registerModule(MockI18n.mock(mod));
			}
		}
		
	}
}
