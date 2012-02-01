package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.TextArea;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.formunits.SimpleTableFormUnit;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.FDesc;

public class TextAreaFW extends StringFormWidget {
	/**
	 * @Deprecated use {@link SimpleTableFormUnit#WIDTH} and {@link WidgetRDesc#width(int)} instead 
	 */
	public static final String textBoxWidth = SimpleTableFormUnit.WIDTH;
	
	public static final String textBoxHeight = "textBoxHeight";

	final TextArea textArea = new TextArea();

	public TextAreaFW(FDesc fielddescriptor, WidgetRDesc renderDesc) {
		super(fielddescriptor);
		initWidget(textArea);
		if (renderDesc.hasProp(SimpleTableFormUnit.WIDTH)){
			textArea.setWidth(renderDesc.getPropValue(SimpleTableFormUnit.WIDTH));
		} else {
			textArea.setWidth(IneFormProperties.DEFAULT_TextBoxWidth);
		}
		if (renderDesc.hasProp(textBoxHeight)){
			textArea.setHeight(renderDesc.getPropValue(textBoxHeight));
		} else { 
			textArea.setHeight(IneFormProperties.DEFAULT_TextAreaHeigth);
		}
		
	}
	
	@Override
	protected void onAttach() {
		registerHandler(textArea.addValueChangeHandler(new ValueChangeHandler<String>() {		
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				fireFormWidgetChanged();
			}
		}));
		registerHandler(textArea.addKeyUpHandler(new KeyUpHandler() {
			
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
		textArea.setEnabled(enabled);
	}

	@Override
	public void setFocus(boolean focused) {
		textArea.setFocus(focused);
	}


	@Override
	public String getStringValue() {
		if(textArea.getValue().length()==0) return null;
		return textArea.getValue();
	}

	@Override
	public void setStringValue(String value) {
		if(value==null) textArea.setValue("");
		else textArea.setValue(value);
	}

}
