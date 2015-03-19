package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.inepex.ineForm.client.general.IneListbox;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class ThreeWayBoolFw extends DenyingFormWidget {
	
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String NULL = "null";
//	private final ListBox listBox = new ListBox(false);
	private final IneListbox listBox = new IneListbox();

	public ThreeWayBoolFw(FDesc fieldDescriptor, String nullText, String trueText, String falseText) {
		super(fieldDescriptor);
		if (nullText == null) nullText = IneFormI18n.notSetText();
		if (trueText == null) trueText = IneFormI18n.trueText();
		if (falseText == null) falseText = IneFormI18n.falseText();
				
		initWidget(listBox.asWidget());
		listBox.getListBox().addItem(nullText);
		listBox.getListBox().addItem(trueText);
		listBox.getListBox().addItem(falseText);
	}

	@Override
	public void setEnabled(boolean enabled) {
		listBox.getListBox().setEnabled(enabled);
	}

	@Override
	public boolean handlesBoolean() {
		return true;
	}

	@Override
	public void setBooleanValue(Boolean value) {
		if(value==null) {
			listBox.getListBox().setSelectedIndex(0);
		} else {
			if(value)
				listBox.getListBox().setSelectedIndex(1);
			else 
				listBox.getListBox().setSelectedIndex(2);
		}
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(listBox.getListBox().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				fireFormWidgetChanged();
			}
		}));
	}
	
	@Override
	public Boolean getBooleanValue() {
		switch(listBox.getListBox().getSelectedIndex()) {
		case 0:
			return null;
		case 1:
			return true;
		case 2: 
			return false;
		}
		
		throw new RuntimeException("The seleceted item of ThreeWayBoolFw "+listBox.getListBox().getSelectedIndex());
	}
}
