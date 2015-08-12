package com.inepex.ineom.shared.dispatch.interfaces;

import java.util.List;

import com.inepex.ineom.shared.assistedobject.AssistedObject;

public interface AbstractSearch {

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

    public void setPropGroups(String... propGroups);

    public List<String> getPropGroups();
}
