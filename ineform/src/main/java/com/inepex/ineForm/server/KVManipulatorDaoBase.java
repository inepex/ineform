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
public interface KVManipulatorDaoBase {

	public abstract ObjectManipulationResult manipulate(ObjectManipulation action) throws Exception;
	public abstract ObjectListResult search(AbstractSearchAction action);
	public abstract RelationListResult searchAsRelation(AbstractSearchAction action);
	
	public abstract void manipulateAsync(ObjectManipulation action, 
			IneformAsyncCallback<ObjectManipulationResult> callback) throws Exception;
	public abstract void searchAsync(AbstractSearchAction action, IneformAsyncCallback<ObjectListResult> callback);
	public abstract void searchAsRelationAsync(AbstractSearchAction action, IneformAsyncCallback<RelationListResult> callback);
	
}
