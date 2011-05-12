package com.inepex.ineom.shared.kvo;

import java.io.Serializable;
import java.util.List;

import com.inepex.ineom.shared.descriptor.ObjectDesc;

public interface AssistedObject extends Serializable{

/******** ADD methods ********/
		
	public abstract void set(String key, Boolean value);

	public abstract void set(String key, Double value);

	public abstract void set(String key, IneList value);

	public abstract void set(String key, Long value);

	public abstract void set(String key, Relation value);

	public abstract void set(String key, String value);

/******** GET methods ********/
	public abstract <T extends Enum<T>> T getEnum(String key, Class<T> enumType);

	public abstract Boolean getBoolean(String key);

	public abstract Double getDouble(String key);

	public abstract IneList getList(String key);

	public abstract Long getLong(String key);

	public abstract Relation getRelation(String key);

	public abstract String getString(String key);

/******** Unchecked ADD methods ********/
	public abstract void setUnchecked(String key, Long value);

	public abstract void setUnchecked(String key, String value);

/******** Unchecked GET methods ********/
	public abstract Long getLongUnchecked(String key);

	public abstract String getStringUnchecked(String key);

/******** Related GET methods ********/
	public abstract Boolean getRelatedBoolean(String dotSeparetedKeys);

	public abstract Double getRelatedDouble(String dotSeparetedKeys);

	public abstract IneList getRelatedList(String dotSeparetedKeys);

	public abstract Long getRelatedLongUnchecked(String dotSeparetedKeys);

	public abstract Long getRelatedLong(String dotSeparetedKeys);

	public abstract Relation getRelatedRelation(String dotSeparetedKeys);

	public abstract String getRelatedString(String dotSeparetedKeys);

	public abstract String getRelatedStringUnchecked(String dotSeparetedKeys);

	public abstract <T extends Enum<T>> T getRelatedEnum(
			String dotSeparetedKeys, Class<T> enumType);

/******** CONTAINS methods ********/
	public abstract boolean containsString(String key);

	public abstract boolean containsBoolean(String key);

	public abstract boolean containsDouble(String key);

	public abstract boolean containsList(String key);

	public abstract boolean containsLong(String key);

	public abstract boolean containsRelation(String key);
	
/******** HELPER methods ********/
	public abstract String getDescriptorName();

	/**
	 * list of used (one of set(String key, *) was invoked on the selected object) keys of AssistedObjects obejct desctiptor
	 */
	public abstract List<String> getKeys();

	public abstract String getValueAsString(String key);

	public abstract AssistedObject clone();
	
	public abstract void copyValuesTo(KeyValueObject otherKvo);

	public abstract AssistedObject getRelatedKVOMultiLevel(List<String> path);
	
	public abstract ObjectDesc getObjectDescriptor();
	
	public abstract AssistedObject getDifference(AssistedObject original);
		
/******** ID RELATED methods ********/
	public abstract Long getId();
	
	public abstract void setId(Long id);

	public abstract boolean isNew();
	
}