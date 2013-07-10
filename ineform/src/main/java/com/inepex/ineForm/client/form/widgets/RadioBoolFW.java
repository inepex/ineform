package com.inepex.ineForm.client.form.widgets;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.inepex.ineForm.client.general.IneRadioButton;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class RadioBoolFW extends DenyingFormWidget {
	public final static String VERTICAL = "vertical";
	
	private ComplexPanel mainPanel = new FlowPanel();
	private final IneRadioButton rbTrue;
	private final IneRadioButton rbFalse;
	
	public static long radioGruop = 0L; 

	public RadioBoolFW(FDesc fieldDescriptor, String trueString, String falseString, boolean vertical) {
		super(fieldDescriptor);
		if (vertical) mainPanel = new VerticalPanel();
		initWidget(mainPanel);
		
		List<IneRadioButton> radios =IneRadioButton.createGroup(trueString, falseString);
		rbTrue=radios.get(0);
		rbFalse=radios.get(1);
		
		mainPanel.add(rbTrue);
		mainPanel.add(rbFalse);
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
		rbFalse.setWholeGroupEnabled(enabled);
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
