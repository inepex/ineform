package com.inepex.ineForm.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.inepex.ineForm.server.handler.ObjectListHandler;
import com.inepex.ineForm.server.handler.ObjectManipulationHandler;
import com.inepex.ineForm.server.handler.RelationListHandler;
import com.inepex.ineForm.server.handler.SetActionForExportServletHandler;
import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineForm.shared.dispatch.SetActionForExportServletAction;

public class IneFormActionHanlderModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		bindHandler(ObjectListAction.class, ObjectListHandler.class);
		bindHandler(ObjectManipulationAction.class, ObjectManipulationHandler.class);
		bindHandler(RelationListAction.class, RelationListHandler.class);
		bindHandler(SetActionForExportServletAction.class, SetActionForExportServletHandler.class);
	}
}
