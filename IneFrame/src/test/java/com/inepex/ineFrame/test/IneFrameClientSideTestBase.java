package com.inepex.ineFrame.test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.inepex.ineFrame.server.IneFrameInitializer;
import com.inepex.ineFrame.server.LocalizationInitializer;
import com.inepex.ineFrame.server.MockCurrentLang;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;

public abstract class IneFrameClientSideTestBase implements IneFrameInitializer {
	public class MockCurrentLangProvider implements Provider<CurrentLang> {
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
		descStore = new ClientDescriptorStore();
		
		setupLocalization();
		
		registerAssists(descStore);
	}
	
	private void setupLocalization() {
		new LocalizationInitializer(serverI18n, this, currentLangProvider)
			.doInitialize();
	}
	
}
