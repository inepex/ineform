package com.inepex.ineForm.server;

import com.inepex.ineForm.shared.dispatch.AbstractSearchAction;
import com.inepex.ineForm.shared.dispatch.ObjectListResult;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;
import com.inepex.ineForm.shared.dispatch.RelationListResult;

/**
 * This is base KVO manipulator class in the server. Just generate it to an entry!
 *
 */
public abstract class KVManipulatorDaoBase {
	public abstract ObjectManipulationResult manipulate(ObjectManipulationAction action) throws Exception;
	public abstract ObjectListResult search(AbstractSearchAction action);
	public abstract RelationListResult searchAsRelation(AbstractSearchAction action);
}
