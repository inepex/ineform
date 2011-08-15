package com.inepex.ineom.shared.dispatch.interfaces;

import java.util.List;

import com.inepex.ineom.shared.kvo.Relation;

public interface RelationListResult extends GenericResult {
	public List<Relation> getList();

	public void setList(List<Relation> list);

	public Long getAllResultCount();

	public void setAllResultCount(Long allResultCount);
}
