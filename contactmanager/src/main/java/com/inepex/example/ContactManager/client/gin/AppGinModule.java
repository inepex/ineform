package com.inepex.example.ContactManager.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHandler;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.ineForm.client.form.widgets.customkvo.ActionBasedOdFinder;
import com.inepex.ineForm.client.form.widgets.customkvo.OdFinder;
import com.inepex.ineForm.shared.dispatch.ActionBasedObjectFinder;
import com.inepex.ineForm.shared.dispatch.ObjectFinder;
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
import com.inepex.ineFrame.client.util.CETDateProviderCln;
import com.inepex.ineFrame.shared.util.DateProvider;

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
		
		bind(OdFinder.class).to(ActionBasedOdFinder.class);
		bind(ObjectFinder.class).to(ActionBasedObjectFinder.class);
		
		bind(DateProvider.class).to(CETDateProviderCln.class).in(Singleton.class);
	}

}
