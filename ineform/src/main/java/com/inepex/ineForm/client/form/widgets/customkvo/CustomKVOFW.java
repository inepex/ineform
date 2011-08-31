package com.inepex.ineForm.client.form.widgets.customkvo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.user.client.ui.IsWidget;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.DenyingFormWidget;
import com.inepex.ineForm.client.general.ErrorMessageManagerInterface;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.util.SharedUtil;

public class CustomKVOFW extends DenyingFormWidget implements AddCallback, RemoveCallback, RowValueChangeCallback {
	
	public static interface View extends IsWidget {
		public void setRemoveCallback(RemoveCallback removeCallback);
		public void setRowValueChangeCallback(RowValueChangeCallback rowValueChangeCallback);
		public void setAddCallback(AddCallback addCallback);
	
		public void clearRows();
		public void addRow(CustomKVORow r);
		public void removeRow(CustomKVORow r);
		
		public void dealResult(Map<Integer, String> res);
		public ErrorMessageManagerInterface getErrorManager(CustomKVORow r);
	}
	
	private final DescriptorStore descStore;
	
	private final View view;
	private final List<CustomKVORow> rows = new ArrayList<CustomKVORow>();
	
	private Relation relation = null;
	
	public CustomKVOFW(FormContext formCtx, RelationFDesc fieldDescriptor, DescriptorStore ds) {
		super(fieldDescriptor);
		this.descStore=ds;
		
		if(!IFConsts.customDescriptorName.equals(fieldDescriptor.getRelatedDescriptorName()))
			throw new IllegalArgumentException();
		
		view = new CustomKVOFWView();
		view.setAddCallback(this);
		view.setRemoveCallback(this);
		view.setRowValueChangeCallback(this);
		
		initWidget(view.asWidget());
	}

	@Override
	public void onRowValueChanged() {
		fireFormWidgetChanged();
	}

	@Override
	public void onRemove(CustomKVORow r) {
		fireFormWidgetChanged();
		rows.remove(r);
		view.removeRow(r);
		fireFormWidgetChanged();
	}

	@Override
	public void onAdd() {
		fireFormWidgetChanged();
		CustomKVORow newRow = new CustomKVORow();
		rows.add(newRow);
		view.addRow(newRow);
		fireFormWidgetChanged();
	}
	
	@Override
	public boolean handlesRelation() {
		return true;
	}
	
	@Override
	public void setRelationValue(Relation value) {
		if(value==null)
			value = new Relation();
		
		if(value.getKvo()==null) {
			value.setKvo(new KeyValueObject(IFConsts.customDescriptorName));
		} else {
			if(!IFConsts.customDescriptorName.equals(value.getKvo().getDescriptorName()))
				throw new IllegalArgumentException();
		}
		
		this.relation=value;

		//clearing previous state
		for(CustomKVORow r : rows) 
			view.removeRow(r);
		
		rows.clear();
		
		//TODO do we need ao null ckeck
		final AssistedObject ao = value.getKvo();
		
		descStore.getCustomOd(value.getId(), new DescriptorStore.OdFoundCallback() {
			
			@Override
			public void onFound(ObjectDesc od) {
				//adding new rows
				for(CustomKVORow r : ODAOCustomKVOMappingHelper.getRowsFromAoAndOd(ao, od)) {
					rows.add(r);
					view.addRow(r);
				}
			}
		});
	}
	
	@Override
	public Relation getRelationValue(){
		AssistedObject ao = ODAOCustomKVOMappingHelper.getAoFromRows(rows);
		ao.setId(relation.getId());
		relation.setKvo(ao);
		
		return relation;
	}
	
	/**
	 * @return false if it's in inconsistent state
	 */
	public boolean validateConsistence() {
		Map<Integer, String> res = ODAOCustomKVOMappingHelper.validateRows(rows);
		view.dealResult(res);
		
		return res.isEmpty();
	}
	
	public CustomKVOObjectDesc getOdFromRows() {
		CustomKVOObjectDesc od = ODAOCustomKVOMappingHelper.getOdFromRows(rows);
		od.setKey(fieldDescriptor.getKey());
		return od;
	}

	public Collection<? extends String> getModelKeys(String prefix) {
		ArrayList<String> rowKeys = new  ArrayList<String>();
		for(CustomKVORow r : rows)
			rowKeys.add(prefix+SharedUtil.ID_PART_SEPARATOR+r.getKey());
		
		return rowKeys;
	}

	public TreeMap<String, ErrorMessageManagerInterface> getErrorManagers(String prefix) {
		TreeMap<String, ErrorMessageManagerInterface> ret = new TreeMap<String, ErrorMessageManagerInterface>();
		for(CustomKVORow r : rows)
			ret.put(prefix+SharedUtil.ID_PART_SEPARATOR+r.getKey(),
					view.getErrorManager(r));
		
		return ret;
	}
}
