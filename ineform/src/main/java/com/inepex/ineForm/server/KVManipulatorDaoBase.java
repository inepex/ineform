package com.inepex.ineForm.server;


import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;
import com.inepex.ineom.shared.dispatch.interfaces.RelationListResult;

/**
 * This is base KVO manipulator class in the server. Just generate it to an entry!
 *
 */
public abstract class KVManipulatorDaoBase {
	public abstract ObjectManipulationResult manipulate(ObjectManipulation action) throws Exception;
	public abstract ObjectListResult search(AbstractSearchAction action);
	public abstract RelationListResult searchAsRelation(AbstractSearchAction action);
}
