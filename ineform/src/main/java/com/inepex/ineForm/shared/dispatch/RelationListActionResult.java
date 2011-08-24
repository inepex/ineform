package com.inepex.ineForm.shared.dispatch;

import java.util.List;

import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.dispatch.GenericResult;
import com.inepex.ineom.shared.dispatch.interfaces.RelationListResult;

/**
 * 
 * 
 * @author István Szoboszlai
 * 
 */
public class RelationListActionResult extends GenericResult implements RelationListResult {

	private static final long serialVersionUID = 3223896182657888457L;

	private List<Relation> list;
	private Long allResultCount;

	public RelationListActionResult() {
		super();
	}

	public RelationListActionResult(List<Relation> requestedPage) {
		super();
		this.list = requestedPage;
	}

	public RelationListActionResult(List<Relation> firstPage, Long allResultCount) {
		this(firstPage);
		this.allResultCount = allResultCount;

	}

	public List<Relation> getList() {
		return list;
	}

	public void setList(List<Relation> list) {
		this.list = list;
	}

	public Long getAllResultCount() {
		return allResultCount;
	}

	public void setAllResultCount(Long allResultCount) {
		this.allResultCount = allResultCount;
	}

}
