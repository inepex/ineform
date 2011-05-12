package com.inepex.ineForm.client.form.widgets.chooser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.kvo.IFConsts;
import com.inepex.ineom.shared.kvo.IneT;
import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.Relation;

public class EnumChooser implements Chooser {
	
	private Comparator<Item> itemNameComparator = new Comparator<Item>() {

		@Override
		public int compare(Item o1, Item o2) {
			return o1.getDisplayName().compareTo(o2.getDisplayName());
		}
	};
	
	private Comparator<Item> relationOrderComparator = new Comparator<Item>() {

		@Override
		public int compare(Item o1, Item o2) {
			if (supportsOrdering) {
				Relation mappedRel1 = relationToItemId.get(o1.getId());
				Relation mappedRel2 = relationToItemId.get(o2.getId());
				return mappedRel1.getKvo().getLong(IFConsts.KEY_ORDERNUM)
					.compareTo(mappedRel2.getKvo().getLong(IFConsts.KEY_ORDERNUM));
			}
				
			else return o1.getDisplayName().compareTo(o2.getDisplayName());
		}
	};
	
	boolean supportsOrdering;
	ChooserFw chooserFw;	
	FDesc fieldDescriptor;
	WidgetRDesc widgetRDesc;
	String relationDescriptorName;
	String secondLevelJoin;	
	boolean isEnum = true;
	Map<String, Item> valueRange = new HashMap<String, Item>();
	Map<String, Item> selected = new HashMap<String, Item>();
	Map<String, Relation> relationToItemId = new HashMap<String, Relation>();
	List<Relation> changed = new ArrayList<Relation>();
	
	List<Item> valueRangeOrdered = new LinkedList<Item>();
	List<Item> selectedOrdered = new LinkedList<Item>();
	
	private String[] values;
	
	final DescriptorStore descStore;
	
	public EnumChooser(DescriptorStore descStore
			, ChooserFw chooserFw
			, FDesc fielddescriptor
			, WidgetRDesc widgetRDesc
			, String relationDescriptorName) {
		this.chooserFw = chooserFw;
		this.fieldDescriptor = fielddescriptor;
		this.widgetRDesc = widgetRDesc;
		this.relationDescriptorName = relationDescriptorName;
		this.descStore = descStore;
		
		supportsOrdering = descStore.getOD(relationDescriptorName).containsKey(IFConsts.KEY_ORDERNUM);
		
		secondLevelJoin = fieldDescriptor.getPropValue("secondLevelJoin");
		
		values = widgetRDesc.getPropValue(IFConsts.enumValues).split(IFConsts.enumValueSplitChar);
		
		peelValueRange();
		updateOrders();
		sortLists();
	}
	
	public void setValues(String[] values) {
		this.values = values;
		resetState();
	}
	
	public void resetState(){
		valueRange.clear();
		valueRangeOrdered.clear();
		selected.clear();
		selectedOrdered.clear();
		changed.clear();
		peelValueRange();
	}
	
	private void peelValueRange(){
		FDesc relFieldDesc = descStore.getOD(relationDescriptorName)
		.getField(secondLevelJoin);
		
		if (relFieldDesc.getType() == IneT.LONG){ // it is an enum
			isEnum = true;
			int num = 0;
			for (String value : values){
				Item item = new Item(num++, value);
				valueRange.put(item.getId(), item);
			}			 
		} else if (relFieldDesc.getType() == IneT.STRING) {
			isEnum = false;
			for (String value : values){
				Item item = new Item(value);
				valueRange.put(item.getId(), item);
			}	
		}
		
	}

	@Override
	public void setSelected(List<Relation> selected) {
		resetState();
		for (Relation rel : selected){
			Item item = null;
			if (isEnum) {
				item = valueRange.get("" + rel.getKvo().getLong(secondLevelJoin));
			} else {
				item = valueRange.get(rel.getKvo().getString(secondLevelJoin));
			}
			relationToItemId.put(item.getId(), rel);
			select(item, false, false);
		}
		updateOrders();
		sortLists();
		chooserFw.reRender();
	}

