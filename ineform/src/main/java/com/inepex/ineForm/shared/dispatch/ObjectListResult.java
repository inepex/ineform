package com.inepex.ineForm.shared.dispatch;

import java.util.List;

import com.inepex.ineFrame.shared.dispatch.GenericResult;
import com.inepex.ineom.shared.kvo.AssistedObject;

/**
 * 
 * 
 * @author István Szoboszlai
 * 
 */
public class ObjectListResult extends GenericResult {

	private static final long serialVersionUID = 3223896182657888457L;

	private List<AssistedObject> list;
	private Long allResultCount;

	public ObjectListResult() {
		super();
	}

	public ObjectListResult(List<AssistedObject> requestedPage) {
		super();
		this.list = requestedPage;
		allResultCount = new Long(requestedPage.size());
	}

	public ObjectListResult(List<AssistedObject> firstPage, Long allResultCount) {
		this(firstPage);
		this.allResultCount = allResultCount;

	}

	public List<AssistedObject> getList() {
		return (List<AssistedObject>) list;
	}

	public void setList(List<AssistedObject> list) {
		this.list = list;
	}

	public Long getAllResultCount() {
		return allResultCount;
	}

	public void setAllResultCount(Long allResultCount) {
		this.allResultCount = allResultCount;
	}

}
