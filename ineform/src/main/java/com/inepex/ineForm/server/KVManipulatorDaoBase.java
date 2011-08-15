package com.inepex.ineForm.server;

import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineForm.shared.dispatch.ObjectListActionResult;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationActionResult;
import com.inepex.ineForm.shared.dispatch.RelationListActionResult;

/**
 * This is base KVO manipulator class in the server. Just generate it to an entry!
 *
 */
public abstract class KVManipulatorDaoBase {
	public abstract ObjectManipulationActionResult manipulate(ObjectManipulationAction action) throws Exception;
	public abstract ObjectListActionResult search(AbstractSearchAction action);
	public abstract RelationListActionResult searchAsRelation(AbstractSearchAction action);
}
