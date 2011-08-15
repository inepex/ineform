package com.inepex.ineForm.server.handler;

import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.dispatch.ObjectListActionResult;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationActionResult;

public interface CustomActionHandler {
	ObjectManipulationActionResult doCustomAction(ObjectManipulationAction action) throws Exception;
	ObjectListActionResult doCustomAction(ObjectListAction action) throws Exception;
}
