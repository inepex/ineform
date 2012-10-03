package com.inepex.ineForm.server;



import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;
import com.inepex.ineom.shared.dispatch.interfaces.RelationListResult;

/**
 * when using commands, sync methods are unnecessary
 * @author SoTi
 *
 */
public abstract class AsyncKvManipulatorDaoBAse implements KVManipulatorDaoBase {

	@Override
	public final ObjectManipulationResult manipulate(ObjectManipulation action) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public final ObjectListResult search(AbstractSearchAction action) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final RelationListResult searchAsRelation(AbstractSearchAction action) {
		throw new UnsupportedOperationException();
	}

}
