package com.inepex.ineForm.shared.customkvo;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

@SuppressWarnings("serial")
public class CreatedFdesc extends FDesc implements Serializable, IsSerializable {
	
	public CreatedFdesc() {
		super();
	}

	public CreatedFdesc(String key, IneT type, String... validators) {
		super(key, type);
		if(validators!=null)
			addValidators(validators);
	}
}