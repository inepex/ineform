package com.inepex.ineFrame.client.navigation.defaults;

import com.google.inject.Provider;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.page.InePage;

public class SimpleCachingPlace extends InePlace {

	private final Provider<? extends InePage> provider;
	private InePage page;
	
	public SimpleCachingPlace(Provider<? extends InePage> provider) {
		this.provider=provider;
	}
	
	@Override
	public InePage getAssociatedPage() {
		if(page==null)
			page = provider.get();
		
		return page;
	}
}