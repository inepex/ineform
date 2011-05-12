package com.inepex.ineForm.client.form.widgets;

import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.kvo.IneList;
import com.inepex.ineom.shared.kvo.Relation;

/**
 * A {@link FormWidget} child that overrides all abstract classes, and does not support
 * setting any kind of data.
 * 
 * This is very useful, for a superclass of a {@link FormWidget}, that only supports one kind of data.
 * @author istvan
 *
 */
public abstract class DenyingFormWidget extends FormWidget {
	
	public DenyingFormWidget(FDesc fielddescriptor) {
		super(fielddescriptor);
	}

	@Override
	public Boolean getBooleanValue() {
		return null;
	}

	@Override
	public Double getDoubleValue() {
		return null;
	}

	@Override
	public IneList getListValue() {
		return null;
	}

	@Override
	public Long getLongValue() {
		return null;
	}

	@Override
	public Relation getRelationValue() {
		return null;
	}

	@Override
	public String getStringValue() {
		return null;
	}

	@Override
	public boolean handlesBoolean() {
		return false;
	}

	@Override
	public boolean handlesDouble() {
		return false;
	}

	@Override
	public boolean handlesList() {
		return false;
	}

	@Override
	public boolean handlesLong() {
		return false;
	}

	@Override
	public boolean handlesRelation() {
		return false;
	}

	@Override
	public boolean handlesString() {
		return false;
	}

	@Override
	public boolean isFocusable() {
		return false;
	}

	@Override
	public void setBooleanValue(Boolean value) {

	}

	@Override
	public void setDoubleValue(Double value) {

	}

	@Override
	public void setEnabled(boolean enabled) {

	}

	@Override
	public void setFocus(boolean focused) {

	}

	@Override
	public void setListValue(IneList value) {

	}

	@Override
	public void setLongValue(Long value) {

	}

	@Override
	public void setRelationValue(Relation value) {

	}

	@Override
	public void setStringValue(String value) {

	}
	
	@Override
	public boolean isReadOnlyWidget() {
		return false;
	}

}