	@Override
	public void select(Item item, boolean reRender, boolean isChange) {
		valueRange.remove(item.getId());
		selected.put(item.getId(), item);
		selectedOrdered.add(selectedOrdered.size(), item);
		
		if (isChange) {
			Relation rel = new Relation();
			rel.setId(IFConsts.NEW_ITEM_ID);
			KeyValueObject kvo = new KeyValueObject(relationDescriptorName);
			if (isEnum) {
				kvo.set(secondLevelJoin, Long.valueOf(item.getId()));
			} else {
				kvo.set(secondLevelJoin, item.getId());
			}		
			rel.setKvo(kvo);
		
			relationToItemId.put(item.getId(), rel);
			changed.add(rel);
		}
		
		if (reRender){
			updateOrders();
			sortLists();
			chooserFw.reRender();
		}
		
	}

	@Override
	public void deselect(Item item, boolean reRender) {
		selected.remove(item.getId());
		valueRange.put(item.getId(), item);
		selectedOrdered.remove(item);
		
		Relation parentRel = relationToItemId.get(item.getId());
		if (changed.contains(parentRel)){
			if (parentRel.getId().longValue() == IFConsts.NEW_ITEM_ID.longValue()){
				changed.remove(parentRel);
			} else {
				parentRel.setKvo(null);
			}
		} else {
			changed.add(parentRel);
			parentRel.setKvo(null);
		}
		relationToItemId.remove(item.getId());
		
		if (reRender){
			updateOrders();
			sortLists();
			chooserFw.reRender();
		}
	}

	@Override
	public void selectAll() {
		List<Item> tmpList = new ArrayList<Item>();
		tmpList.addAll(valueRange.values());
		for (Item toSelect : tmpList){
			select(toSelect, false, true);
		}
		updateOrders();
		sortLists();
		chooserFw.reRender();
	}

	@Override
	public void deselectAll() {
		List<Item> tmpList = new ArrayList<Item>();
		tmpList.addAll(selected.values());
		for (Item toDeselect : tmpList){
			deselect(toDeselect, false);
		}
		updateOrders();
		sortLists();
		chooserFw.reRender();
	}

	@Override
	public List<Item> getValueRange() {
		return valueRangeOrdered;
	}

	@Override
	public List<Item> getSelected() {
		return selectedOrdered;
	}

	@Override
	public List<Relation> getChanged() {
		return changed;
	}

	@Override
	public void moveUp(Item item) {
		int oldIndex = selectedOrdered.indexOf(item);
		if (oldIndex > 0){
			selectedOrdered.remove(item);
			selectedOrdered.add(oldIndex-1, item);
			updateOrders();
			chooserFw.reRender();
		}
	}

	@Override
	public void moveDown(Item item) {
		int oldIndex = selectedOrdered.indexOf(item);
		if (oldIndex < selectedOrdered.size()-1){
			selectedOrdered.remove(item);
			selectedOrdered.add(oldIndex+1, item);
			updateOrders();
			chooserFw.reRender();
		}
	}
	
	private void sortLists(){
		selectedOrdered.clear();
		selectedOrdered.addAll(selected.values());
		Collections.sort(selectedOrdered, relationOrderComparator);
		valueRangeOrdered.clear();
		valueRangeOrdered.addAll(valueRange.values());
		Collections.sort(valueRangeOrdered, itemNameComparator);
	}
	
	private void updateOrders(){
		if (supportsOrdering){
			for (int i = 0; i < selectedOrdered.size(); i++){
				Relation mappedRel = relationToItemId.get(selectedOrdered.get(i).getId());
				if (mappedRel.getKvo() != null){					
					Long prevValue = mappedRel.getKvo()
							.getLong(IFConsts.KEY_ORDERNUM);
					mappedRel.getKvo()
						.set(IFConsts.KEY_ORDERNUM, new Long(i));					
					
					if (!changed.contains(mappedRel)
							&& (prevValue == null || prevValue.longValue() != new Long(i).longValue()) 
					) changed.add(mappedRel);
				}
			}
		}
	}

	@Override
	public boolean isSupportsOrdering() {
		return supportsOrdering;
	}

}
