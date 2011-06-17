package com.inepex.ineForm.client.form.widgets.chooser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.datamanipulator.ValueRangeResultCallback;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.kvo.IFConsts;
import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.Relation;

public class RelationChooser implements Chooser {
	private boolean supportsOrdering;
	
	private ChooserView chooserView;	
	private Map<String, Item> valueRange = new TreeMap<String, Item>();
	private List<Item> valueRangeList = new ArrayList<Item>();
	private LinkedList<Item> selected = new LinkedList<Item>();
	private List<Relation> changed = new ArrayList<Relation>();
	private ValueRangeProvider valueRangeProvider;
	private FDesc fieldDescriptor;
	private String relationDescriptorName;
	private String secondLevelJoin;	
	private Map<String, Relation> relationToItemId = new HashMap<String, Relation>();
	
	final DescriptorStore descStore;
	
	private Comparator<Item> relationNameComparator = new Comparator<Item>() {

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
	
	
	public RelationChooser(FormContext formCtx
			, ChooserView chooserView
			, FDesc fielddescriptor
			, String relationDescriptorName) {
		this.chooserView = chooserView;
		this.valueRangeProvider = formCtx.valueRangeProvider;
		this.fieldDescriptor = fielddescriptor;
		this.relationDescriptorName = relationDescriptorName;
		this.descStore = formCtx.descStore;
		
		supportsOrdering = descStore.getOD(relationDescriptorName).containsKey(IFConsts.KEY_ORDERNUM);
		
		secondLevelJoin = fieldDescriptor.getPropValue("secondLevelJoin");
		
		chooserView.setEnabled(false);
		resetState();		
	}
	
	public void resetState(){
		chooserView.setEnabled(false);
		valueRange.clear();
		valueRangeList.clear();
		selected.clear();
		changed.clear();
		relationToItemId.clear();
		loadValueRange();
	}
	
	public void loadValueRange(){
		FDesc relFieldDesc = descStore.getOD(relationDescriptorName)
			.getField(secondLevelJoin);
		
		valueRangeProvider.getRelationValueRange(relFieldDesc, new ValueRangeResultCallback() {
			
			@Override
			public void onValueRangeResultReady(List<Relation> relationList) {
				List<Item> itemList = new ArrayList<Item>();
				for (Relation rel : relationList){
					itemList.add(new Item(rel));
				}
				setValueRange(itemList);
			}
		});
	}
	
	public void setValueRange(List<Item> itemList){
		for (Item item : itemList){
			valueRange.put(item.getId(), item);
		}
		filterSelectedFromValuesAndEnableWidget();
	}
	
	public void setSelected(List<Relation> relationList){
		resetState();
		for (Relation rel : relationList){
			Relation mappedRel = rel.getKvo().getRelation(secondLevelJoin);
			Item item = new Item(mappedRel);
			selected.add(item);
			relationToItemId.put(item.getId(), rel);
		}
		
		filterSelectedFromValuesAndEnableWidget();
	}
	
	private boolean checkDataLoaded(){
		if (valueRange != null && selected != null){
			return true;
		} else return false;
	}
	
	private void filterSelectedFromValuesAndEnableWidget(){
		if (checkDataLoaded() && selected.size() > 0){
			for (Item selectedItem : selected){
				valueRange.remove(selectedItem.getId());
			}
		}
		
		if (checkDataLoaded()){
			chooserView.setEnabled(true);
			sortLists();
		}
		
		chooserView.reRender();
	}
	
	public void select(Item item, boolean reRender, boolean isChange){
		selected.addLast(item);
		valueRange.remove(item.getId());
		
		Relation parentRel = new Relation();
		parentRel.setId(IFConsts.NEW_ITEM_ID);
		KeyValueObject kvo = new KeyValueObject(relationDescriptorName);
		kvo.setId(IFConsts.NEW_ITEM_ID);
		kvo.set(secondLevelJoin, (Relation)item.getO());
		parentRel.setKvo(kvo);
		relationToItemId.put(item.getId(), parentRel);
		changed.add(parentRel);
		
		if (reRender){
			updateOrders();
			sortLists();
			chooserView.reRender();
		}
	}
	
	public void deselect(Item rel, boolean reRender){
		selected.remove(rel);
		valueRange.put(rel.getId(), rel);
		
		Relation parentRel = relationToItemId.get(rel.getId());
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
		relationToItemId.remove(rel.getId());
		
		if (reRender){
			updateOrders();
			sortLists();
			chooserView.reRender();
		}
	}
	
	public void selectAll(){
		List<Item> tmpList = new ArrayList<Item>();
		tmpList.addAll(valueRange.values());
		for (Item toSelect : tmpList){
			select(toSelect, false, true);
		}
		updateOrders();
		sortLists();
		chooserView.reRender();
	}
	
	public void deselectAll(){
		List<Item> tmpList = new ArrayList<Item>();
		tmpList.addAll(selected);
		for (Item toDeselect : tmpList){
			deselect(toDeselect, false);
		}
		updateOrders();
		sortLists();
		chooserView.reRender();
	}
	
	private void sortLists(){
		Collections.sort(selected, relationOrderComparator);
		valueRangeList.clear();
		valueRangeList.addAll(valueRange.values());
		Collections.sort(valueRangeList, relationNameComparator);
	}
	

	public List<Item> getValueRange() {
		return valueRangeList;
	}

	public List<Item> getSelected() {
		return selected;
	}

	public List<Relation> getChanged() {
		return changed;
	}
	
	public void moveUp(Item rel){
		int oldIndex = selected.indexOf(rel);
		if (oldIndex > 0){
			selected.remove(rel);
			selected.add(oldIndex-1, rel);
			updateOrders();
			chooserView.reRender();
		}
	}
	
	public void moveDown(Item rel){
		int oldIndex = selected.indexOf(rel);
		if (oldIndex < selected.size()-1){
			selected.remove(rel);
			selected.add(oldIndex+1, rel);
			updateOrders();
			chooserView.reRender();
		}
	}
	
	private void updateOrders(){
		if (supportsOrdering){
			for (int i = 0; i < selected.size(); i++){
				Relation mappedRel = relationToItemId.get(selected.get(i).getId());
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
