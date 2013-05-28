package com.inepex.translatorapp.client.page;

import com.google.inject.Inject;
import com.inepex.ineFrame.client.page.defaults.DummyPage;
import com.inepex.translatorapp.client.i18n.translatorappI18n;

public class InactivePage extends DummyPage {
	
	@Inject
	public InactivePage() {
		super("<h1>" + translatorappI18n.inactiveAccount() + "</h1>");
	}
}
