package com.inepex.ineForm.client.form.widgets;

import java.util.ArrayList;
import java.util.List;

import com.inepex.ineForm.client.form.widgets.listbox.AbstractListBoxFW;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.kvo.IFConsts;

public class EnumListFW extends AbstractListBoxFW {
	public final static String enumValues = "enumValues";
	private List<String> hidedNames = null;
	private List<String> hidedValues = null;
	
	public EnumListFW(FDesc fieldDescriptor, WidgetRDesc wrDesc, String enumValues) {
		super(fieldDescriptor, wrDesc);
		int i=0;
		for (String value : enumValues.split(IFConsts.enumValueSplitChar)){
			listBox.addItem(value, Integer.toString(i++));
		}
	}

    @Override
    public Long getLongValue() {
    	if(notSelectedText.equals(listBox.getValue(listBox.getSelectedIndex()))) return null;
    	return Long.parseLong(listBox.getValue(listBox.getSelectedIndex()));
	}
    

    @Override
    public void setLongValue(Long value) {
    	String val;
    	
    	if(value==null && !allowsNull && listBox.getItemCount()>0) {
    		listBox.setSelectedIndex(0);
    		return;
    	}
    	
      	if (value == null) val=notSelectedText;
      	else val=value.toString();
      	
      	for(int i=0; i<listBox.getItemCount(); i++) {
      		if(val.equals(listBox.getValue(i))) {
      			listBox.setSelectedIndex(i);
      			return;
      		}
      	}
    }
    
	@Override
	public boolean handlesLong() {
		return true;
	}
	
	public void hideItem(Long value) {
		if(value==null) return;
		
		String val=value.toString();
      	
      	for(int i=0; i<listBox.getItemCount(); i++) {
      		if(val.equals(listBox.getValue(i))) {
      			if(hidedNames==null) hidedNames=new ArrayList<String>();
      			if(hidedValues==null) hidedValues=new ArrayList<String>();
      			hidedNames.add(listBox.getItemText(i));
      			hidedValues.add(val);
      			listBox.removeItem(i);
      			return;
      		}
      	}
	}
	
	public void showItem(Long value) {
		if(value==null || hidedValues==null) return;
		
		String val=value.toString();
		
		for(int i=0; i<hidedValues.size(); i++) {
			if(val.equals(hidedValues.get(i))) {
				listBox.addItem(hidedNames.get(i), val);
				hidedNames.remove(i);
				hidedValues.remove(i);
				return;
			}
		}
	}	
}
