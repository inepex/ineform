package com.inepex.example.ContactManager.server;

import java.util.Arrays;
import java.util.Collection;

import com.inepex.inei18n.server.ApplicationLangs;

public class CMAppLangs implements ApplicationLangs{

	@Override
	public Collection<String> getLangs() {
		return Arrays.asList("en", "hu");
	}

}
