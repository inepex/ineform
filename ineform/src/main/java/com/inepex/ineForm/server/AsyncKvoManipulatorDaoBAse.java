package com.inepex.ineForm.server;

import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;
import com.inepex.ineom.shared.dispatch.interfaces.RelationListResult;

/**
 * when using commands, sync methods are unnecessary
 * 
 * @author SoTi
 *
 */
public abstract class AsyncKvoManipulatorDaoBAse implements KVManipulatorDaoBase {

    @Override
    public final ObjectManipulationResult manipulate(ObjectManipulation action) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public final ObjectListResult search(AbstractSearch action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final RelationListResult searchAsRelation(AbstractSearch action) {
        throw new UnsupportedOperationException();
    }

}
