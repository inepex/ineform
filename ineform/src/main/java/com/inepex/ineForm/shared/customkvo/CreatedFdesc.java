package com.inepex.ineForm.shared.customkvo;

import java.io.Serializable;

import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.descriptor.FDesc;

@SuppressWarnings("serial")
public class CreatedFdesc extends FDesc implements Serializable {
	
	public CreatedFdesc() {
		super();
	}

	public CreatedFdesc(String key, IneT type, String... properties) {
		super(key, type, properties);
	}
}