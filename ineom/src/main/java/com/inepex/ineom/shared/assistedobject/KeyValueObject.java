package com.inepex.ineom.shared.assistedobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.NullableHashMap;
import com.inepex.ineom.shared.Relation;

/**
 * @author Istvan Szoboszlai Class for representing an object with key-value
 *         pairs. Data is stored in 6 different maps, in each by type.
 */
@SuppressWarnings("serial")
public class KeyValueObject extends AssistedObject {

	private String descriptorName = null;

	protected NullableHashMap<String, Boolean> booleanValues = new NullableHashMap<String, Boolean>();
	protected NullableHashMap<String, Double> doubleValues = new NullableHashMap<String, Double>();
	protected NullableHashMap<String, IneList> listValues = new NullableHashMap<String, IneList>();
	protected NullableHashMap<String, Long> longValues = new NullableHashMap<String, Long>();
	protected NullableHashMap<String, Relation> relationValues = new NullableHashMap<String, Relation>();
	protected NullableHashMap<String, String> stringValues = new NullableHashMap<String, String>();

	/**
	 * Default constructor needed for the type to be searilizable, although the
	 * other constructor that specifies descriptorName should be used
	 */
	public KeyValueObject() {
	}

	/**
	 * This constructor should be used to initiate a new KVO
	 * 
	 * @param descriptorName
	 */
	public KeyValueObject(String descriptorName) {
		this.descriptorName = descriptorName;
	}

	/**
	 * idKVO constructor
	 * 
	 */
	public KeyValueObject(String descriptorName, Long id) {
		this.descriptorName = descriptorName;
		setId(id);
	}

	@Override
	public KeyValueObject clone() {
		KeyValueObject kvo = new KeyValueObject(descriptorName);
		this.copyValuesTo(kvo);
		return kvo;
	}

	@Override
	public String getDescriptorName() {
		return descriptorName;
	}

	@Override
	public Long getId() {
		Long id = longValues.get(IFConsts.KEY_ID);
		return id == null ? IFConsts.NEW_ITEM_ID : id;
	}

	@Override
	public void setId(Long id) {
		longValues.put(IFConsts.KEY_ID, id);
	}

	/**
	 * TODO unused yet
	 * 
	 */
	protected boolean isDeleted() {
		//TODO unused yet
		Boolean deleted = booleanValues.get(IFConsts.KEY_ISDELETED);
		return deleted != null || deleted;
	}

	/**
	 * TODO unused yet
	 * 
	 */
	void setDeleleted(boolean b) {
		//TODO unused yet
		booleanValues.put(IFConsts.KEY_ISDELETED, b);
	}

	@Override
	public boolean isNew() {
		return getId().equals(IFConsts.NEW_ITEM_ID);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(booleanValues).append("\n");
		sb.append(doubleValues).append("\n");
		sb.append(listValues).append("\n");
		sb.append(longValues).append("\n");
		sb.append(relationValues).append("\n");
		sb.append(stringValues).append("\n");

		return sb.toString();
	}

	@Override
	protected void set(String key, Boolean value) {
		booleanValues.put(key, value);
	}

	@Override
	protected void set(String key, Double value) {
		doubleValues.put(key, value);
	}

	@Override
	protected void set(String key, IneList value) {
		listValues.put(key, value);
	}

	@Override
	protected void set(String key, Long value) {
		longValues.put(key, value);
	}

	@Override
	protected void set(String key, Relation value) {
		relationValues.put(key, value);
	}

	@Override
	protected void set(String key, String value) {
		stringValues.put(key, value);
	}

	@Override
	protected Boolean getBoolean(String key) {
		return booleanValues.get(key);
	}

	@Override
	protected Double getDouble(String key) {
		return doubleValues.get(key);
	}

	@Override
	protected IneList getList(String key) {
		return listValues.get(key);
	}

	@Override
	protected Long getLong(String key) {
		return longValues.get(key);
	}

	@Override
	protected Relation getRelation(String key) {
		return relationValues.get(key);
	}

	@Override
	protected String getString(String key) {
		return stringValues.get(key);
	}

	@Override
	public void setUnchecked(String key, Long value) {
		longValues.put(key, value);
	}

