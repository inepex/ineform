package com.inepex.ineom.shared.dispatch.interfaces;

import java.util.List;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.dispatch.ManipulationTypes;

public interface ObjectManipulation {

	public ManipulationTypes getManipulationType();

	public void setManipulationType(ManipulationTypes manipulationType);

	public AssistedObject getObject();

	public void setObject(AssistedObject object);

	public Long getIdToRefresh();
	
	public void setIdToRefresh(Long idToRefresh);
	
	public void setPropGroups(String... propGroups);
	
	public List<String> getPropGroups();
}
