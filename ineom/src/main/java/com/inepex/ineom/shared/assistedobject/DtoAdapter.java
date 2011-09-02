package com.inepex.ineom.shared.assistedobject;

import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;

@SuppressWarnings("serial")
public abstract class DtoAdapter extends AssistedObject {
	
	@Override
	public abstract DtoAdapter clone();
	
	protected abstract void setObject(String key, Object o);
	protected abstract Object getObject(String key);
	
	@Override
	protected void set(String key, Boolean value) {
		setObject(key, value);
	}

	@Override
	protected void set(String key, Double value) {
		setObject(key, value);
	}

	@Override
	protected void set(String key, IneList value) {
		setObject(key, value);
	}

	@Override
	protected void set(String key, Long value) {
		setObject(key, value);
	}

	@Override
	protected void set(String key, Relation value) {
		setObject(key, value);
	}

	@Override
	protected void set(String key, String value) {
		setObject(key, value);
	}

	@Override
	protected Boolean getBoolean(String key) {
		return (Boolean) getObject(key);
	}

	@Override
	protected Double getDouble(String key) {
		return (Double) getObject(key);
	}

	@Override
	protected IneList getList(String key) {
		return (IneList) getObject(key);
	}

	@Override
	protected Long getLong(String key) {
		return (Long) getObject(key);
	}

	@Override
	protected Relation getRelation(String key) {
		return (Relation) getObject(key);
	}

	@Override
	protected String getString(String key) {
		return (String) getObject(key);
	}

	@Override
	public void setUnchecked(String key, Long value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUnchecked(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long getLongUnchecked(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getStringUnchecked(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean containsString(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean containsBoolean(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean containsDouble(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean containsList(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean containsLong(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean containsRelation(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void copyValuesTo(AssistedObject otherKvo) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long getId() {
		return IFConsts.NEW_ITEM_ID;
	}

	@Override
	public void setId(Long id) {
	}

	@Override
	public boolean isNew() {
		return true;
	}

}
