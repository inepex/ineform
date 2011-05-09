package com.inepex.ineForm.client.form.widgets.chooser;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.DenyingFormWidget;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.inei18n.client.IneFormI18n_old;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.kvo.IneList;

public class ChooserFw extends DenyingFormWidget {
	public static final String relationChooser = "relationChooser"; //default
	public static final String enumChooser = "enumChooser";
	public static final String stringChooser = "stringChooser";
	
	private int listSize = 10;
	private Chooser chooser;
	private Grid panel = new Grid(1, 3);
	private ListBox valueRange = new ListBox();
	private ListBox selected = new ListBox();
	private VerticalPanel selectedPanel = new VerticalPanel();
	private VerticalPanel buttons = new VerticalPanel();
	private Button select = new Button(IneFormI18n_old.SELECT());
	private Button deselect = new Button(IneFormI18n_old.DESELECT());
	private Button selectAll = new Button(IneFormI18n_old.SELECTALL());
	private Button deselectAll = new Button(IneFormI18n_old.DESELECTALL());
	private Button moveUp = new Button(IneFormI18n_old.MOVEUP());
	private Button moveDown = new Button(IneFormI18n_old.MOVEDOWN());
	
	private Map<String, Item> stringToObject = new HashMap<String, Item>();

	private ClickHandler selectClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			if (valueRange.getSelectedIndex() != -1) {
				String value = valueRange.getValue(valueRange.getSelectedIndex());
				chooser.select(stringToObject.get(value), true, true);
			}
		}
	};
	
	private ClickHandler deselectClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			if (selected.getSelectedIndex() != -1) {
				String value = selected.getValue(selected.getSelectedIndex());
				chooser.deselect(stringToObject.get(value), true);
			}
		}
	};
	
	private ClickHandler selectAllClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			chooser.selectAll();
		}
	};
	
	private ClickHandler deselectAllClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			chooser.deselectAll();
		}
	};
	
	private DoubleClickHandler selectDoubleClick = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			if (valueRange.getSelectedIndex() != -1) {
				String value = valueRange.getValue(valueRange.getSelectedIndex());
				chooser.select(stringToObject.get(value), true, true);
			}
		}
	};
	
	private ClickHandler moveDownClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			if (selected.getSelectedIndex() != -1) {
				int oldIndex = selected.getSelectedIndex();
				String value = selected.getValue(selected.getSelectedIndex());
				chooser.moveDown(stringToObject.get(value));
				if (oldIndex < selected.getItemCount() - 1) selected.setSelectedIndex(oldIndex+1);
			}
		}
	};
	
	private ClickHandler moveUpClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			if (selected.getSelectedIndex() != -1) {
				int oldIndex = selected.getSelectedIndex();
				String value = selected.getValue(selected.getSelectedIndex());
				chooser.moveUp(stringToObject.get(value));
				if (oldIndex > 0) selected.setSelectedIndex(oldIndex-1);
			}
		}
	};
	
	public ChooserFw(FormContext formCtx
			, FDesc fieldDescriptor
			, WidgetRDesc widgetRDesc
			, String relationDescriptorName) {
		super(fieldDescriptor);
		if (widgetRDesc.hasProp(enumChooser) 
				|| widgetRDesc.hasProp(stringChooser)){
			chooser = new EnumChooser(formCtx.descStore
					, this
					, fieldDescriptor
					, widgetRDesc
					, relationDescriptorName);
		} else {
			chooser = new RelationChooser(formCtx
					, this
					, fieldDescriptor
					, relationDescriptorName);
		}
		initWidget(panel);
		
		panel.setWidget(0, 0, valueRange);
		panel.setWidget(0, 1, buttons);
		buttons.add(selectAll);
		buttons.add(select);
		buttons.add(deselect);
		buttons.add(deselectAll);
		panel.setWidget(0, 2, selectedPanel);
		if (chooser.isSupportsOrdering()) selectedPanel.add(moveUp);
		selectedPanel.add(selected);
		if (chooser.isSupportsOrdering()) selectedPanel.add(moveDown);
		
		valueRange.setVisibleItemCount(listSize);
		selected.setVisibleItemCount(listSize);
		
		addStyleName("ChooserFw");
		selected.setWidth(IneFormProperties.DEFAULT_ListBoxWidth);
		valueRange.setWidth(IneFormProperties.DEFAULT_ListBoxWidth);
		
		if (widgetRDesc.hasProp(enumChooser) 
				|| widgetRDesc.hasProp(stringChooser)){
			reRender();
		}
	}
	
	@Override
	public boolean handlesList() {
		return true;
	}

	@Override
	public void setListValue(IneList value) {
		if (value == null || value.getRelationList() == null)
		{
			return;
		}
			
		chooser.setSelected(value.getRelationList());
	}
	
	@Override
	public IneList getListValue() {
		IneList list = new IneList();
		list.setRelationList(chooser.getChanged());
		return list;
	}
	
	public void reRender(){
		valueRange.clear();
		selected.clear();
		stringToObject.clear();
		if (chooser.getValueRange() != null)
		for (Item item : chooser.getValueRange()){
			String id = String.valueOf(item.getId());
			stringToObject.put(id, item);
			valueRange.addItem(item.getDisplayName(), id);
		}
		
		if (chooser.getSelected() != null)
		for (Item item : chooser.getSelected()){
			String id = String.valueOf(item.getId());
			stringToObject.put(id, item);
			selected.addItem(item.getDisplayName(), id);
		}
	}

	
	@Override
	protected void onAttach() {
		registerHandler(select.addClickHandler(selectClickHandler));
		registerHandler(valueRange.addDoubleClickHandler(selectDoubleClick));
		registerHandler(deselect.addClickHandler(deselectClickHandler));
		registerHandler(selectAll.addClickHandler(selectAllClickHandler));
		registerHandler(deselectAll.addClickHandler(deselectAllClickHandler));
		registerHandler(moveDown.addClickHandler(moveDownClickHandler));
		registerHandler(moveUp.addClickHandler(moveUpClickHandler));
		super.onAttach();
	}

	@Override
	public void setEnabled(boolean enabled) {
		selected.setEnabled(enabled);
		valueRange.setEnabled(enabled);
		deselect.setEnabled(enabled);
		select.setEnabled(enabled);
		selectAll.setEnabled(enabled);
		deselectAll.setEnabled(enabled);
	}
}
