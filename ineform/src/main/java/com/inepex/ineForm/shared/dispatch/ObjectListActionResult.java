package com.inepex.ineForm.shared.dispatch;

import java.util.List;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.dispatch.GenericActionResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;

/**
 * 
 * 
 * @author István Szoboszlai
 * 
 */
public class ObjectListActionResult extends GenericActionResult implements ObjectListResult{

	private static final long serialVersionUID = 3223896182657888457L;

	private List<AssistedObject> list;
	private Long allResultCount;
	private String descriptorName;	

	public ObjectListActionResult() {
		super();
	}

	public ObjectListActionResult(List<AssistedObject> requestedPage) {
		super();
		setList(requestedPage);
		allResultCount = new Long(requestedPage.size());
	}

	public ObjectListActionResult(List<AssistedObject> firstPage, Long allResultCount) {
		setList(firstPage);
		this.allResultCount = allResultCount;

	}
	@Override
	public List<AssistedObject> getList() {
		return list;
	}

	@Override
	public void setList(List<AssistedObject> list) {
		this.list = list;
		if (list.size() > 0){
			descriptorName = list.get(0).getDescriptorName();
		}
	}

	@Override
	public Long getAllResultCount() {
		return allResultCount;
	}

	@Override
	public void setAllResultCount(Long allResultCount) {
		this.allResultCount = allResultCount;
	}
	
	@Override
	public String getDescriptorName() {
		return descriptorName;
	}

	public void setDescriptorName(String descriptorName) {
		this.descriptorName = descriptorName;
	}	


}
