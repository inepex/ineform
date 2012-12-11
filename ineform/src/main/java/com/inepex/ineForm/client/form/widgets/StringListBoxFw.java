package com.inepex.ineForm.client.form.widgets;

import java.util.ArrayList;
import java.util.List;

import com.inepex.ineForm.client.form.widgets.listbox.AbstractListBoxFW;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

/**
 * A listbox with String value and String list value range
 * @author SoTi
 *
 */
public class StringListBoxFw extends AbstractListBoxFW {

	private List<String> valueRange = new ArrayList<String>();
	
	public StringListBoxFw(FDesc fieldDesc, WidgetRDesc wrDesc) {
		super(fieldDesc, wrDesc);
	}
	
	public void setValueRange(List<String> valueRange){
		this.valueRange = valueRange;
		listBox.clear();
		if (allowsNull) addNullItem();
		for (String value : valueRange){
			listBox.addItem(value);
		}
	}
	
	@Override
	public String getStringValue() {
		int selectedIndex = listBox.getSelectedIndex();
		
		if(selectedIndex==-1 || allowsNull && selectedIndex==0)
			return null;
		
		return valueRange.get(selectedIndex - (allowsNull ? 1 : 0) );
	}
	
	@Override
	public boolean handlesString() {
		return true;
	}

	@Override
	public void setStringValue(String value) {
		listBox.setSelectedIndex((allowsNull ? 1 : 0) + valueRange.indexOf(value));
	}


}
