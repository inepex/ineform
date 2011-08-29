package com.inepex.ineForm.client.form.widgets.customkvo;

import com.google.gwt.user.client.ui.ListBox;
import com.inepex.ineForm.shared.types.ODFieldType;

public class OdFieldTypeListBox extends ListBox{

	public OdFieldTypeListBox() {
		super(false);
		
		for(ODFieldType type : ODFieldType.values()) {
			addItem(type.toString());
		}
	}
	
	public void setValue(ODFieldType type) {
		setSelectedIndex(type.ordinal());
	}
	
	public ODFieldType getValue() {
		return ODFieldType.values()[getSelectedIndex()];
	}
}
