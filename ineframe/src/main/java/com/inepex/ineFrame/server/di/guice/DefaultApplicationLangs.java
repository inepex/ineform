package com.inepex.ineFrame.server.di.guice;

import java.util.Arrays;
import java.util.Collection;

import com.google.inject.Inject;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;
import com.inepex.inei18n.server.ApplicationLangs;
import com.inepex.inei18n.server.I18nModuleConverter;

public class DefaultApplicationLangs implements ApplicationLangs{

	private final Collection<String> langs;
	
	@Inject
	DefaultApplicationLangs() {
		langs = Arrays.asList(
				new I18nModuleConverter(IneFrameI18n.class).getModuleLanguages());
	}
	
	@Override
	public Collection<String> getLangs() {
		return langs;
	}

	
}
