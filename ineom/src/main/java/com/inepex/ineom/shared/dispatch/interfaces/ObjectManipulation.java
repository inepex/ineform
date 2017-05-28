package com.inepex.ineom.shared.dispatch.interfaces;

import java.util.List;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.dispatch.ManipulationTypes;

public interface ObjectManipulation {

    ManipulationTypes getManipulationType();

    void setManipulationType(ManipulationTypes manipulationType);

    AssistedObject getObject();

    void setObject(AssistedObject object);

    Long getIdToRefresh();

    void setIdToRefresh(Long idToRefresh);

    void setPropGroups(String... propGroups);

    List<String> getPropGroups();
}
