package com.inepex.ineForm.shared.descriptorext;

import com.inepex.ineForm.client.form.formunits.SimpleTableFormUnit;
import com.inepex.ineForm.shared.types.FWTypes;

public class WidgetRDesc extends FormRDescBase {

	private static final long serialVersionUID = -8017885749027661786L;
	
	private FWTypes formWidgetType;

	public WidgetRDesc() {
	}
	
	public WidgetRDesc(FWTypes formWidgetType) {
		this.formWidgetType = formWidgetType;
	}

	public WidgetRDesc(FWTypes formWidgetType, String... propList) {
		this(formWidgetType);
		addProps(propList);
	}

	public FWTypes getFormWidgetType() {
		return formWidgetType;
	}
	
	public WidgetRDesc setDisplayName(String displayName) {
		return setDisplayName(displayName, WidgetRDesc.class);
	}

	public WidgetRDesc width(int pixel) {
		addProp(SimpleTableFormUnit.WIDTH, pixel+"px");
		return this;
	}
}
