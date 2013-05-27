package com.inepex.translatorapp.client.navigation;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.navigation.DefaultPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.places.SimpleCachingPlace;
import com.inepex.translatorapp.client.page.DummyPage;

@Singleton
public class AppPlaceHierarchyProvider extends DefaultPlaceHierarchyProvider {

	//loggedin
	@Inject Provider<DummyPage> dummyPageProvider;
	
	
	@Override
	public void createPlaceHierarchy() {
		placeRoot.addChild(NavigationProperties.defaultPlace, new SimpleCachingPlace(dummyPageProvider))
				 ;
	}
	
	@Override
	public List<String> getCurrentMenuRoot() {
		return null;
	}
}

