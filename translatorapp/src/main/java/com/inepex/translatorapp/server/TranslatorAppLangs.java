package com.inepex.translatorapp.server;

import java.util.Arrays;
import java.util.Collection;

import com.inepex.inei18n.server.ApplicationLangs;

public class TranslatorAppLangs implements ApplicationLangs{

	@Override
	public Collection<String> getLangs() {
		return Arrays.asList("en");
	}

}
