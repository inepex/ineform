package com.inepex.ineForm.client.form.widgets.listbox;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.widgets.DenyingFormWidget;
import com.inepex.ineom.shared.descriptor.FDesc;

public class AbstractListBoxFW extends DenyingFormWidget {
	
	protected final ListBox listBox = new ListBox();
	
	public final static String DEFAULT_notSelectedText = "--";
	
	protected boolean allowsNull = true;
	
	protected FDesc fieldDescriptor;
	
	public AbstractListBoxFW(FDesc fieldDesc) {
		super(fieldDesc);
		this.fieldDescriptor = fieldDesc;
		initWidget(listBox);
		setAllowsNull(fieldDesc.isNullable());
		listBox.setWidth(IneFormProperties.DEFAULT_ListBoxWidth);		
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(listBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				fireFormWidgetChanged();
			}
		}));
	}
	
	public void setAllowsNull(boolean allowsNull) {		
		this.allowsNull = allowsNull;
		if (allowsNull)
			addNullItem();
	}
	
	protected void addNullItem() {
		listBox.addItem(DEFAULT_notSelectedText, DEFAULT_notSelectedText);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		listBox.setEnabled(enabled);
	}

	@Override
	public void setFocus(boolean focused) {
		listBox.setFocus(focused);
	}
    
}
