package com.inepex.ineForm.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.google.inject.name.Names;
import com.inepex.ineForm.server.handler.ObjectListHandler;
import com.inepex.ineForm.server.handler.ObjectManipulationHandler;
import com.inepex.ineForm.server.handler.RelationListHandler;
import com.inepex.ineForm.server.handler.SetActionForExportServletHandler;
import com.inepex.ineForm.shared.dispatch.ActionObjectFactory;
import com.inepex.ineForm.shared.dispatch.ManipulationObjectFactory;
import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineForm.shared.dispatch.SetActionForExportServletAction;
import com.inepex.ineom.shared.IFConsts;

public class IneFormActionHanlderModule extends ActionHandlerModule {

    private String mongoUrl = "localhost";
    private String mongoUser = "";
    private String mongoPass = "";

    public IneFormActionHanlderModule() {}

    public IneFormActionHanlderModule setMongoUrl(String mongoUrl) {
        this.mongoUrl = mongoUrl;
        return this;
    }

    public IneFormActionHanlderModule setMongoUser(String mongoUser) {
        this.mongoUser = mongoUser;
        return this;
    }

    public IneFormActionHanlderModule setMongoPass(String mongoPass) {
        this.mongoPass = mongoPass;
        return this;
    }

    @Override
    protected void configureHandlers() {
        bind(String.class).annotatedWith(Names.named(IFConsts.prop_mongoUrl)).toInstance(mongoUrl);
        bind(String.class)
            .annotatedWith(Names.named(IFConsts.prop_mongoUser))
            .toInstance(mongoUser);
        bind(String.class)
            .annotatedWith(Names.named(IFConsts.prop_mongoPass))
            .toInstance(mongoPass);
        bindHandler(ObjectListAction.class, ObjectListHandler.class);
        bindHandler(ObjectManipulationAction.class, ObjectManipulationHandler.class);
        bindHandler(RelationListAction.class, RelationListHandler.class);
        bindHandler(SetActionForExportServletAction.class, SetActionForExportServletHandler.class);
        bind(ManipulationObjectFactory.class).to(ActionObjectFactory.class);
    }
}
