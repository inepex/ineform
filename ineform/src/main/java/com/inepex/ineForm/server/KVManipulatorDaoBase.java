package com.inepex.ineForm.server;


import com.inepex.ineForm.shared.IneformAsyncCallback;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;
import com.inepex.ineom.shared.dispatch.interfaces.RelationListResult;

/**
 * This is base KVO manipulator class in the server. Just generate it to an entry!
 *
 */
public interface KVManipulatorDaoBase {

	public abstract ObjectManipulationResult manipulate(ObjectManipulation action) throws Exception;
	public abstract ObjectListResult search(AbstractSearch action);
	public abstract RelationListResult searchAsRelation(AbstractSearch action);
	
	public abstract void manipulateAsync(ObjectManipulation action, 
			IneformAsyncCallback<ObjectManipulationResult> callback) throws Exception;
	public abstract void searchAsync(AbstractSearch action, IneformAsyncCallback<ObjectListResult> callback);
	public abstract void searchAsRelationAsync(AbstractSearch action, IneformAsyncCallback<RelationListResult> callback);
	
}
