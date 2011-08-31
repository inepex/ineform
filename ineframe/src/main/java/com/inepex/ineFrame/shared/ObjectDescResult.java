package com.inepex.ineFrame.shared;

import net.customware.gwt.dispatch.shared.Result;

import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class ObjectDescResult implements Result {
	
	private ObjectDesc od;

	
	public ObjectDesc getOd() {
		return od;
	}
	
	public void setOd(ObjectDesc od) {
		this.od = od;
	}
}
