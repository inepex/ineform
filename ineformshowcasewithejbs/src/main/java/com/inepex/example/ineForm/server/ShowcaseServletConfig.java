package com.inepex.example.ineForm.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.inepex.core.module.guice.IneModuleGuiceModule;
import com.inepex.ineForm.server.guice.IneFormActionHanlderModule;
import com.inepex.ineForm.server.guice.IneFormDispatcherGuiceModule;
import com.inepex.ineForm.server.upload.UploadServletModule;
import com.inepex.ineFrame.server.di.guice.IneFrameBaseActionHanlderModule;
import com.inepex.ineFrame.server.di.guice.IneFrameBaseModule;
import com.inepex.ineFrame.server.di.guice.IneFrameBaseServletModule;

public class ShowcaseServletConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ServletModule() {
										protected void configureServlets() {
											install(new JpaPersistModule("IneFormShowCaseWithEjbs"));
											
											filter("/*").through(PersistFilter.class);
										};
									}
									, new IneFrameBaseServletModule("ineformshowcasewithejbs", ShowcaseDispatchServlet.class)
//									, new IneCoreBaseServletModule("ineformshowcasewithejbs", ShowcaseDispatchServlet.class)
									, new UploadServletModule() 
									, new TestServletModule()
									, new IneFrameBaseActionHanlderModule()
									, new IneFrameBaseModule()
									, new IneFormActionHanlderModule()
									, new IneFormDispatcherGuiceModule()
//									, new IneFrameModuleGuiceModule()
									, new IneModuleGuiceModule(false)
									);
	}
	
}
