package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineFrame.client.widgets.CaptchaWidget;
import com.inepex.ineom.shared.descriptor.FDesc;

public class CaptchaFW extends StringFormWidget {

	private CaptchaWidget captchaWidget;
	
	public CaptchaFW(FDesc fielddescriptor) {
		super(fielddescriptor);
		captchaWidget = new CaptchaWidget();
		captchaWidget.getTextBox().setWidth(IneFormProperties.DEFAULT_TextBoxWidth);
		initWidget(captchaWidget);
	}
	
	public CaptchaWidget getCaptchaWidget() {
		return captchaWidget;
	}
	
	@Override
	protected void onAttach() {
		registerHandler(captchaWidget.getTextBox().addValueChangeHandler(new ValueChangeHandler<String>() {		
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				fireFormWidgetChanged();
			}
		}));
		registerHandler(captchaWidget.getTextBox().addKeyUpHandler(new KeyUpHandler() {
			
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
		captchaWidget.getTextBox().setEnabled(enabled);
	}

	@Override
	public void setFocus(boolean focused) {
		captchaWidget.getTextBox().setFocus(focused);
	}


	@Override
	public String getStringValue() {
		if(captchaWidget.getTextBox().getValue().length()==0) return null;
		return captchaWidget.getTextBox().getValue();
	}

	@Override
	public void setStringValue(String value) {
		//nothing to do
	}
}
