package com.inepex.ineForm.server.guice;

import com.google.inject.servlet.ServletModule;
import com.inepex.ineForm.server.csv.ExportServlet;
import com.inepex.ineom.shared.IFConsts;

public class IneFormServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        serve(IFConsts.exportServletUrl).with(ExportServlet.class);
    }

}
