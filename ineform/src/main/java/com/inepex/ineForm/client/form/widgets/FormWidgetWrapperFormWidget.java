package com.inepex.ineForm.client.form.widgets;

import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeEvent;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeHandler;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;

/**
 * overriding this class is the easiest way to wrap a formWidget
 * and give to it additional functionality
 *
 */
public class FormWidgetWrapperFormWidget extends FormWidget{

	private final FormWidget wrappedFW;
	
	public FormWidgetWrapperFormWidget(FormWidget wrappedFormWidget) {
		super(wrappedFormWidget.getFieldDescriptor());
		this.wrappedFW=wrappedFormWidget;
	}
	
	public FormWidget getWrappedFW() {
		return wrappedFW;
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		registerHandler(wrappedFW.addFormWidgetChangeHandler(new FormWidgetChangeHandler() {
			
			@Override
			public void onFormWidgetChange(FormWidgetChangeEvent e) {
				fireFormWidgetChanged();
			}
		}));
	}

	@Override
	public void setEnabled(boolean enabled) {
		wrappedFW.setEnabled(enabled);
	}

	@Override
	public boolean isFocusable() {
		return wrappedFW.isFocusable();
	}

	@Override
	public void setFocus(boolean focused) {
		wrappedFW.setFocus(focused);
	}

	@Override
	public boolean handlesBoolean() {
		return wrappedFW.handlesBoolean();
	}

	@Override
	public void setBooleanValue(Boolean value) {
		wrappedFW.setBooleanValue(value);
	}

	@Override
	public Boolean getBooleanValue() {
		return wrappedFW.getBooleanValue();
	}

	@Override
	public boolean handlesDouble() {
		return wrappedFW.handlesDouble();
	}

	@Override
	public void setDoubleValue(Double value) {
		wrappedFW.setDoubleValue(value);
	}

	@Override
	public Double getDoubleValue() {
		return wrappedFW.getDoubleValue();
	}

	@Override
	public boolean handlesList() {
		return wrappedFW.handlesList();
	}

	@Override
	public void setListValue(IneList value) {
		wrappedFW.setListValue(value);
	}

	@Override
	public IneList getListValue() {
		return wrappedFW.getListValue();
	}

	@Override
	public boolean handlesLong() {
		return wrappedFW.handlesLong();
	}

	@Override
	public void setLongValue(Long value) {
		wrappedFW.setLongValue(value);
	}

	@Override
	public Long getLongValue() {
		return wrappedFW.getLongValue();
	}

	@Override
	public boolean handlesRelation() {
		return wrappedFW.handlesRelation();
	}

	@Override
	public void setRelationValue(Relation value) {
		wrappedFW.setRelationValue(value);
	}

	@Override
	public Relation getRelationValue() {
		return wrappedFW.getRelationValue();
	}

	@Override
	public boolean handlesString() {
		return wrappedFW.handlesString();
	}

	@Override
	public void setStringValue(String value) {
		wrappedFW.setStringValue(value);
	}

	@Override
	public String getStringValue() {
		return wrappedFW.getStringValue();
	}
	
	@Override
	public boolean isReadOnlyWidget() {
		return wrappedFW.isReadOnlyWidget();
	}

}
