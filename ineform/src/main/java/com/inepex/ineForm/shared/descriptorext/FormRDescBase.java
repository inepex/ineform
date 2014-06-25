package com.inepex.ineForm.shared.descriptorext;

import com.inepex.ineom.shared.descriptor.DescriptorBase;


public abstract class FormRDescBase extends DescriptorBase {

	public static final String prop_showIDs = "prop_showIDs";
	private static final long serialVersionUID = 1L;
	
	public FormRDescBase prop(String propkey, String value) {
		addProp(propkey , value);
		return this;
	}
}
