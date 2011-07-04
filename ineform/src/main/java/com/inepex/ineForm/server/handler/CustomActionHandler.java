package com.inepex.ineForm.server.handler;

import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;
import com.inepex.ineom.shared.dispatch.ObjectListAction;
import com.inepex.ineom.shared.dispatch.ObjectListResult;

public interface CustomActionHandler {
	ObjectManipulationResult doCustomAction(ObjectManipulationAction action) throws Exception;
	ObjectListResult doCustomAction(ObjectListAction action) throws Exception;
}
