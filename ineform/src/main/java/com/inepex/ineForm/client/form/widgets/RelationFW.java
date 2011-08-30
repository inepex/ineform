package com.inepex.ineForm.client.form.widgets;

import java.util.Set;
import java.util.TreeSet;

import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeEvent;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeHandler;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.util.SharedUtil;

public class RelationFW extends DenyingFormWidget {
	
	private IneForm form = null;
	private Relation relation = null;
	final private RelationFDesc fieldDescriptor;
	
	public RelationFW(FormContext formCtx, RelationFDesc fieldDescriptor) {
		super(fieldDescriptor);
		this.fieldDescriptor = fieldDescriptor;
		
		String relatedDescriptorName = fieldDescriptor.getRelatedDescriptorName();
		form = new IneForm(formCtx, relatedDescriptorName, null);
		form.renderForm();
		
		initWidget(form.asWidget());
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		for(AbstractFormUnit innerFormUnits : form.getRootPanelWidget().getFormUnits()) {
			registerHandler(innerFormUnits.addFormWidgetChangeHandler(new FormWidgetChangeHandler() {
				@Override
				public void onFormWidgetChange(FormWidgetChangeEvent e) {
					fireFormWidgetChanged();
				}
			}));
		}
	}
	
	@Override
	public boolean handlesRelation() {
		return true;
	}
	
	@Override
	public void setRelationValue(Relation value) {
		if (value == null)
			return;
		this.relation = value;
		if (relation.getKvo() != null)
			form.setInitialData(relation.getKvo());
	}
	
	@Override
	public Relation getRelationValue(){
		AssistedObject kvo;
		if (relation != null && relation.getKvo() != null)
			kvo = relation.getKvo().clone();
		else
			kvo = new KeyValueObject(fieldDescriptor.getRelatedDescriptorName());
		
		form.getValues(kvo);
		
		return new Relation(kvo.getId(), "", kvo);
	}
	
	public Set<String> getErrorManagerKeySet(String prefix) {	
		Set<String> keys= new TreeSet<String>();
		for(AbstractFormUnit unit : form.getRootPanelWidget().getFormUnits()) {
			for(String key : unit.getErrorManagerKeySet())
				keys.add(prefix+SharedUtil.ID_PART_SEPARATOR+key);
		}
		
		return keys;
	}
}
