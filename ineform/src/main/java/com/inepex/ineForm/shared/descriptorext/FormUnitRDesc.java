package com.inepex.ineForm.shared.descriptorext;

import com.inepex.ineForm.shared.types.FormUnitT;
import com.inepex.ineom.shared.descriptor.Prop;

public class FormUnitRDesc extends FormRDescBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6655320901307573389L;
	protected FormUnitT formUnitType;

	public FormUnitRDesc() {
	}
	
	public FormUnitRDesc(FormUnitT formUnitType) {
		this.formUnitType=formUnitType;
	}
	
	public FormUnitRDesc(FormUnitT formUnitType, String... propList) {
		this(formUnitType);
		
		try {
		for (String propString : propList){
			Prop prop =Prop.fromString(propString);
			props.put(prop.getName(), prop);
		}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public FormUnitRDesc(FormUnitT formUnitType,  Prop... propList) {
		this(formUnitType);
		
		for (Prop prop : propList){
			props.put(prop.getName(), prop);
		}
	}

	public FormUnitT getFormUnitType() {
		return formUnitType;
	}

	public void setFormUnitType(FormUnitT formUnitType) {
		this.formUnitType = formUnitType;
	}

	
}
