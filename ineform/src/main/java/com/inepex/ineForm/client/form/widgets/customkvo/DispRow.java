package com.inepex.ineForm.client.form.widgets.customkvo;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOFW.RemoveCallback;
import com.inepex.ineForm.client.general.FlowPanelBasedEMM;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;

class DispRow extends HandlerAwareFlowPanel {
	
	private final RemoveCallback removeCallback;
	private final CustomKVORow row;
	
	private final TextBox keyBox = new TextBox();
	private final OdFieldTypeListBox typeBox = new OdFieldTypeListBox();
	private final TextBox valueBox = new TextBox();
	private final Button removeBtn = new Button(IneFormI18n.REMOVE());
	private final FlowPanelBasedEMM errorManager = FlowPanelBasedEMM.newInstance();
	
	public DispRow(CustomKVORow row, RemoveCallback removeCallback) {
		this.row=row;
		this.removeCallback=removeCallback;
		
		add(keyBox);
		add(typeBox);
		add(valueBox);
		add(removeBtn);
		add(errorManager);
		
		keyBox.setValue(row.getKey());
		typeBox.setValue(row.getType());
		valueBox.setValue(row.getValue());
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(keyBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				row.setKey(keyBox.getValue());
			}
		}));
		
		registerHandler(typeBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				row.setType(typeBox.getValue());
			}
		}));
		
		registerHandler(valueBox.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				row.setValue(valueBox.getValue());
			}
		}));
		
		registerHandler(removeBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				removeCallback.onClick(row);
			}
		}));
	}
}