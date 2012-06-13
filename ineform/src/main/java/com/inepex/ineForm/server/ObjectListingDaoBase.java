package com.inepex.ineForm.server;

import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;
import com.inepex.ineom.shared.dispatch.interfaces.RelationListResult;

public abstract class ObjectListingDaoBase implements KVManipulatorDaoBase {

	@Override
	public ObjectManipulationResult manipulate(ObjectManipulation action) throws Exception {
		throw new RuntimeException("not supported");
	}
	
	@Override
	public RelationListResult searchAsRelation(AbstractSearchAction action) {
		throw new RuntimeException("not supported");
	}
	
}
