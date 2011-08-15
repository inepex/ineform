package com.inepex.ineom.shared.dispatch.interfaces;

import com.inepex.ineom.shared.kvo.AssistedObject;

public interface AbstractSearchAction {

	public AssistedObject getSearchParameters();

	public Integer getFirstResult();

	public Integer getNumMaxResult();

	public void setFirstResult(Integer firstResult);

	public void setNumMaxResult(Integer numMaxResult);

	public String getOrderKey();

	public void setOrderKey(String orderKey);

	public Boolean isDescending();

	public void setDescending(Boolean descending);

	public Boolean isQueryResultCount();

	public void setQueryResultCount(Boolean queryResultCount);

	public String getDescriptorName();

	public void setDescriptorName(String descriptorName);

	public void setSearchParameters(AssistedObject searchParameters);
}
