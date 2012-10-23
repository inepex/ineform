package com.inepex.ineForm.client.form.widgets.customkvo;

import java.util.ArrayList;
import java.util.List;

import com.inepex.ineForm.client.form.widgets.DenyingFormWidget;
import com.inepex.ineForm.shared.customkvoeditor.CustomOdFinder;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;

public abstract class CustomKVOFWBase extends DenyingFormWidget{
	
	public static String showHeader = "showHeader";
	public static String showType = "showType";

	protected Relation relation = null;
	private final CustomOdFinder odFinder;
	protected final List<CustomKVORow> rows = new ArrayList<CustomKVORow>();
	
	protected boolean isShowHeader = true;
	protected boolean isShowType = false;
	
	public CustomKVOFWBase(RelationFDesc fieldDescriptor, WidgetRDesc widgetRDesc, CustomOdFinder odFinder) {
		super(fieldDescriptor);
		this.odFinder = odFinder;
		
		if (widgetRDesc.hasProp(showHeader)) isShowHeader = Boolean.parseBoolean(widgetRDesc.getPropValue(showHeader));
		if (widgetRDesc.hasProp(showType)) isShowType = Boolean.parseBoolean(widgetRDesc.getPropValue(showType));

	}
	
	protected abstract void beforeRelationParsed();
	protected abstract void onRelationParsed();
	
	@Override
	public void setRelationValue(Relation value) {
		beforeRelationParsed();
		//clearing previous state 
		rows.clear();
		
		if(value!=null) {
			relation = new Relation(value.getId(), value.getDisplayName(), value.getKvo());
			
			if(value.getKvo()!=null) {
				if(!IFConsts.customDescriptorName.equals(value.getKvo().getDescriptorName()))
					throw new IllegalArgumentException();
			}
			
		} else {
			relation = null;
			onRelationParsed();
			return;
		}
		
		
		final AssistedObject ao = value.getKvo();
		
		odFinder.getCustomOd(value.getId(), new CustomOdFinder.OdFoundCallback() {
			
			@Override
			public void onFound(ObjectDesc od) {
				//clearing again to avoid "fast-reload caused row duplications"
				rows.clear();
				
				//adding new rows
				for(CustomKVORow r : ODAOCustomKVOMappingHelper.getRowsFromAoAndOd(ao, od)) {
					rows.add(r);
				}
				onRelationParsed();
			}
		});
		

	}
	
	@Override
	public boolean handlesRelation() {
		return true;
	}
	
	
}
