package com.inepex.ineForm.shared.dispatch;

import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;
import com.inepex.ineom.shared.dispatch.interfaces.RelationListResult;

public class ActionObjectFactory implements ManipulationObjectFactory {

	@Override
	public ObjectManipulationResult getNewObjectManipulationResult() {
		return new ObjectManipulationActionResult();
	}

	@Override
	public ObjectListResult getNewObjectListResult() {
		return new ObjectListActionResult();
	}

	@Override
	public RelationListResult getNewRelationListResult() {
		return new RelationListActionResult();
	}
}
