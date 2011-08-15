package com.inepex.ineom.shared.dispatch.interfaces;

import com.inepex.ineom.shared.dispatch.ManipulationTypes;
import com.inepex.ineom.shared.kvo.AssistedObject;

public interface ObjectManipulation {

	public ManipulationTypes getManipulationType();

	public void setManipulationType(ManipulationTypes manipulationType);

	public AssistedObject getObject();

	public void setObject(AssistedObject object);

	public Long getIdToRefresh();
	
	public void setIdToRefresh(Long idToRefresh);
}
