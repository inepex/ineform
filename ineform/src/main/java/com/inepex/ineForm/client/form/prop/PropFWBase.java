package com.inepex.ineForm.client.form.prop;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.inepex.ineForm.client.form.widgets.DenyingFormWidget;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.PropFieldType;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.fdesc.PropFDesc;

public abstract class PropFWBase extends DenyingFormWidget{
	
	public static String showHeader = "showHeader";
	public static String showType = "showType";

	protected Relation relation = null;
	protected final List<String> originalKeys = new ArrayList<String>();
	protected final List<PropRow> rows = new ArrayList<PropRow>();
	
	protected boolean isShowHeader = true;
	protected boolean isShowType = false;
	
	public PropFWBase(PropFDesc fieldDescriptor, WidgetRDesc widgetRDesc) {
		super(fieldDescriptor);
		
		if (widgetRDesc.hasProp(showHeader)) isShowHeader = Boolean.parseBoolean(widgetRDesc.getPropValue(showHeader));
		if (widgetRDesc.hasProp(showType)) isShowType = Boolean.parseBoolean(widgetRDesc.getPropValue(showType));

	}
	
	protected abstract void beforeParsed();
	protected abstract void onParsed();
	
	@Override
	public void setStringValue(String value) {
		beforeParsed();
		rows.clear();
		if (value != null && !value.equals("")){
			JSONObject propJson = JSONParser.parseStrict(value).isObject();
			if (propJson != null){
				for (String key : propJson.keySet()){
					PropFieldType type = PropFieldType.STRING;
					String rowValue = "";
					if (propJson.get(key).isBoolean() != null){
						type = PropFieldType.BOOLEAN;
						rowValue = "" + propJson.get(key).isBoolean().booleanValue();
					} else if (propJson.get(key).isNumber() != null){
						type = PropFieldType.DOUBLE;
						rowValue = "" + propJson.get(key).isNumber().doubleValue();
					} else if (propJson.get(key).isString() != null){
						type = PropFieldType.STRING;
						rowValue = propJson.get(key).isString().stringValue();
					}
					
					PropRow row = new PropRow(key, type, rowValue);
					originalKeys.add(key);
					rows.add(row);
				}
			}
		}
		onParsed();

	}
	
	@Override
	public boolean handlesString() {
		return true;
	}
	
	
}
