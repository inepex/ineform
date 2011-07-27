package com.inepex.example.ContactManager.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHandler;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.DefaultAuthManager;
import com.inepex.ineFrame.client.navigation.MasterPage;
import com.inepex.ineFrame.client.navigation.PlaceHandler;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.defaults.DefaultIneFrameMasterPage;
import com.inepex.ineFrame.client.navigation.defaults.DefaultIneFrameMasterPageView;
import com.inepex.ineFrame.client.navigation.header.IneFrameHeader;
import com.inepex.ineFrame.client.navigation.header.IneFrameHeaderView;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer;
import com.inepex.ineFrame.client.navigation.menu.MenuRendererView;

public class AppGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(AuthManager.class).to(DefaultAuthManager.class).in(Singleton.class);
		
		//navigation & masterpage
		bind(PlaceHierarchyProvider.class).to(AppPlaceHierarchyProvider.class).in(Singleton.class);
		bind(PlaceHandler.class).to(AppPlaceHandler.class).in(Singleton.class);
		bind(MasterPage.class).to(DefaultIneFrameMasterPage.class).in(Singleton.class);
		bind(DefaultIneFrameMasterPage.View.class).to(DefaultIneFrameMasterPageView.class).in(Singleton.class);
		bind(MenuRenderer.View.class).to(MenuRendererView.class).in(Singleton.class);
		bind(IneFrameHeader.View.class).to(IneFrameHeaderView.class).in(Singleton.class);
		
	}

}
