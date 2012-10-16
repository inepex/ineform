package com.inepex.ineFrame.shared;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.dispatch.GenericActionResult;
import com.inepex.ineom.shared.dispatch.interfaces.CustomObjectDescResultInterface;

public class CustomObjectDescResult extends GenericActionResult implements CustomObjectDescResultInterface {
	
	private ObjectDesc od;
	private AssistedObject assistedObject;

	public CustomObjectDescResult() {
	}
	
	public CustomObjectDescResult(ObjectDesc od, AssistedObject assistedObject) {
		this.od=od;
		this.assistedObject=assistedObject;
	}

	@Override
	public ObjectDesc getOd() {
		return od;
	}
	
	@Override
	public void setOd(ObjectDesc od) {
		this.od = od;
	}
	
	@Override
	public AssistedObject getAssistedObject() {
		return assistedObject;
	}
	
	@Override
	public void setAssistedObject(AssistedObject assistedObject) {
		this.assistedObject = assistedObject;
	}
}
