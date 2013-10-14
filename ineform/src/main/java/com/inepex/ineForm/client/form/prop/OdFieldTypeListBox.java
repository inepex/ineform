package com.inepex.ineForm.client.form.prop;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.inepex.ineForm.client.general.IneListbox;
import com.inepex.ineForm.shared.types.ODFieldType;

public class OdFieldTypeListBox extends FlowPanel {

	private IneListbox listBox = new IneListbox();
	
	public OdFieldTypeListBox() {
		listBox.setWidthOfCosturction(120);
		add(listBox);
		
		for(ODFieldType type : ODFieldType.values()) {
			listBox.getListBox().addItem(
					ODFieldType.getODFieldTypeName(type),
					type.toString());
		}
	}
	
	public void setValue(ODFieldType type) {
		listBox.getListBox().setSelectedIndex(type.ordinal());
	}
	
	public ODFieldType getValue() {
		return ODFieldType.values()[listBox.getListBox().getSelectedIndex()];
	}
	
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return listBox.getListBox().addChangeHandler(handler);
	}
}
