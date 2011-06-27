package com.inepex.ineFrame.client.navigation.defaults;

import com.google.inject.Provider;
import com.inepex.ineFrame.client.page.defaults.DummyPage;

public class DummyPageProvider implements Provider<DummyPage>{

	@Override
	public DummyPage get() {
		return new DummyPage();
	}

}
