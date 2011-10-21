package com.inepex.ineForm.client.form.widgets.customkvo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.user.client.ui.IsWidget;
import com.inepex.ineForm.client.general.ErrorMessageManagerInterface;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.util.SharedUtil;

public class CustomKVOFW extends CustomKVOFWBase implements AddCallback, RemoveCallback, RowValueChangeCallback {
	
	public static interface View extends IsWidget {
		public void setRemoveCallback(RemoveCallback removeCallback);
		public void setRowValueChangeCallback(RowValueChangeCallback rowValueChangeCallback);
		public void setAddCallback(AddCallback addCallback);
	
		public void clearRows();
		public void addRow(CustomKVORow r);
		public void removeRow(CustomKVORow r);
		
		public void dealResult(Map<Long, String> res);
		public ErrorMessageManagerInterface getErrorManager(CustomKVORow r);
		
		public void showReadOnly(List<CustomKVORow> rows);
		public void showEditable();
	}
	
	private final View view;
	
	private boolean enabled = true;
	
	public CustomKVOFW(RelationFDesc fieldDescriptor, WidgetRDesc widgetRDesc, OdFinder odFinder, View view) {
		super(fieldDescriptor, widgetRDesc, odFinder);
		
		if(!IFConsts.customDescriptorName.equals(fieldDescriptor.getRelatedDescriptorName()))
			throw new IllegalArgumentException();
		
		this.view = view;
		this.view.setAddCallback(this);
		this.view.setRemoveCallback(this);
		this.view.setRowValueChangeCallback(this);
		
		initWidget(view.asWidget());
		view.showEditable();
	}
	
	@Override
	protected void beforeRelationParsed() {
		view.clearRows();
	}

	@Override
	protected void onRelationParsed() {
		for(CustomKVORow r : rows) {
			view.addRow(r);
		}
		if (!enabled) view.showReadOnly(rows);
		else view.showEditable();
		
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		view.showReadOnly(rows);
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
	public Relation getRelationValue(){
		AssistedObject ao = ODAOCustomKVOMappingHelper.getAoFromRows(rows);
		if(ao==null) {
			relation=null;
			return null;
		}
		
		if(relation!=null) {
			ao.setId(relation.getId());
		} else {
			relation= new Relation();
			ao.setId(IFConsts.NEW_ITEM_ID);
		}
		
		relation.setKvo(ao);
		
		return relation;
	}

	/**
	 * @return false if it's in inconsistent state
	 */
	public boolean validateConsistence() {
		Map<Long, String> res = ODAOCustomKVOMappingHelper.validateRows(rows);
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
