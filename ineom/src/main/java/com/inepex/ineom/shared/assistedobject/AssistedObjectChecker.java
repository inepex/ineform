package com.inepex.ineom.shared.assistedobject;

import java.util.List;

import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

/**
 * this class is for manipulating a KVO's fields
 *
 * it's not superclass of AssistedObject for avoid mistypes, but has the same methods
 * 
 * see {@link AssistedObjectHandlerFactory}'s java doc too
 */
public class AssistedObjectChecker {

	private final AssistedObject assistedObject;
	public final String descriptorName;
	public final ObjectDesc objectDescriptor;
	private final DescriptorChecker checker;

	public AssistedObjectChecker(AssistedObject assistedObject,
			String descriptorName, ObjectDesc objectDescriptor) {
		this.assistedObject = assistedObject;
		this.descriptorName = descriptorName;
		this.objectDescriptor = objectDescriptor;
		this.checker = new RealDescriptorChecker(objectDescriptor, descriptorName);
	}
	
	protected AssistedObjectChecker(String descriptorName){
		this.assistedObject = new KeyValueObject(descriptorName);
		this.descriptorName = descriptorName;
		this.objectDescriptor = null;
		this.checker = new DummyDescriptorChecker();
	}
	
	public AssistedObject getAssistedObject() {
		return assistedObject;
	}

	// -------------------------------------------------------
	// setter, getter and contain methods
	// -------------------------------------------------------
	public void set(String key, Boolean value) {
		checker.checkDescriptorCheckKey(key, IneT.BOOLEAN);
		assistedObject.set(key, value);
	}

	public void set(String key, Double value) {
		checker.checkDescriptorCheckKey(key, IneT.DOUBLE);
		assistedObject.set(key, value);
	}

	public void set(String key, IneList value) {
		checker.checkDescriptorCheckKey(key, IneT.LIST);
		assistedObject.set(key, value);
	}

	public void set(String key, Long value) {
		checker.checkDescriptorCheckKey(key, IneT.LONG);
		assistedObject.set(key, value);
	}

	public void set(String key, Relation value) {
		checker.checkDescriptorCheckKey(key, IneT.RELATION);
		assistedObject.set(key, value);
	}

	public void set(String key, String value) {
		checker.checkDescriptorCheckKey(key, IneT.STRING);
		assistedObject.set(key, value);
	}
	
	public void unSetString(String key) {
		checker.checkDescriptorCheckKey(key, IneT.STRING);
		assistedObject.unsetField(key);
	}

	public Boolean getBoolean(String key) {
		checker.checkDescriptorCheckKey(key, IneT.BOOLEAN);
		return assistedObject.getBoolean(key);
	}
	
	public Boolean getBooleanUnchecked(String key) {
		return assistedObject.getBoolean(key);
	}

	public Double getDouble(String key) {
		checker.checkDescriptorCheckKey(key, IneT.DOUBLE);
		return assistedObject.getDouble(key);
	}
	
	public Double getDoubleUnchecked(String key) {
		return assistedObject.getDouble(key);
	}

	public IneList getList(String key) {
		checker.checkDescriptorCheckKey(key, IneT.LIST);
		return assistedObject.getList(key);
	}
	
	public IneList getListUnchecked(String key) {
		return assistedObject.getList(key);
	}

	public Long getLong(String key) {
		checker.checkDescriptorCheckKey(key, IneT.LONG);
		return assistedObject.getLong(key);
	}

	public Relation getRelation(String key) {
		checker.checkDescriptorCheckKey(key, IneT.RELATION);
		return assistedObject.getRelation(key);
	}
	
	public Relation getRelationUnchecked(String key) {
		return assistedObject.getRelation(key);
	}

	public String getString(String key) {
		checker.checkDescriptorCheckKey(key, IneT.STRING);
		return assistedObject.getString(key);
	}

	public void setUnchecked(String key, Long value) {
		assistedObject.setUnchecked(key, value);
	}

	public void setUnchecked(String key, String value) {
		assistedObject.setUnchecked(key, value);
	}

	public Long getLongUnchecked(String key) {
		return assistedObject.getLongUnchecked(key);
	}

	public String getStringUnchecked(String key) {
		return assistedObject.getStringUnchecked(key);
	}

	public boolean containsString(String key) {
		checker.checkDescriptorCheckKey(key, IneT.STRING);
		return assistedObject.containsString(key);
	}

	public boolean containsBoolean(String key) {
		checker.checkDescriptorCheckKey(key, IneT.BOOLEAN);
		return assistedObject.containsBoolean(key);
	}

	public boolean containsDouble(String key) {
		checker.checkDescriptorCheckKey(key, IneT.DOUBLE);
		return assistedObject.containsDouble(key);
	}

	public boolean containsList(String key) {
		checker.checkDescriptorCheckKey(key, IneT.LIST);
		return assistedObject.containsList(key);
	}

	public boolean containsLong(String key) {
		checker.checkDescriptorCheckKey(key, IneT.LONG);
		return assistedObject.containsLong(key);
	}

	public boolean containsRelation(String key) {
		checker.checkDescriptorCheckKey(key, IneT.RELATION);
		return assistedObject.containsRelation(key);
	}

	// -------------------------------------------------------
	// other methods
	// -------------------------------------------------------
	public String getDescriptorName() {
		return assistedObject.getDescriptorName();
	}

	public List<String> getKeys() {
		return assistedObject.getKeys();
	}

	public void copyValuesTo(AssistedObject otherKvo) {
		if(!getDescriptorName().equals(otherKvo.getDescriptorName()))
			throw new IllegalArgumentException();
		
		assistedObject.copyValuesTo(otherKvo);
	}

	public Long getId() {
		return assistedObject.getId();
	}

	public void setId(Long id) {
		assistedObject.setId(id);
	}

	public boolean isNew() {
		return assistedObject.isNew();
	}
	
	public String getValueAsString(String key) {
		Object o;

		switch (objectDescriptor.getField(key).getType()) {
		case BOOLEAN:
			o = getBoolean(key);
			return o == null ? null : 
				((Boolean) o ? IFConsts.TRUE : IFConsts.FALSE);

		case DOUBLE:
			o = getDouble(key);
			return o == null ? null : o.toString();

		case LIST:
			o = getList(key);
			return o == null ? null : o.toString();

		case LONG:
			o = getLong(key);
			return o == null ? null : o.toString();

		case RELATION:
			Relation r = getRelation(key);
			return r == null ? null : r.getDisplayName();

		case STRING:
			o = getString(key);
			return o == null ? null : o.toString();
		case UNDEFINED:
			return null;
		default:
			return null;
		}
	}
}
