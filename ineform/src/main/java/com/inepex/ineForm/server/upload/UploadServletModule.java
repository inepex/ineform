package com.inepex.ineForm.server.upload;

import com.google.inject.servlet.ServletModule;
import com.inepex.ineom.shared.kvo.IFConsts;

public class UploadServletModule extends ServletModule {
	
	@Override
	protected void configureServlets() {
		serve("/" + IFConsts.uploadServletUrl ).with(UploadServlet.class);
	}

}
