package com.inepex.ineForm.shared.dispatch;

import com.inepex.ineom.shared.dispatch.interfaces.CustomObjectDescResultInterface;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;
import com.inepex.ineom.shared.dispatch.interfaces.RelationListResult;

public interface ManipulationObjectFactory {
	
	public CustomObjectDescResultInterface getNewObjectDescResult();

	public ObjectManipulationResult getNewObjectManipulationResult();

	public ObjectListResult getNewObjectListResult();

	public RelationListResult getNewRelationListResult();
}
