package com.inepex.ineFrame.shared;

import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.dispatch.GenericResult;

public class ObjectDescResult extends GenericResult {
	
	private ObjectDesc od;

	public ObjectDescResult() {
	}
	
	public ObjectDescResult(ObjectDesc od) {
		this.od=od;
	}

	public ObjectDesc getOd() {
		return od;
	}
	
	public void setOd(ObjectDesc od) {
		this.od = od;
	}
}
