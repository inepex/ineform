package com.inepex.ineForm.shared.descriptorext;

import com.inepex.ineForm.shared.types.FormUnitT;

@SuppressWarnings("serial")
public class FormUnitRDesc extends FormRDescBase {
	
	protected FormUnitT formUnitType;

	public FormUnitRDesc() {
	}
	
	public FormUnitRDesc(FormUnitT formUnitType) {
		this.formUnitType=formUnitType;
	}
	
	public FormUnitRDesc(FormUnitT formUnitType, String... propList) {
		this(formUnitType);
		addProps(propList);
	}

	public FormUnitT getFormUnitType() {
		return formUnitType;
	}

	public void setFormUnitType(FormUnitT formUnitType) {
		this.formUnitType = formUnitType;
	}

	
}
