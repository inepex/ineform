package com.inepex.ineFrame.server;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provider;
import com.inepex.ineFrame.test.IneFrameClientSideTestBase.MockCurrentLangProvider;
import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.I18nModule;

public class MockI18n {

	private static final Logger _logger = LoggerFactory.getLogger(MockI18n.class);
	
	public static <T extends I18nModule, K extends ServerI18nProvider<T>> T mock(Class<K> serverClazz){
		try {
		Constructor<K> constructor = serverClazz.getConstructor(Provider.class);
		ServerI18nProvider<T> i18nProvider = constructor.newInstance(new MockCurrentLangProvider());
		return i18nProvider.getVirgineI18nModule();
		} catch (Exception e){
			_logger.error("Exception", e);
			return null;
		}
	}
}
