package com.inepex.ineForm.shared.dispatch;

import java.util.List;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.dispatch.GenericResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;

/**
 * 
 * 
 * @author István Szoboszlai
 * 
 */
public class ObjectListActionResult extends GenericResult implements ObjectListResult{

	private static final long serialVersionUID = 3223896182657888457L;

	private List<AssistedObject> list;
	private Long allResultCount;

	public ObjectListActionResult() {
		super();
	}

	public ObjectListActionResult(List<AssistedObject> requestedPage) {
		super();
		this.list = requestedPage;
		allResultCount = new Long(requestedPage.size());
	}

	public ObjectListActionResult(List<AssistedObject> firstPage, Long allResultCount) {
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
