package com.inepex.ineForm.shared.descriptorext;

import com.inepex.ineForm.shared.types.PanelWidgetT;
import com.inepex.ineom.shared.descriptor.Prop;

public class PanelWidgetRDesc extends FormRDescBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3806100768334487463L;
	
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
	
	public PanelWidgetRDesc(PanelWidgetT panelType,  Prop... propList) {
		this(panelType);
		
		for (Prop prop : propList){
			props.put(prop.getName(), prop);
		}
	}

	public PanelWidgetT getPanelType() {
		return panelType;
	}

	public void setPanelType(PanelWidgetT panelType) {
		this.panelType = panelType;
	}
	
	
	
	
}
