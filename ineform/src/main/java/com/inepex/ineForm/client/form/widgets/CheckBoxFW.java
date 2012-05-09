package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.FDesc;

public class CheckBoxFW extends DenyingFormWidget {

	public static final String CHECKBOXTEXT = "checkBoxText";
	public static final String CHECKBOXHTML = "checkBoxHTML";
	
	private CheckBox checkBox = new CheckBox();
	
	public CheckBoxFW(FDesc fielddescriptor, WidgetRDesc wrDesc) {
		super(fielddescriptor);
		initWidget(checkBox);
		
		if(wrDesc.getPropValue(CHECKBOXTEXT)!=null) {
			checkBox.setText(wrDesc.getPropValue(CHECKBOXTEXT));
		}else if(wrDesc.getPropValue(CHECKBOXHTML) != null){
			checkBox.setHTML(wrDesc.getPropValue(CHECKBOXHTML));
		}
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		registerHandler(checkBox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				fireFormWidgetChanged();
			}
		}));
	}
	
	@Override
	public boolean handlesBoolean() {
		return true;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		checkBox.setEnabled(enabled);
	}
	
	@Override
	public void setBooleanValue(Boolean value) {
		checkBox.setValue(value!=null && value);
	}
	
	@Override
	public Boolean getBooleanValue() {
		return checkBox.getValue();
	}
}
