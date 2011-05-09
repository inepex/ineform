package com.inepex.ineForm.server.handler;

import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.dispatch.ObjectListResult;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;

public interface CustomActionHandler {
	ObjectManipulationResult doCustomAction(ObjectManipulationAction action) throws Exception;
	ObjectListResult doCustomAction(ObjectListAction action) throws Exception;
}
