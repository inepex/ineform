package com.inepex.ineForm.client.form.widgets.customkvo;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.DenyingFormWidget;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;

public class CustomKVOFW extends DenyingFormWidget implements AddCallback, RemoveCallback, RowValueChangeCallback {
	
	public static interface View extends IsWidget {
		public void setRemoveCallback(RemoveCallback removeCallback);
		public void setRowValueChangeCallback(RowValueChangeCallback rowValueChangeCallback);
		public void setAddCallback(AddCallback addCallback);
	
		public void clearRows();
		public void addRow(CustomKVORow r);
		public void removeRow(CustomKVORow r);
	}
	
	private final DescriptorStore descStore;
	
	private final CustomKVOFWView view;
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
		
		initWidget(view);
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
	
	/**
	 * TODO check
	 * 
	 */
	@Override
	public void setRelationValue(Relation value) {
		if(!IFConsts.customDescriptorName.equals(value.getKvo().getDescriptorName()))
			throw new IllegalArgumentException();
		
		if(value.getId()==null)
			throw new IllegalArgumentException();
		
		this.relation=value;
		
		ObjectDesc od = descStore.getCustomOd(value.getId());
		//TODO do we need od null check?
		AssistedObject ao = value.getKvo();
		//TODO do we need ao null ckeck 
		
		//clearing previous state
		for(CustomKVORow r : rows) 
			view.removeRow(r);
		
		rows.clear();
		
		//adding new rows
		for(CustomKVORow r : ODAOCustomKVOMappingHelper.getRowsFromAoAndOd(ao, od)) {
			rows.add(r);
			view.addRow(r);
		}
	}
	
	@Override
	public Relation getRelationValue(){
		relation.setKvo(ODAOCustomKVOMappingHelper.getAoFromRows(rows));
		
		return relation;
	}
}
