package com.inepex.ineForm.client.form.widgets;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.inepex.ineom.shared.descriptor.FDesc;

public class RadioBoolFW extends DenyingFormWidget {
	public final static String VERTICAL = "vertical";
	
	private ComplexPanel mainPanel = new FlowPanel();
	private final RadioButtonBase rbTrue;
	private final RadioButtonBase rbFalse;
	
	public static long radioGruop = 0L; 

	public RadioBoolFW(FDesc fieldDescriptor, String trueString, String falseString, boolean vertical) {
		super(fieldDescriptor);
		if (vertical) mainPanel = new VerticalPanel();
		initWidget(mainPanel);
		
		List<RadioButtonBase> radios =createGroup(trueString, falseString);
		rbTrue=radios.get(0);
		rbFalse=radios.get(1);
		
		mainPanel.add(rbTrue);
		mainPanel.add(rbFalse);
	}
	
	protected List<RadioButtonBase> createGroup(String label1, String label2) {
		return Arrays.asList(
				(RadioButtonBase)new IneFormRadioButton("RadioBoolFW"+radioGruop, label1),
				(RadioButtonBase)new IneFormRadioButton("RadioBoolFW"+radioGruop++, label2));
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(rbTrue.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				fireFormWidgetChanged();
			}
		}));
		
		registerHandler(rbFalse.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				fireFormWidgetChanged();
			}
		}));
	}

	@Override
	public void setEnabled(boolean enabled) {
		rbFalse.setEnabled(enabled);
		rbTrue.setEnabled(enabled);
	}
	
	@Override
	public boolean handlesBoolean() {
		return true;
	}

	@Override
	public void setBooleanValue(Boolean value) {
		if(value!=null) {
			if(value) {
				rbTrue.setValue(true);
			} else {
				rbFalse.setValue(true);
			}
		} else {
			rbFalse.setValue(false);
			rbTrue.setValue(false);
		}
	}

	@Override
	public Boolean getBooleanValue() {
		if(rbTrue.getValue() || rbFalse.getValue()) {
			return rbTrue.getValue();
		} else return null;
	}
}
