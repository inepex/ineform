package com.inepex.ineom.shared.dispatch.interfaces;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

public interface CustomObjectDescResultInterface {

	void setOd(ObjectDesc od);
	
	ObjectDesc getOd();
	
	AssistedObject getAssistedObject();
	
	void setAssistedObject(AssistedObject assistedObject);
}
