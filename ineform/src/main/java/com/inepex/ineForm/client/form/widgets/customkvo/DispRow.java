package com.inepex.ineForm.client.form.widgets.customkvo;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.inepex.ineForm.client.general.ErrorMessageManagerInterface;
import com.inepex.ineForm.client.general.SimpleTableErrorMessageManager;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.shared.types.ODFieldType;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;

public class DispRow {
	
	private final FlexTable rowTable;
	
	private final RemoveCallback removeCallback;
	private final RowValueChangeCallback rowValueChangeCallback;
	private final CustomKVORow row;
	
	private final AttachHackKeyWidget attachHackKeyWidget = new AttachHackKeyWidget() ;
	private final TextBox keyBox = new TextBox();
	
	private final OdFieldTypeListBox typeBox = new OdFieldTypeListBox();
	
	private final FlowPanel valueBoxes = new FlowPanel();
	private final TextBox valueBox = new TextBox();
	private final CheckBox valueBooleanBox = new CheckBox();
	
	private final Button removeBtn = new Button(IneFormI18n.REMOVE());
	private SimpleTableErrorMessageManager emm;
	
	public DispRow(CustomKVORow row, RemoveCallback removeCallback, RowValueChangeCallback rowValueChangeCallback, FlexTable rowTable) {
		this.rowTable=rowTable;
		this.row=row;
		this.removeCallback=removeCallback;
		this.rowValueChangeCallback=rowValueChangeCallback;
		
		createRowUI();
		
		keyBox.setValue(row.getKey());
		typeBox.setValue(row.getType());
		refreshValue();
	}
	
	private void refreshValue() {
		String val = row.getValue();
		if(val==null) val="";
		
		if(ODFieldType.BOOLEAN.equals(row.getType())) {
			valueBox.setVisible(false);
			valueBox.setValue("");
			
			valueBooleanBox.setVisible(true);
			Boolean booleanValue=false;
			try {
				booleanValue=Boolean.parseBoolean(val);
			} catch (NumberFormatException e) {
				//nothing to do
			}
			row.setValue(booleanValue.toString());
			valueBooleanBox.setValue(booleanValue);
		} else {
			valueBooleanBox.setVisible(false);
			valueBooleanBox.setValue(false);
			
			valueBox.setVisible(true);
			valueBox.setValue(val);
		}
	}
	
	public ErrorMessageManagerInterface getErrorManager() {
		return emm;
	}
	
	private void createRowUI() {
		int currentRow = rowTable.getRowCount();
		
		attachHackKeyWidget.add(keyBox);
		rowTable.setWidget(currentRow, 0, attachHackKeyWidget);
		
		rowTable.setWidget(currentRow, 1, typeBox);
		
		valueBoxes.add(valueBox);
		valueBoxes.add(valueBooleanBox);
		rowTable.setWidget(currentRow, 2, valueBoxes); 
		
		rowTable.setWidget(currentRow, 3, removeBtn);
		
		rowTable.addCell(currentRow);
		emm = new SimpleTableErrorMessageManager(rowTable.getCellFormatter().getElement(currentRow, 4));
	}
	
	private class AttachHackKeyWidget extends HandlerAwareFlowPanel {
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
					row.clearDataIfCanNotParse();
					refreshValue();
					
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
			
			registerHandler(valueBooleanBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
	
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					Boolean val = valueBooleanBox.getValue();
					row.setValue(val==null ? null : val.toString());
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
}