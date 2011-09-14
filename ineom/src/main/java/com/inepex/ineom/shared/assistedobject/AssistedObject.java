package com.inepex.ineom.shared.assistedobject;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;

/**
 * 
 * this abstract class describes the base data storing class like KeyValueObject or DTOAdatper
 * 
 * methods are unchecked!! use {@link AssistedObjectChecker} to type safe manipulating
 *  
 */
@SuppressWarnings("serial")
public abstract class AssistedObject implements Serializable {

	/******** ADD methods ********/
	protected abstract void set(String key, Boolean value);

	protected abstract void set(String key, Double value);

	protected abstract void set(String key, IneList value);

	protected abstract void set(String key, Long value);

	protected abstract void set(String key, Relation value);

	protected abstract void set(String key, String value);

	/******** GET methods ********/
	protected abstract Boolean getBoolean(String key);

	protected abstract Double getDouble(String key);

	protected abstract IneList getList(String key);

	protected abstract Long getLong(String key);

	protected abstract Relation getRelation(String key);

	protected abstract String getString(String key);

	/******** Unchecked ADD methods ********/
	public abstract void setUnchecked(String key, Long value);

	public abstract void setUnchecked(String key, String value);

	/******** Unchecked GET methods ********/
	public abstract Long getLongUnchecked(String key);

	public abstract String getStringUnchecked(String key);

	/******** CONTAINS methods ********/
	protected abstract boolean containsString(String key);

	protected abstract boolean containsBoolean(String key);

	protected abstract boolean containsDouble(String key);

	protected abstract boolean containsList(String key);

	protected abstract boolean containsLong(String key);

	protected abstract boolean containsRelation(String key);

	/******** HELPER methods ********/
	public abstract  String getDescriptorName();

	/**
	 * list of used (one of set(String key, *) was invoked on the selected
	 * object) keys of AssistedObjects object descriptor
	 */
	public abstract List<String> getKeys();
	public abstract Set<String> getLongKeys();
	public abstract Set<String> getBooleanKeys();
	public abstract Set<String> getDoubleKeys();
	public abstract Set<String> getStringKeys();
	public abstract Set<String> getListKeys();
	public abstract Set<String> getRelationKeys();

	public abstract AssistedObject clone();

	protected abstract void copyValuesTo(AssistedObject target);

	/******** ID RELATED methods ********/
	public abstract Long getId();

	public abstract void setId(Long id);

	public abstract boolean isNew();
}