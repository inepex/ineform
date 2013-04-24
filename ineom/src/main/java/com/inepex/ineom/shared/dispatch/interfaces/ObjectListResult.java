package com.inepex.ineom.shared.dispatch.interfaces;

import java.util.List;

import com.inepex.ineom.shared.assistedobject.AssistedObject;

public interface ObjectListResult extends GenericResult {

	public List<AssistedObject> getList();

	public void setList(List<AssistedObject> list);

	public Long getAllResultCount();

	public void setAllResultCount(Long allResultCount);
	
	/**
	 * may be null
	 * @return
	 */
	public String getDescriptorName();	

	public void setDescriptorName(String descriptorName);

}
