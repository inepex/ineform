package com.inepex.ineForm.server.guice;

import com.google.inject.servlet.ServletModule;
import com.inepex.ineForm.server.csv.ExportServlet;

public class IneFormServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        serve("/export").with(ExportServlet.class);
    }

}
