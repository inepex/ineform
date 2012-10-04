package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.formunits.SimpleTableFormUnit;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public abstract class TextBoxFWBase extends StringFormWidget {

	protected TextBox textBox; 

	public TextBoxFWBase(FDesc fielddescriptor, WidgetRDesc wrDesc) {
		super(fielddescriptor);		
	}
	
	protected void updateWidth(WidgetRDesc wrDesc){
		if (wrDesc.hasProp(SimpleTableFormUnit.WIDTH)){
			textBox.setWidth(wrDesc.getPropValue(SimpleTableFormUnit.WIDTH));
		} else {
			textBox.setWidth(IneFormProperties.DEFAULT_TextBoxWidth);
		}
	}
	
	@Override
	protected void onAttach() {
		registerHandler(textBox.addValueChangeHandler(new ValueChangeHandler<String>() {		
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				fireFormWidgetChanged();
			}
		}));
		registerHandler(textBox.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				fireFormWidgetChanged();
			}
		}));

		super.onAttach();
	} 
	
	

	@Override
	public boolean isFocusable() {
		return true;
	}

	@Override
	public void setEnabled(boolean enabled) {
		textBox.setEnabled(enabled);
	}

	@Override
	public void setFocus(boolean focused) {
		textBox.setFocus(focused);
	}


	@Override
	public String getStringValue() {
		if(textBox.getValue().length()==0) return null;
		return textBox.getValue();
	}

	@Override
	public void setStringValue(String value) {
		if(value==null) textBox.setValue("");
		else textBox.setValue(value);
	}

}
