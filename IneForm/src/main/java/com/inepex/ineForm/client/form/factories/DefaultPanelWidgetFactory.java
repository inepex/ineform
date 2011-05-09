package com.inepex.ineForm.client.form.factories;

import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.panelwidgets.DisplayedFormUnitChangeHandler;
import com.inepex.ineForm.client.form.panelwidgets.FlowPanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.HorizontalPanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.PanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.PlaceholderPanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.StepperPanelPageWidget;
import com.inepex.ineForm.client.form.panelwidgets.StepperPanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.TabPageWidget;
import com.inepex.ineForm.client.form.panelwidgets.TabPanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.VerticalPanelWidget;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;
import com.inepex.ineForm.shared.types.PanelWidgetT;

public class DefaultPanelWidgetFactory implements PanelWidgetFactory{

	protected PanelWidgetT getTypeFromDescriptor(PanelWidgetRDesc paneldesc){
		PanelWidgetT type;
		
		//DEFAULT
		if(paneldesc==null || paneldesc.getPanelType()==null) 
			type=IneFormProperties.defPanelWidgetType;
		else 
			type=paneldesc.getPanelType();
		
		return type;
	}
	
	@Override
	public PanelWidget createPanel(PanelWidgetRDesc paneldesc, PanelWidget parent, DisplayedFormUnitChangeHandler handler) {
		
		PanelWidgetT type = getTypeFromDescriptor(paneldesc);
		
		if(type.equals(PanelWidgetT.FLOWPANEL))
			return new FlowPanelWidget(type,parent, handler);
		
		if(type.equals(PanelWidgetT.VERTICALPANEL))
			return new VerticalPanelWidget(type,parent, handler);
		
		if(type.equals(PanelWidgetT.HORIZONTALPANEL))
			return new HorizontalPanelWidget(type,parent, handler);

		if(type.equals(PanelWidgetT.PLACEHOLDERPANEL))
			return new PlaceholderPanelWidget(type,parent, paneldesc, handler);
		
		if(type.equals(PanelWidgetT.TABPANEL))
			return new TabPanelWidget(type,parent, handler);
		
		if(type.equals(PanelWidgetT.TABPAGE))
			return new TabPageWidget(type, parent, paneldesc.getDisplayName(), handler);
		
		if(type.equals(PanelWidgetT.STEPPERPAGE))
			return new StepperPanelPageWidget(type,parent, handler);
		
		if(type.equals(PanelWidgetT.STEPPERPANEL))
			return new StepperPanelWidget(type,parent, handler);
			
		return null;
	}

}
