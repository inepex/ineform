package com.inepex.ineForm.client.form.widgets.customkvo;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.inepex.ineForm.client.general.ErrorMessageManagerInterface;
import com.inepex.ineForm.client.general.FlowPanelBasedEMM;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;

class DispRow extends HandlerAwareComposite{
	
	private final HorizontalPanel hp = new HorizontalPanel();
	
	private final RemoveCallback removeCallback;
	private final RowValueChangeCallback rowValueChangeCallback;
	private final CustomKVORow row;
	
	private final TextBox keyBox = new TextBox();
	private final OdFieldTypeListBox typeBox = new OdFieldTypeListBox();
	private final TextBox valueBox = new TextBox();
	private final Button removeBtn = new Button(IneFormI18n.REMOVE());
	private final FlowPanelBasedEMM errorManager = new FlowPanelBasedEMM();
	
	public DispRow(CustomKVORow row, RemoveCallback removeCallback, RowValueChangeCallback rowValueChangeCallback) {
		this.row=row;
		this.removeCallback=removeCallback;
		this.rowValueChangeCallback=rowValueChangeCallback;
		
		hp.add(keyBox);
		hp.add(typeBox);
		hp.add(valueBox);
		hp.add(removeBtn);
		hp.add(errorManager);
		
		keyBox.setStyleName(ResourceHelper.getRes().style().dispRowKey());
		
		keyBox.setValue(row.getKey());
		typeBox.setValue(row.getType());
		valueBox.setValue(row.getValue());
		
		initWidget(hp);
	}
	
	public ErrorMessageManagerInterface getErrorManager() {
		return errorManager;
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(keyBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				row.setKey(keyBox.getValue());
				rowValueChangeCallback.onRowValueChanged();
			}
		}));
		
		registerHandler(typeBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				row.setType(typeBox.getValue());
				rowValueChangeCallback.onRowValueChanged();
			}
		}));
		
		registerHandler(valueBox.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				row.setValue(valueBox.getValue());
				rowValueChangeCallback.onRowValueChanged();
			}
		}));
		
		registerHandler(removeBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				removeCallback.onRemove(row);
			}
		}));
	}
}