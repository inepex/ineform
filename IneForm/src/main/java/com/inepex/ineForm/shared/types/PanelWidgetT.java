package com.inepex.ineForm.shared.types;

import java.io.Serializable;

import com.inepex.ineForm.client.form.widgets.FormWidget;

/**
 * Defines names for different {@link FormWidget} types
 * 
 * 
 * @author istvan
 *
 */
public class PanelWidgetT implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3542896731206796817L;
	
	public final static String p_TitleHtml = "TitleHtml";
	
	public final static PanelWidgetT FLOWPANEL = new PanelWidgetT("FLOWPANEL");
	public final static PanelWidgetT VERTICALPANEL = new PanelWidgetT("VERTICALALPANEL");
	public final static PanelWidgetT HORIZONTALPANEL = new PanelWidgetT("HORIZONTAL");
	public final static PanelWidgetT TABPANEL = new PanelWidgetT("TABPANEL");
	public final static PanelWidgetT TABPAGE = new PanelWidgetT("TABPAGE");
	public final static PanelWidgetT STEPPERPAGE = new PanelWidgetT("STEPPERPAGE");
	public final static PanelWidgetT STEPPERPANEL = new PanelWidgetT("STEPPERPANEL");
	public final static PanelWidgetT PLACEHOLDERPANEL = new PanelWidgetT("PLACEHOLDERPANEL");
	
	private String typeName = "";

	public PanelWidgetT(){
		
	}

	protected PanelWidgetT(String typeName){
		this.typeName = typeName;
	}
	
	public String getTypeName() {
		return typeName;
	}	

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		if (obj instanceof PanelWidgetT)
			return ((PanelWidgetT)obj).getTypeName().equals(getTypeName());
		
		if (obj instanceof String)
			return ((String)obj).equals(getTypeName());
				
		return super.equals(obj);
	}
	
}
