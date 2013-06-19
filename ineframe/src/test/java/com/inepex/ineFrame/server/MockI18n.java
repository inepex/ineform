package com.inepex.ineFrame.server;

import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public class MockI18n {

	private static final Logger _logger = LoggerFactory.getLogger(MockI18n.class);
	
	public static <T extends I18nModule> void mock(Class<T> clazz){
		try {
		ServerI18nProvider<T> i18nProvider = Mockito.mock(ServerI18nProvider.class);
		clazz.getConstructor(I18nModuleProvider.class).newInstance(i18nProvider);
		Mockito.when(i18nProvider.get()).thenReturn(clazz.newInstance());
		} catch (Exception e){
			_logger.error("Exception", e);
		}
	}
}
