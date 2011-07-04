package com.inepex.ineFrame.client.navigation.defaults;

import com.google.inject.Provider;
import com.inepex.ineFrame.client.page.defaults.DummyPage;

public class DummyPageProvider implements Provider<DummyPage>{
	
	private String text;
	
	public DummyPageProvider(){}
	
	public DummyPageProvider(String text){
		this.text=text;
	}

	@Override
	public DummyPage get() {
		if(text==null)
			return new DummyPage();
		else
			return new DummyPage(text);
	}

}