	@Override
	public void setUnchecked(String key, String value) {
		stringValues.put(key, value);
	}

	@Override
	public Long getLongUnchecked(String key) {
		return longValues.get(key);
	}

	@Override
	public String getStringUnchecked(String key) {
		return stringValues.get(key);
	}

	@Override
	protected boolean containsString(String key) {
		return stringValues.keySet().contains(key);
	}

	@Override
	protected boolean containsBoolean(String key) {
		return booleanValues.keySet().contains(key);
	}

	@Override
	protected boolean containsDouble(String key) {
		return doubleValues.keySet().contains(key);
	}

	@Override
	protected boolean containsList(String key) {
		return listValues.keySet().contains(key);
	}

	@Override
	protected boolean containsLong(String key) {
		return longValues.keySet().contains(key);
	}

	@Override
	protected boolean containsRelation(String key) {
		return relationValues.keySet().contains(key);
	}

	@Override
	public List<String> getKeys() {
		List<String> allKeys = new ArrayList<String>();

		allKeys.addAll(booleanValues.keySet());
		allKeys.addAll(doubleValues.keySet());
		allKeys.addAll(listValues.keySet());
		allKeys.addAll(longValues.keySet());
		allKeys.addAll(relationValues.keySet());
		allKeys.addAll(stringValues.keySet());

		return allKeys;
	}

	@Override
	protected void copyValuesTo(AssistedObject target) {

		for (String key : booleanValues.keySet()) {
			Boolean bool = this.booleanValues.get(key);
			target.set(key, bool == null ? null : new Boolean(bool));
		}

		for (String key : doubleValues.keySet()) {
			Double dbl = this.doubleValues.get(key);
			target.set(key, dbl == null ? null : new Double(dbl));
		}

		for (String key : listValues.keySet()) {
			IneList ineList = this.listValues.get(key);
			target.set(key, ineList == null ? null : new IneList(ineList));
		}

		for (String key : longValues.keySet()) {
			Long lng = this.longValues.get(key);
			target.set(key, lng == null ? null : new Long(lng));
		}

		for (String key : relationValues.keySet()) {
			Relation relation = this.relationValues.get(key);
			Relation targetRelation = target.getRelation(key);

			if (targetRelation == null || relation == null
					|| targetRelation.getKvo() == null
					|| targetRelation.getId() == null
					|| !targetRelation.getId().equals(relation.getId())) {
				target.set(key, relation == null ? null
						: new Relation(relation));
			} else {
				if (relation.getKvo() == null)
					targetRelation.setKvo(null);
				else {
					relation.getKvo().copyValuesTo(targetRelation.getKvo());
				}
			}
		}

		for (String key : stringValues.keySet()) {
			String str = this.stringValues.get(key);
			target.set(key, str == null ? null : new String(str));
		}
	}

	/**
	 * NOT GENERATED
	 * FOR TESTING
	 * 
	 */
	@Override
	public boolean equals(Object obj) { 
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		KeyValueObject other = (KeyValueObject) obj;
		if (descriptorName == null) {
			if (other.descriptorName != null)
				return false;
		} else if (!descriptorName.equals(other.descriptorName))
			return false;

		if (!booleanValues.equals(other.booleanValues))
			return false;
		if (!doubleValues.equals(other.doubleValues))
			return false;
		if (!listValues.equals(other.listValues))
			return false;
		if (!longValues.equals(other.longValues))
			return false;
		if (!relationValues.equals(other.relationValues))
			return false;
		if (!stringValues.equals(other.stringValues))
			return false;

		return true;
	}

	@Override
	public Set<String> getLongKeys() {
		return longValues.keySet();
	}

	@Override
	public Set<String> getBooleanKeys() {
		return booleanValues.keySet();
	}

	@Override
	public Set<String> getDoubleKeys() {
		return doubleValues.keySet();
	}

	@Override
	public Set<String> getStringKeys() {
		return stringValues.keySet();
	}

	@Override
	public Set<String> getListKeys() {
		return listValues.keySet();
	}

	@Override
	public Set<String> getRelationKeys() {
		return relationValues.keySet();
	}
}
