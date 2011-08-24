package com.inepex.ineom.shared.assistedobject;

import java.util.List;

import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

/**
 * this class is for manipulating a KVO's fields
 *
 * it's not superclass of AssistedObject for avoid mistypes, but has the same methods
 * 
 * use {@link AssistedObjectHandlerFactory} to create instances
 */
public class AssistedObjectChecker {

	private final AssistedObject assistedObject;
	public final String descriptorName;
	public final ObjectDesc objectDescriptor;

	public AssistedObjectChecker(AssistedObject assistedObject,
			String descriptorName, ObjectDesc objectDescriptor) {
		this.assistedObject = assistedObject;
		this.descriptorName = descriptorName;
		this.objectDescriptor = objectDescriptor;
	}
	
	public AssistedObject getAssistedObject() {
		return assistedObject;
	}

	// -------------------------------------------------------
	// check method
	// -------------------------------------------------------
	private void checkDescriptorCheckKey(String key, IneT ineT) {
		if (!objectDescriptor.containsKey(key))
			throw new InvalidKeyException("Key '" + key
					+ "' is not found in descriptor '" + descriptorName + "'");

		IneT fieldT = objectDescriptor.getField(key).getType();
		if (!fieldT.equals(ineT))
			throw new InvalidKeyException("The type of field for key '" + key
					+ "' in descriptor '" + descriptorName + "' is '"
					+ fieldT.toString() + "' and not '" + ineT.toString() + "'");
	}

	// -------------------------------------------------------
	// setter, getter and contain methods
	// -------------------------------------------------------
	public void set(String key, Boolean value) {
		checkDescriptorCheckKey(key, IneT.BOOLEAN);
		assistedObject.set(key, value);
	}

	public void set(String key, Double value) {
		checkDescriptorCheckKey(key, IneT.DOUBLE);
		assistedObject.set(key, value);
	}

	public void set(String key, IneList value) {
		checkDescriptorCheckKey(key, IneT.LIST);
		assistedObject.set(key, value);
	}

	public void set(String key, Long value) {
		checkDescriptorCheckKey(key, IneT.LONG);
		assistedObject.set(key, value);
	}

	public void set(String key, Relation value) {
		checkDescriptorCheckKey(key, IneT.RELATION);
		assistedObject.set(key, value);
	}

	public void set(String key, String value) {
		checkDescriptorCheckKey(key, IneT.STRING);
		assistedObject.set(key, value);
	}

	public Boolean getBoolean(String key) {
		checkDescriptorCheckKey(key, IneT.BOOLEAN);
		return assistedObject.getBoolean(key);
	}

	public Double getDouble(String key) {
		checkDescriptorCheckKey(key, IneT.DOUBLE);
		return assistedObject.getDouble(key);
	}

	public IneList getList(String key) {
		checkDescriptorCheckKey(key, IneT.LIST);
		return assistedObject.getList(key);
	}

	public Long getLong(String key) {
		checkDescriptorCheckKey(key, IneT.LONG);
		return assistedObject.getLong(key);
	}

	public Relation getRelation(String key) {
		checkDescriptorCheckKey(key, IneT.RELATION);
		return assistedObject.getRelation(key);
	}

	public String getString(String key) {
		checkDescriptorCheckKey(key, IneT.STRING);
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
		checkDescriptorCheckKey(key, IneT.STRING);
		return assistedObject.containsString(key);
	}

	public boolean containsBoolean(String key) {
		checkDescriptorCheckKey(key, IneT.BOOLEAN);
		return assistedObject.containsBoolean(key);
	}

	public boolean containsDouble(String key) {
		checkDescriptorCheckKey(key, IneT.DOUBLE);
		return assistedObject.containsDouble(key);
	}

	public boolean containsList(String key) {
		checkDescriptorCheckKey(key, IneT.LIST);
		return assistedObject.containsList(key);
	}

	public boolean containsLong(String key) {
		checkDescriptorCheckKey(key, IneT.LONG);
		return assistedObject.containsLong(key);
	}

	public boolean containsRelation(String key) {
		checkDescriptorCheckKey(key, IneT.RELATION);
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
}
