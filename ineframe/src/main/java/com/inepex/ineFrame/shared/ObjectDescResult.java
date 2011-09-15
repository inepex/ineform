package com.inepex.ineFrame.shared;

import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.dispatch.GenericResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectDescResultInterface;

public class ObjectDescResult extends GenericResult implements ObjectDescResultInterface {
	
	private ObjectDesc od;

	public ObjectDescResult() {
	}
	
	public ObjectDescResult(ObjectDesc od) {
		this.od=od;
	}

	@Override
	public ObjectDesc getOd() {
		return od;
	}
	
	@Override
	public void setOd(ObjectDesc od) {
		this.od = od;
	}
}
