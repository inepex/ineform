package com.inepex.ineom.shared.dispatch.interfaces;

import java.util.List;

import com.inepex.ineom.shared.Relation;

public interface RelationListResult extends GenericResult {
    List<Relation> getList();

    void setList(List<Relation> list);

    Long getAllResultCount();

    void setAllResultCount(Long allResultCount);
}
