package com.inepex.ineForm.client.form.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.InlineLabel;
import com.inepex.ineForm.client.form.widgets.listbox.AbstractListBoxFW;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.kvo.IFConsts;

public class EnumLabelFW extends DenyingFormWidget {
	
	public final static String enumValues = EnumListFW.enumValues;
	protected final static String DEFAULT_notSelectedText = AbstractListBoxFW.DEFAULT_notSelectedText;
	protected boolean allowsNull = true;
	
	protected Long longValue;
	protected List<String> displayableStrings = new ArrayList<String>();
	protected InlineLabel dispLabel = new InlineLabel(); 
	
	public EnumLabelFW(FDesc fieldDescriptor, String values) {
		super(fieldDescriptor);
		setAllowsNull(fieldDescriptor.isNullable());
		
		for (String value : values.split(IFConsts.enumValueSplitChar)){
			displayableStrings.add(value);
		}
		
		initWidget(dispLabel);
	}
	
	public void setAllowsNull(boolean allowsNull) {		
		this.allowsNull = allowsNull;
		if (allowsNull)
			addNullItem();
	}
	
	protected void addNullItem() {
		displayableStrings.add(DEFAULT_notSelectedText);
	}

    @Override
    public Long getLongValue() {
    	return longValue;
	}
    

    @Override
    public void setLongValue(Long value) {
    	longValue=value;
    	
      	if (value == null) {
      		dispLabel.setText("");
      		return;
      	}
		if (allowsNull) dispLabel.setText(displayableStrings.get(value.intValue()+1));
		else dispLabel.setText(displayableStrings.get(value.intValue()));
    }
    
	@Override
	public boolean handlesLong() {
		return true;
	}
	
	@Override
	public boolean isReadOnlyWidget() {
		return true;
	}
}
