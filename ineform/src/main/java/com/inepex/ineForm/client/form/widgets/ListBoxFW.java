package com.inepex.ineForm.client.form.widgets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.datamanipulator.ValueRangeResultCallback;
import com.inepex.ineForm.client.datamanipulator.events.KeyValueObjectListModifiedEvent;
import com.inepex.ineForm.client.datamanipulator.events.KeyValueObjectListModifiedEventHandler;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.listbox.AbstractListBoxFW;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;

public class ListBoxFW extends AbstractListBoxFW {
	
	private final Map<Long, Integer> itemIdIndexMap = new HashMap<Long, Integer>();
	
	private ValueRangeProvider valueRangeProvider;
	private RelationFDesc relationFDesc = null;
	
	private Relation value;
	
	protected FormContext formCtx;
	
	public ListBoxFW(FormContext formCtx, FDesc fieldDescriptor, WidgetRDesc wrDesc) {
		super(fieldDescriptor, wrDesc);
		this.formCtx = formCtx;
		this.valueRangeProvider = formCtx.valueRangeProvider;
		relationFDesc = (RelationFDesc) fieldDescriptor;

		loadDataFromValueRangeProvider();
	}

	private class ListModifyHandler implements KeyValueObjectListModifiedEventHandler {
		@Override
		public void onObjectListModified(
				KeyValueObjectListModifiedEvent event) {
			if (event.getListType().equals(relationFDesc.getRelatedDescriptorName()))
				loadDataFromValueRangeProvider();
		}
	}
	@Override
	protected void onAttach() {
		registerHandler(formCtx.eventBus.addHandler(KeyValueObjectListModifiedEvent.TYPE, new ListModifyHandler()));
		super.onAttach();
	}
	
	@Override
	protected void fireFormWidgetChanged() {
		int selected = listBox.getSelectedIndex();
		
		if(selected==-1 || selected==0 &&  allowsNull) value = null;
		else value = new Relation(Long.parseLong(listBox.getValue(selected)), listBox.getItemText(selected));
		
		super.fireFormWidgetChanged();
	}
	
	public void reLoadListAndKeepSelectedOrSetToNull() {
		if (valueRangeProvider != null) {
			listBox.setEnabled(false);
			valueRangeProvider.getRelationValueRange(fieldDescriptor, new ValueRangeResultCallback() {
				@Override
				public void onValueRangeResultReady(List<Relation> relationList) {
					if (relationList != null){
						listBox.setEnabled(true);
						ListBoxFW.this.setValueRange(relationList);
						if(value==null || !itemIdIndexMap.containsKey(value.getId())) {
							setRelationValue(null);
						}
					}
				}
			});
		}
	}
	
	@Override
	public boolean handlesRelation() {
		return true;
	}
	
	public void setValueRange(List<Relation> valueRange){
		listBox.clear();
		itemIdIndexMap.clear();
		
		if (allowsNull) addNullItem();
		int index = allowsNull ? 1 : 0;
		
		if(valueRange!=null) {
			for (Relation relation : valueRange) {
				listBox.addItem(relation.getDisplayName(), relation.getId().toString());
				itemIdIndexMap.put(relation.getId(), index++);
			}
		}
		
		setRelationValue(value);
	}

	

	@Override
	public void setRelationValue(Relation value) {
		this.value = value;
		
		if (value == null) {
			if (allowsNull) {
				listBox.setSelectedIndex(0);
			}
		} else {
			if(itemIdIndexMap.containsKey(value.getId()))
				listBox.setSelectedIndex(itemIdIndexMap.get(value.getId()));
		}
	}
	
	@Override
	public Relation getRelationValue() {
		return value;
	}

	private void loadDataFromValueRangeProvider(){
		if (valueRangeProvider != null) {
			listBox.setEnabled(false);
			valueRangeProvider.getRelationValueRange(fieldDescriptor, new ValueRangeResultCallback() {
				@Override
				public void onValueRangeResultReady(List<Relation> relationList) {
					if (relationList != null){
						listBox.setEnabled(true);
						ListBoxFW.this.setValueRange(relationList);
					}
				}
			});
		}
	}	
}
