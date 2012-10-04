package com.inepex.ineForm.client.form.widgets.customkvo;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.shared.types.ODFieldType;

public class OdFieldTypeListBox extends FlowPanel {

	private ListBox listBox = new ListBox(false);
	
	public OdFieldTypeListBox() {
		setStyleName(ResourceHelper.ineformRes().style().OdFieldTypeListBoxWrapper());
		
		add(listBox);
		
		for(ODFieldType type : ODFieldType.values()) {
			listBox.addItem(
					ODFieldType.getODFieldTypeName(type),
					type.toString());
		}
	}
	
	public void setValue(ODFieldType type) {
		listBox.setSelectedIndex(type.ordinal());
	}
	
	public ODFieldType getValue() {
		return ODFieldType.values()[listBox.getSelectedIndex()];
	}
	
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return listBox.addChangeHandler(handler);
	}
}
