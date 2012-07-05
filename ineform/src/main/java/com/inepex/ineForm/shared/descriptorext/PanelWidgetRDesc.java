package com.inepex.ineForm.shared.descriptorext;

import com.inepex.ineForm.shared.types.PanelWidgetT;

@SuppressWarnings("serial")
public class PanelWidgetRDesc extends FormRDescBase {
	
	protected PanelWidgetT panelType;

	public PanelWidgetRDesc() {
	}
	
	public PanelWidgetRDesc(PanelWidgetT panelType) {
		this.panelType=panelType;
	}
	
	public PanelWidgetRDesc(PanelWidgetT panelType, String... propList) {
		this(panelType);
		addProps(propList);
	}

	public PanelWidgetT getPanelType() {
		return panelType;
	}

	public void setPanelType(PanelWidgetT panelType) {
		this.panelType = panelType;
	}
	
	
	
	
}
