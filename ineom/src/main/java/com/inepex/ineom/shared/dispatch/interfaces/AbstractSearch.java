package com.inepex.ineom.shared.dispatch.interfaces;

import com.inepex.ineom.shared.assistedobject.AssistedObject;

import java.util.List;

public interface AbstractSearch {

    AssistedObject getSearchParameters();

    Integer getFirstResult();

    Integer getNumMaxResult();

    void setFirstResult(Integer firstResult);

    void setNumMaxResult(Integer numMaxResult);

    String getOrderKey();

    void setOrderKey(String orderKey);

    Boolean isDescending();

    void setDescending(Boolean descending);

    Boolean isQueryResultCount();

    void setQueryResultCount(Boolean queryResultCount);

    String getDescriptorName();

    void setDescriptorName(String descriptorName);

    void setSearchParameters(AssistedObject searchParameters);

    void setPropGroups(String... propGroups);

    List<String> getPropGroups();
}
