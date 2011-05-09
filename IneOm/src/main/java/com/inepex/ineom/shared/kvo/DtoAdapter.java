package com.inepex.ineom.shared.kvo;

import java.io.Serializable;
import java.util.List;

import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;

public abstract class DtoAdapter implements AssistedObject, Serializable {

	private static final long serialVersionUID = 1L;

	private transient ObjectDesc objectDescriptor = null;
	
	@Override
	public abstract DtoAdapter clone();

	protected abstract void setObject(String key, Object o);
	protected abstract Object getObject(String key);
	
	
	@Override
	public void set(String key, Boolean value) {
		setObject(key, value);
	}

	@Override
	public void set(String key, Double value) {
		setObject(key, value);
	}

	@Override
	public void set(String key, IneList value) {
		setObject(key, value);
	}

	@Override
	public void set(String key, Long value) {
		setObject(key, value);
	}

	@Override
	public void set(String key, Relation value) {
		setObject(key, value);
	}

	@Override
	public void set(String key, String value) {
		setObject(key, value);
	}

	@Override
	public <T extends Enum<T>> T getEnum(String key, Class<T> enumType) {
		Long l = getLong(key);
		if (l == null)
			return null;
		else
			return enumType.getEnumConstants()[l.intValue()];
	}

	@Override
	public Boolean getBoolean(String key) {
		return (Boolean) getObject(key);
	}

	@Override
	public Double getDouble(String key) {
		return (Double) getObject(key);
	}

	@Override
	public IneList getList(String key) {
		return (IneList) getObject(key);
	}

	@Override
	public Long getLong(String key) {
		return (Long) getObject(key);
	}

	@Override
	public Relation getRelation(String key) {
		return (Relation) getObject(key);
	}

	@Override
	public String getString(String key) {
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
	public Boolean getRelatedBoolean(String dotSeparetedKeys) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Double getRelatedDouble(String dotSeparetedKeys) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IneList getRelatedList(String dotSeparetedKeys) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long getRelatedLongUnchecked(String dotSeparetedKeys) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long getRelatedLong(String dotSeparetedKeys) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Relation getRelatedRelation(String dotSeparetedKeys) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRelatedString(String dotSeparetedKeys) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRelatedStringUnchecked(String dotSeparetedKeys) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends Enum<T>> T getRelatedEnum(String dotSeparetedKeys,
			Class<T> enumType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsString(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsBoolean(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsDouble(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsList(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsLong(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsRelation(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void copyValuesTo(KeyValueObject otherKvo) {
		throw new UnsupportedOperationException();
	}


	@Override
	public AssistedObject getDifference(AssistedObject original) {
		return this.clone();
	}
	
	@Override
	public String getValueAsString(String key) {
		if (getDescriptorName() == null)
			return "";

		ObjectDesc descriptor = getObjectDescriptor();
		Object o;

		if (descriptor != null) {
			switch (descriptor.getField(key).getType()) {
			case RELATION:
				Relation r = getRelation(key);
				return r == null ? null : r.getDisplayName();

			default:
				o = getObject(key);
				return o == null ? null : o.toString();
			}
		}

		return null;
	}
	
	@Override
	public AssistedObject getRelatedKVOMultiLevel(List<String> path) {
		AssistedObject actual = this;
		for (int i = 0; i < path.size() - 1; i++) {
			Relation rel = actual.getRelation(path.get(i));
			if (rel == null) {
				rel = new Relation();
				RelationFDesc relDescriptor = (RelationFDesc) actual
						.getObjectDescriptor().getField(path.get(i));
				rel.setKvo(new KeyValueObject(relDescriptor
						.getRelatedDescriptorName()));
			}
			actual = rel.getKvo();
		}
		return actual;
	}
	
	public ObjectDesc getObjectDescriptor() {
		if (objectDescriptor == null)
			objectDescriptor = ExposedDescStore.get().getOD(getDescriptorName());

		return objectDescriptor;
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
