package com.inepex.ineForm.client.form.factories;

import java.util.List;

import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.client.form.formunits.SimpleTableFormUnit;
import com.inepex.ineForm.client.form.formunits.UIBinderFormUnit;
import com.inepex.ineForm.shared.descriptorext.FormRDescBase;
import com.inepex.ineForm.shared.descriptorext.FormUnitRDesc;
import com.inepex.ineForm.shared.types.FormUnitT;
import com.inepex.ineom.shared.descriptor.Node;

public class DefaultFormUnitFactory implements FormUnitFactory {

	@Override
	public AbstractFormUnit createFormUnit(FormContext formCtx,
			FormUnitRDesc formDesc,
			String objectDescriptorsName,
			List<Node<FormRDescBase>> selectedFields) {
		
		if(formDesc==null || formDesc.getFormUnitType()==null || formDesc.getFormUnitType().equals(FormUnitT.SIMPLETABLEFORM)) {
			return new SimpleTableFormUnit(formCtx, objectDescriptorsName, selectedFields);
		} else if(formDesc.getFormUnitType().equals(FormUnitT.UIBINDERFORM)) { 
			return new UIBinderFormUnit(formCtx, objectDescriptorsName, selectedFields);
		}
		
		return null;
	}

}
