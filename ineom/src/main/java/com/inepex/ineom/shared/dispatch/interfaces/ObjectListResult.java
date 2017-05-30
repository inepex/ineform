package com.inepex.ineom.shared.dispatch.interfaces;

import java.util.List;

import com.inepex.ineom.shared.assistedobject.AssistedObject;

public interface ObjectListResult extends GenericResult {

    List<AssistedObject> getList();

    void setList(List<AssistedObject> list);

    Long getAllResultCount();

    void setAllResultCount(Long allResultCount);

    /**
     * may be null
     * 
     * @return
     */
    String getDescriptorName();

    void setDescriptorName(String descriptorName);

}
