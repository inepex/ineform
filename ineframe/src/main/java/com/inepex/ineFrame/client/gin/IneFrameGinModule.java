package com.inepex.ineFrame.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
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

public class IneFrameGinModule extends AbstractGinModule {
	
	private Class<? extends PlaceHierarchyProvider> placeHierarchyProvider;
	private Class<? extends PlaceHandler> placeHandler;
	private Class<? extends AuthManager> authManager = DefaultAuthManager.class;
	private Class<? extends MasterPage> masterPage = DefaultIneFrameMasterPage.class;
	private Class<? extends MasterPage.View> masterPageView = DefaultIneFrameMasterPageView.class;
	private Class<? extends MenuRenderer.View> menuRendererView = MenuRendererView.class;
	private Class<? extends IneFrameHeader.View> ineFrameHeaderView = IneFrameHeaderView.class;
	
	public IneFrameGinModule(
			Class<? extends PlaceHierarchyProvider> placeHierarchyProvider,
			Class<? extends PlaceHandler> placeHandler) {
		super();
		this.placeHierarchyProvider = placeHierarchyProvider;
		this.placeHandler = placeHandler;
	}

	@Override
	protected void configure() {
		bind(PlaceHierarchyProvider.class).to(placeHierarchyProvider).in(Singleton.class);
		bind(PlaceHandler.class).to(placeHandler).in(Singleton.class);
		bind(MasterPage.class).to(masterPage).in(Singleton.class);
		bind(MasterPage.View.class).to(masterPageView).in(Singleton.class);
		bind(MenuRenderer.View.class).to(menuRendererView).in(Singleton.class);
		bind(IneFrameHeader.View.class).to(ineFrameHeaderView).in(Singleton.class);		
		bind(AuthManager.class).to(authManager).in(Singleton.class);
	}

	public IneFrameGinModule setAuthManager(Class<? extends AuthManager> authManager) {
		this.authManager = authManager;
		return this;
	}

	public IneFrameGinModule setMasterPage(Class<? extends MasterPage> masterPage) {
		this.masterPage = masterPage;
		return this;
	}

	public IneFrameGinModule setMasterPageView(Class<? extends MasterPage.View> masterPageView) {
		this.masterPageView = masterPageView;
		return this;
	}

	public IneFrameGinModule setMenuRendererView(Class<? extends MenuRenderer.View> menuRendererView) {
		this.menuRendererView = menuRendererView;
		return this;
	}

	public IneFrameGinModule setIneFrameHeaderView(Class<? extends IneFrameHeader.View> ineFrameHeaderView) {
		this.ineFrameHeaderView = ineFrameHeaderView;
		return this;
	}
	
	
	
	
	
}