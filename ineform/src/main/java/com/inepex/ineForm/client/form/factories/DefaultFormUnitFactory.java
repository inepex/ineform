package com.inepex.ineForm.client.form.factories;

import java.util.List;

import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.client.form.formunits.SimpleTableFormUnit;
import com.inepex.ineForm.client.form.formunits.UIBinderFormUnit;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDescBase;
import com.inepex.ineForm.shared.descriptorext.FormUnitRDesc;
import com.inepex.ineForm.shared.types.FormUnitT;
import com.inepex.ineom.shared.descriptor.Node;

public class DefaultFormUnitFactory implements FormUnitFactory {

	@Override
	public AbstractFormUnit createFormUnit(FormContext formCtx,
			FormRDesc formRDesc,
			FormUnitRDesc formUnitRDesc,
			String objectDescriptorsName,
			List<Node<FormRDescBase>> selectedFields) {
		
		if(formUnitRDesc==null || formUnitRDesc.getFormUnitType()==null || formUnitRDesc.getFormUnitType().equals(FormUnitT.SIMPLETABLEFORM)) {
			return new SimpleTableFormUnit(formCtx, formRDesc, objectDescriptorsName, selectedFields);
		} else if(formUnitRDesc.getFormUnitType().equals(FormUnitT.UIBINDERFORM)) { 
			return new UIBinderFormUnit(formCtx, objectDescriptorsName, selectedFields);
		}
		
		return null;
	}

}
