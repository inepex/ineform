package com.inepex.ineForm.client.form.widgets;

import java.util.ArrayList;
import java.util.List;

import com.inepex.ineForm.client.form.widgets.listbox.AbstractListBoxFW;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class EnumListFW extends AbstractListBoxFW {
	public final static String enumValues = "enumValues";
	private List<String> hidedNames = null;
	private List<String> hidedValues = null;
	
	public EnumListFW(FDesc fieldDescriptor, WidgetRDesc wrDesc, String enumValues) {
		super(fieldDescriptor, wrDesc);
		int i=0;
		for (String value : enumValues.split(IFConsts.enumValueSplitChar)){
			getListBox().addItem(value, Integer.toString(i++));
		}
	}

    @Override
    public Long getLongValue() {
    	if(notSelectedText.equals(getListBox().getValue(getListBox().getSelectedIndex()))) return null;
    	return Long.parseLong(getListBox().getValue(getListBox().getSelectedIndex()));
	}
    

    @Override
    public void setLongValue(Long value) {
    	String val;
    	
    	if(value==null && !allowsNull && getListBox().getItemCount()>0) {
    		getListBox().setSelectedIndex(0);
    		return;
    	}
    	
      	if (value == null) val=notSelectedText;
      	else val=value.toString();
      	
      	for(int i=0; i<getListBox().getItemCount(); i++) {
      		if(val.equals(getListBox().getValue(i))) {
      			getListBox().setSelectedIndex(i);
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
      	
      	for(int i=0; i<getListBox().getItemCount(); i++) {
      		if(val.equals(getListBox().getValue(i))) {
      			if(hidedNames==null) hidedNames=new ArrayList<String>();
      			if(hidedValues==null) hidedValues=new ArrayList<String>();
      			hidedNames.add(getListBox().getItemText(i));
      			hidedValues.add(val);
      			getListBox().removeItem(i);
      			return;
      		}
      	}
	}
	
	public void showItem(Long value) {
		if(value==null || hidedValues==null) return;
		
		String val=value.toString();
		
		for(int i=0; i<hidedValues.size(); i++) {
			if(val.equals(hidedValues.get(i))) {
				getListBox().addItem(hidedNames.get(i), val);
				hidedNames.remove(i);
				hidedValues.remove(i);
				return;
			}
		}
	}	
}
