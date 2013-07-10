package com.inepex.ineForm.client.form.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.inepex.ineForm.client.form.widgets.listbox.AbstractListBoxFW;
import com.inepex.ineForm.client.general.IneRadioButton;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class RadioEnumSelectorFW extends DenyingFormWidget {
	
	public final static String enumValues = EnumListFW.enumValues;
	protected final static String DEFAULT_notSelectedText = AbstractListBoxFW.DEFAULT_notSelectedText;
	protected final boolean allowsNull;
	public final static String VERTICAL = "vertical";
	
	protected ComplexPanel mainPanel = new FlowPanel();
	protected List<IneRadioButton> radioButtons = new ArrayList<IneRadioButton>();
	
	private ClickHandler changeHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			fireFormWidgetChanged();
		}
	};
	
	public RadioEnumSelectorFW(FDesc fieldDescriptor, String values, WidgetRDesc renderDescriptor) {
		super(fieldDescriptor);
		this.allowsNull = fieldDescriptor.isNullable();
		
		String[] valuesAsArray = values.split(IFConsts.enumValueSplitChar);
		String[] labels;
		if(allowsNull) {
			labels = new String[valuesAsArray.length+1];
			labels[0] = DEFAULT_notSelectedText;
			for(int i=0; i<valuesAsArray.length; i++) 
				labels[i+1]=valuesAsArray[i];
		} else {
			labels=valuesAsArray;
		}
		
		radioButtons=IneRadioButton.createGroup(labels);
		
		if (renderDescriptor.hasProp(VERTICAL)) mainPanel = new VerticalPanel();
		
		initWidget(mainPanel);
		for(IneRadioButton rb : radioButtons) 
			mainPanel.add(rb);
		
		radioButtons.get(0).setValue(true);
		
		mainPanel.setStyleName(ResourceHelper.ineformRes().style().displayInline());
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		for(IneRadioButton rb : radioButtons) {
			registerHandler(rb.addClickHandler(changeHandler));
		}
	}

    @Override
    public Long getLongValue() {
    	for(int ind=0; ind<radioButtons.size(); ind++) {
    		if(radioButtons.get(ind).getValue()==true) {
    			if(allowsNull) {
    				if(ind==0) return null;
    				return new Long(ind-1);
    			} else {
    				return new Long(ind);
    			}
    		}
    	}
    	
    	return null;
	}
    
    public void selectFirstVisibleIfFwIsEmpty() {
    	if(getLongValue()==null) {
    		for(IneRadioButton rb : radioButtons) {
    			if(rb.isVisible()) {
    				rb.setValue(true);
    				return;
    			}
    		}
    	}
    }

    @Override
    public void setLongValue(Long value) {
      	if (value == null) {
  			for(IneRadioButton rb : radioButtons) {
  				rb.setValue(false);
  			}
  		
  			radioButtons.get(0).setValue(true);
  			
      	} else {
      		radioButtons.get(index(value)).setValue(true);
      	}	
    }
    
	@Override
	public boolean handlesLong() {
		return true;
	}
	
	public void hideItem(Long item) {
		radioButtons.get(index(item)).setVisible(false);
		radioButtons.get(index(item)).setValue(false);
	}
	
	public void showItem(Long item) {
		radioButtons.get(index(item)).setVisible(true);
	}
	
	private int index(Long item) {
		if(allowsNull && item==null) return 0;
		int ind = item.intValue();
  		if(allowsNull) ind++;
  		return ind;
	}
}
