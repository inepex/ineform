package com.inepex.ineForm.client.form.widgets;

import java.util.Map;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeEvent;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeHandler;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public abstract class FormWidget extends HandlerAwareComposite {
		
	protected Widget mainWidget;
	protected boolean shouldRender = true;
	protected final FDesc fieldDescriptor;
		
	protected Map<String,Object> originalValues;
	
	public FormWidget(FDesc fieldDescriptor) {
		
		this.fieldDescriptor=fieldDescriptor;
	}
	
	@Override
	protected void initWidget(Widget widget) {
		
		this.mainWidget = widget;
		super.initWidget(widget);
		if (fieldDescriptor != null)
			getElement().setId(IFConsts.FWIDPREFIX + fieldDescriptor.getKey());
	}
	
	@Override
	public void setWidth(String width) {
		mainWidget.setWidth(width);
	}
	
	public abstract void setEnabled(boolean enabled);
	
	public HandlerRegistration addFormWidgetChangeHandler(FormWidgetChangeHandler h) {
		return addHandler(h, FormWidgetChangeEvent.TYPE);
	}
	
	protected void fireFormWidgetChanged() {
		fireEvent(new FormWidgetChangeEvent(this));
	}

	public abstract boolean isFocusable();

	public abstract void setFocus(boolean focused);
	
	/**
	 * Returns if the widget should be rendered to the form. If you don't want your derived widget to be
	 * rendered, than just set shouldRender field of the class to false
	 * 
	 * @return
	 */
	public boolean isShouldRender() {
		return shouldRender;
	}
	

	public FDesc getFieldDescriptor() {
		return fieldDescriptor;
	}

	//********************** data flow
	public abstract boolean isReadOnlyWidget();
	
	public abstract boolean handlesBoolean();
	public abstract void setBooleanValue(Boolean value);
	public abstract Boolean getBooleanValue();

	public abstract boolean handlesDouble();
	public abstract void setDoubleValue(Double value);
	public abstract Double getDoubleValue();

	public abstract boolean handlesList();
	public abstract void setListValue(IneList value);
	public abstract IneList getListValue();

	public abstract boolean handlesLong();
	public abstract void setLongValue(Long value);
	public abstract Long getLongValue();

	public abstract boolean handlesRelation();
	public abstract void setRelationValue(Relation value);
	public abstract Relation getRelationValue();

	public abstract boolean handlesString();
	public abstract void setStringValue(String value);
	public abstract String getStringValue();
}
