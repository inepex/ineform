package com.inepex.ineForm.client.form.widgets.listbox;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.widgets.DenyingFormWidget;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.FDesc;

public class AbstractListBoxFW extends DenyingFormWidget {
	public static class Params {
		public static String notSelectedText = "notSelectedText";
	}
	
	protected final ListBox listBox = new ListBox();
	
	public final static String DEFAULT_notSelectedText = "--";
	
	protected String notSelectedText = DEFAULT_notSelectedText;
	
	protected boolean allowsNull = true;
	
	protected FDesc fieldDescriptor;
	protected WidgetRDesc wrDesc;
	
	public AbstractListBoxFW(FDesc fieldDesc, WidgetRDesc wrDesc) {
		super(fieldDesc);
		this.fieldDescriptor = fieldDesc;
		this.wrDesc = wrDesc;
		initWidget(listBox);
		setAllowsNull(fieldDesc.isNullable());
		listBox.setWidth(IneFormProperties.DEFAULT_ListBoxWidth);
		if (wrDesc.hasProp(Params.notSelectedText)){
			notSelectedText = wrDesc.getPropValue(Params.notSelectedText);
		}
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
		listBox.addItem(notSelectedText, notSelectedText);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		listBox.setEnabled(enabled);
	}

	@Override
	public void setFocus(boolean focused) {
		listBox.setFocus(focused);
	}
	
	public ListBox getListBox(){
		return listBox;
	}
    
}
