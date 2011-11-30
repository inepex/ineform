package com.inepex.ineForm.shared.descriptorext;

import com.inepex.ineom.shared.descriptor.DescriptorBase;


public class FormRDescBase extends DescriptorBase {

	private static final long serialVersionUID = 1L;
	
	public FormRDescBase prop(String propkey, String value) {
		addProp(propkey , value);
		return this;
	}
}
