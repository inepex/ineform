package com.inepex.ineForm.client.form.factories;

import java.util.List;

import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.shared.descriptorext.FormRDescBase;
import com.inepex.ineForm.shared.descriptorext.FormUnitRDesc;
import com.inepex.ineom.shared.descriptor.Node;

public interface FormUnitFactory {
	
	public AbstractFormUnit createFormUnit(FormContext formCtx,
						FormUnitRDesc formDesc, 
						String objectDescriptorsName, 
						List<Node<FormRDescBase>> selectedFields);
}
