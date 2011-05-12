package com.inepex.ineom.shared.kvo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.util.SharedUtil;

/**
 * @author Istvan Szoboszlai Class for representing an object with key-value
 *         pairs. Data is stored in 6 different maps, in each by type.
 */
public class KeyValueObject implements AssistedObject {

	private static final long serialVersionUID = 8733400848398541311L;

	private String descriptorName = null;

	private transient ObjectDesc objectDescriptor = null;

	protected Map<String, Boolean> booleanValues = null;
	protected Map<String, Double> doubleValues = null;
	protected Map<String, IneList> listValues = null;
	protected Map<String, Long> longValues = null;
	protected Map<String, Relation> relationValues = null;
	protected Map<String, String> stringValues = null;

	/**
	 * Default constructor needed for the type to be searilizable, although the
	 * other constructor that specifies descriptorName should be used
	 */
	public KeyValueObject() {
	}

	public ObjectDesc getObjectDescriptor() {
		if (objectDescriptor == null)
			objectDescriptor = ExposedDescStore.get().getOD(descriptorName);

		return objectDescriptor;
	}

	/**
	 * This constructor should be used to initiate a new
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

	// ---------------------------- ADD
	// methods-----------------------------------------------------
	// --------------------------------------------------------------------------------------------

	@Override
	public void set(String key, Boolean value) {
		checkDescriptorCheckKey(key);

		if (value == null) {
			if (booleanValues != null)
				booleanValues.remove(key);
		} else {
			createBooleanMapIfNull();
			booleanValues.put(key, value);
		}

	}

	private void createBooleanMapIfNull() {
		if (booleanValues == null)
			booleanValues = new HashMap<String, Boolean>();
	}

	@Override
	public void set(String key, Double value) {
		checkDescriptorCheckKey(key);
		createDoubleMapIfNull();

		doubleValues.put(key, value);
	}

	private void createDoubleMapIfNull() {
		if (doubleValues == null)
			doubleValues = new HashMap<String, Double>();
	}

	@Override
	public void set(String key, IneList value) {
		checkDescriptorCheckKey(key);
		createListMapIfNull();
		listValues.put(key, value);
	}

	private void createListMapIfNull() {
		if (listValues == null)
			listValues = new HashMap<String, IneList>();

	}

	@Override
	public void set(String key, Long value) {
		checkDescriptorCheckKey(key);
		setUnchecked(key, value);
	}

	private void createLongMapIfNull() {
		if (longValues == null)
			longValues = new HashMap<String, Long>();
	}

	@Override
	public void setUnchecked(String key, Long value) {
		createLongMapIfNull();
		longValues.put(key, value);
	}

	@Override
	public void set(String key, Relation value) {
		checkDescriptorCheckKey(key);
		createRelationMapIfNull();
		relationValues.put(key, value);
	}

	private void createRelationMapIfNull() {
		if (relationValues == null)
			relationValues = new HashMap<String, Relation>();

	}

	@Override
	public void set(String key, String value) {
		checkDescriptorCheckKey(key);
		setUnchecked(key, value);
	}

	@Override
	public void setUnchecked(String key, String value) {
		createStringMapIfNull();
		stringValues.put(key, value);
	}

	private void createStringMapIfNull() {
		if (stringValues == null)
			stringValues = new HashMap<String, String>();
	}

	// ----------------------------getters---------------------------------------------------------
	// --------------------------------------------------------------------------------------------

	@Override
	public Boolean getBoolean(String key) {
		checkDescriptorCheckKey(key);
		
		if (booleanValues != null)
			return booleanValues.get(key);

		return null;
	}

	@Override
	public Double getDouble(String key) {
		checkDescriptorCheckKey(key);

		if (doubleValues != null)
			return doubleValues.get(key);

		return null;
	}

	@Override
	public IneList getList(String key) {
		checkDescriptorCheckKey(key);

		if (listValues != null)
			return listValues.get(key);

		return null;
	}

	@Override
	public Long getLongUnchecked(String key) {
		if (longValues != null)
			return longValues.get(key);

		return null;
	}

	@Override
	public Long getLong(String key) {
		checkDescriptorCheckKey(key);
		return getLongUnchecked(key);
	}

	@Override
	public Relation getRelation(String key) {
		checkDescriptorCheckKey(key);

		if (relationValues != null)
			return relationValues.get(key);

		return null;
	}

	@Override
	public String getString(String key) {
		checkDescriptorCheckKey(key);
		return getStringUnchecked(key);
	}

	@Override
	public String getStringUnchecked(String key) {

		if (stringValues != null)
			return stringValues.get(key);

		return null;

	}

	@Override
	public <T extends Enum<T>> T getEnum(String key, Class<T> enumType) {
		Long l = getLong(key);
		if (l == null)
			return null;
		else
			return enumType.getEnumConstants()[l.intValue()];
	}

	private boolean checkDescriptorCheckKey(String key) {
		if (descriptorName == null)
			return false;

		ObjectDesc descriptor = getObjectDescriptor();

		if (descriptor == null)
			return false;

		if (!descriptor.containsKey(key))
			throw new InvalidKeyException("Key '" + key
					+ "' is not found in descriptor '" + descriptorName + "'");

		return true;
	}

	// --------------------related
	// getters---------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	@Override
	public Boolean getRelatedBoolean(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObject relatedKvo = getRelatedKVOMultiLevel(id);
		return relatedKvo.getBoolean(id.get(id.size() - 1));
	}

	@Override
	public Double getRelatedDouble(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObject relatedKvo = getRelatedKVOMultiLevel(id);
		return relatedKvo.getDouble(id.get(id.size() - 1));
	}

	@Override
	public IneList getRelatedList(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObject relatedKvo = getRelatedKVOMultiLevel(id);
		return relatedKvo.getList(id.get(id.size() - 1));
	}

	@Override
	public Long getRelatedLongUnchecked(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObject relatedKvo = getRelatedKVOMultiLevel(id);
		return relatedKvo.getLongUnchecked(id.get(id.size() - 1));
	}

	@Override
	public Long getRelatedLong(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObject relatedKvo = getRelatedKVOMultiLevel(id);
		return relatedKvo.getLong(id.get(id.size() - 1));
	}

	@Override
	public Relation getRelatedRelation(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObject relatedKvo = getRelatedKVOMultiLevel(id);
		return relatedKvo.getRelation(id.get(id.size() - 1));
	}

	@Override
	public String getRelatedString(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObject relatedKvo = getRelatedKVOMultiLevel(id);
		return relatedKvo.getString(id.get(id.size() - 1));
	}

	@Override
	public String getRelatedStringUnchecked(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObject relatedKvo = getRelatedKVOMultiLevel(id);
		return relatedKvo.getStringUnchecked(id.get(id.size() - 1));
	}

	@Override
	public <T extends Enum<T>> T getRelatedEnum(String dotSeparetedKeys,
			Class<T> enumType) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObject relatedKvo = getRelatedKVOMultiLevel(id);
		return relatedKvo.getEnum(id.get(id.size() - 1), enumType);
	}

	/******** HELPER methods ********/
	@Override
	public List<String> getKeys() {
		List<String> allKeys = new ArrayList<String>();
		if (booleanValues != null)
			allKeys.addAll(booleanValues.keySet());
		if (doubleValues != null)
			allKeys.addAll(doubleValues.keySet());
		if (listValues != null)
			allKeys.addAll(listValues.keySet());
		if (longValues != null)
			allKeys.addAll(longValues.keySet());
		if (relationValues != null)
			allKeys.addAll(relationValues.keySet());
		if (stringValues != null)
			allKeys.addAll(stringValues.keySet());

		return allKeys;
	}

	@Override
	public String getDescriptorName() {
		return descriptorName;
	}

	@Override
	public String getValueAsString(String key) {
		if (descriptorName == null)
			return "";

		ObjectDesc descriptor = getObjectDescriptor();
		Object o;

		if (descriptor != null) {
			switch (descriptor.getField(key).getType()) {
			case BOOLEAN:
				o = getBoolean(key) ? IFConsts.TRUE : IFConsts.FALSE;
				return o == null ? null : o.toString();

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
			}
		}

		return null;
	}

	public KeyValueObject clone() {
		KeyValueObject kvo = new KeyValueObject(descriptorName);
		this.copyValuesTo(kvo);
		return kvo;
	}

	public void shallowCopyTo(KeyValueObject target) {
		if (!this.getDescriptorName().equals(target.getDescriptorName())) {
			throw new RuntimeException(
					"Descriptor name of target object does not match descriptor name of this object!");
		}
		createBooleanMapIfNull();
		createDoubleMapIfNull();
		createListMapIfNull();
		createLongMapIfNull();
		createRelationMapIfNull();
		createStringMapIfNull();
		target.booleanValues = this.booleanValues;		
		target.doubleValues = this.doubleValues;		
		target.listValues = this.listValues;		
		target.longValues = this.longValues;		
		target.relationValues = this.relationValues;		
		target.stringValues = this.stringValues;
	}

	@Override
	public void copyValuesTo(KeyValueObject target) {
		if (booleanValues != null) {
			if (target.booleanValues == null)
				target.booleanValues = new HashMap<String, Boolean>();
			for (String key : booleanValues.keySet()) {
				Boolean bool = this.booleanValues.get(key);
				target.booleanValues.put(key, bool == null ? null
						: new Boolean(bool));
			}
		}

		if (doubleValues != null) {
			if (target.doubleValues == null)
				target.doubleValues = new HashMap<String, Double>();
			for (String key : doubleValues.keySet()) {
				Double dbl = this.doubleValues.get(key);
				target.doubleValues.put(key, dbl == null ? null : new Double(
						dbl));
			}
		}

		if (listValues != null) {
			if (target.listValues == null)
				target.listValues = new HashMap<String, IneList>();
			for (String key : listValues.keySet()) {
				IneList ineList = this.listValues.get(key);
				target.listValues.put(key, ineList == null ? null
						: new IneList(ineList));
			}
		}

		if (longValues != null) {
			if (target.longValues == null)
				target.longValues = new HashMap<String, Long>();
			for (String key : longValues.keySet()) {
				Long lng = this.longValues.get(key);
				target.longValues.put(key, lng == null ? null : new Long(lng));
			}
		}

		if (relationValues != null) {
			if (target.relationValues == null)
				target.relationValues = new HashMap<String, Relation>();
			
			for (String key : relationValues.keySet()) {
				Relation relation = this.relationValues.get(key);
				Relation targetRelation = target.relationValues.get(key);
				
				if (targetRelation == null 
						|| relation == null 
						|| targetRelation.getKvo() == null
						|| targetRelation.getId()==null
						|| !targetRelation.getId().equals(relation.getId())) {
					target.relationValues.put(key, relation == null ? null
						: new Relation(relation));
				} else {
					if (relation.getKvo() == null)
						targetRelation.setKvo(null);
					else {
						relation.getKvo().copyValuesTo((KeyValueObject)targetRelation.getKvo());
					}
				}
			}
		}

		if (stringValues != null) {
			if (target.stringValues == null)
				target.stringValues = new HashMap<String, String>();
			for (String key : stringValues.keySet()) {
				String str = this.stringValues.get(key);
				target.stringValues.put(key, str == null ? null : new String(
						str));
			}
		}
	}

	public Long getId() {
		if (longValues == null)
			return IFConsts.NEW_ITEM_ID;

		Long id = longValues.get(IFConsts.KEY_ID);
		return id == null ? IFConsts.NEW_ITEM_ID : id;
	}

	public void setId(Long id) {
		set(IFConsts.KEY_ID, id);
	}

	public boolean isDeleted() {
		return booleanValues.get(IFConsts.KEY_ISDELETED);
	}

	public void setDeleleted(boolean b) {
		set(IFConsts.KEY_ISDELETED, b);
	}

	@Override
	/**
	 * not generated
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyValueObject other = (KeyValueObject) obj;
		if (booleanValues == null) {
			if (other.booleanValues != null)
				return false;
		} else if (!booleanValues.equals(other.booleanValues))
			return false;
		if (descriptorName == null) {
			if (other.descriptorName != null)
				return false;
		} else if (!descriptorName.equals(other.descriptorName))
			return false;
		if (doubleValues == null) {
			if (other.doubleValues != null)
				return false;
		} else if (!doubleValues.equals(other.doubleValues))
			return false;
		if (listValues == null) {
			if (other.listValues != null)
				return false;
		} else if (!listValues.equals(other.listValues))
			return false;
		if (longValues == null) {
			if (other.longValues != null)
				return false;
		} else if (!longValues.equals(other.longValues))
			return false;
		if (objectDescriptor == null) {
			if (other.objectDescriptor != null)
				return false;
		} else if (!objectDescriptor.equals(other.objectDescriptor))
			return false;
		if (relationValues == null) {
			if (other.relationValues != null)
				return false;
		} else if (!relationValues.equals(other.relationValues))
			return false;
		if (stringValues == null) {
			if (other.stringValues != null)
				return false;
		} else if (!stringValues.equals(other.stringValues))
			return false;

		return true;
	}

	public boolean isNew() {
		return getId().equals(IFConsts.NEW_ITEM_ID);
	}

	// Static functions following
	@Deprecated
	public static boolean isKvoNew(KeyValueObject kvo) {
		return kvo != null && kvo.getId().equals(IFConsts.NEW_ITEM_ID);
	}

	/******** CONTAINS methods ********/
	@Override
	public boolean containsString(String key) {
		return stringValues != null && stringValues.keySet().contains(key);
	}

	@Override
	public boolean containsBoolean(String key) {
		return booleanValues != null && booleanValues.keySet().contains(key);
	}

	@Override
	public boolean containsDouble(String key) {
		return doubleValues != null && doubleValues.keySet().contains(key);
	}

	@Override
	public boolean containsList(String key) {
		return listValues != null && listValues.keySet().contains(key);
	}

	@Override
	public boolean containsLong(String key) {
		return longValues != null && longValues.keySet().contains(key);
	}

	@Override
	public boolean containsRelation(String key) {
		return relationValues != null && relationValues.keySet().contains(key);
	}

	@Override
	public AssistedObject getRelatedKVOMultiLevel(List<String> path) {
		AssistedObject actual = this;
		for (int i = 0; i < path.size() - 1; i++) {
			Relation rel = actual.getRelation(path.get(i));
			if (rel == null) {
				rel = new Relation(IFConsts.NEW_ITEM_ID, "");				
				actual.set(path.get(i), rel);
			}
			
			if(rel.getKvo()==null) {
				RelationFDesc relDescriptor = (RelationFDesc) actual.getObjectDescriptor().getField(path.get(i));
				rel.setKvo(new KeyValueObject(relDescriptor.getRelatedDescriptorName()));
			}
			
			actual = rel.getKvo();
		}
		return actual;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (booleanValues != null && booleanValues.size() > 0)
			sb.append(booleanValues).append("\n");
		if (doubleValues != null && doubleValues.size() > 0)
			sb.append(doubleValues).append("\n");
		if (listValues != null && listValues.size() > 0)
			sb.append(listValues).append("\n");
		if (longValues != null && longValues.size() > 0)
			sb.append(longValues).append("\n");
		if (relationValues != null && relationValues.size() > 0)
			sb.append(relationValues).append("\n");
		if (stringValues != null && stringValues.size() > 0)
			sb.append(stringValues).append("\n");

		return sb.toString();
	}

	@Override
	public AssistedObject getDifference(AssistedObject original) {
		if (original == null)
			return this;

		KeyValueObject difference = new KeyValueObject(
				original.getDescriptorName(), original.getId());

		ObjectDesc objDesc = original.getObjectDescriptor();

		for (String key : objDesc.getFields().keySet()) {
			FDesc fieldDesc = objDesc.getFields().get(key);
			switch (fieldDesc.getType()) {
			case BOOLEAN: {
				Boolean orig = original.getBoolean(key);
				Boolean chng = this.getBoolean(key);
				if (orig == null) {
					if (chng == null)
						continue;
					difference.set(key, chng);
				} else {
					if (!orig.equals(chng))
						difference.set(key, chng);
				}
				break;
			}
			case DOUBLE: {
				Double orig = original.getDouble(key);
				Double chng = this.getDouble(key);
				if (orig == null) {
					if (chng == null)
						continue;
					difference.set(key, chng);
				} else {
					if (!orig.equals(chng))
						difference.set(key, chng);
				}
				break;
			}
			case LIST: {
				// IMPORTANT: IneList always just contains the difference after
				// edited in a form!
				IneList chng = this.getList(key);
				if (chng == null || chng.getRelationList() == null
						|| chng.getRelationList().size() == 0)
					continue;
				difference.set(key, chng);
				break;
			}
			case LONG: {
				Long orig = original.getLong(key);
				Long chng = this.getLong(key);
				if (orig == null) {
					if (chng == null)
						continue;
					difference.set(key, chng);
				} else {
					if (!orig.equals(chng))
						difference.set(key, chng);
				}
				break;
			}
			case RELATION: {
				Relation orig = original.getRelation(key);
				Relation chng = this.getRelation(key);
				if (orig == null) {
					if (chng == null)
						continue;
					difference.set(key, chng);
				} else {
					if (!orig.equals(chng)) { 
						if (chng == null)
							difference.set(key, (Relation) null);
						else {	
							// We should only add the fields to the relations kvo that differ!
							Relation rel = new Relation(chng.getId(), chng.getDisplayName());
							if (chng.getKvo() != null)
								rel.setKvo(chng.getKvo().getDifference(orig.getKvo()));
							difference.set(key, rel);
						}
					}
				}
				break;
			}
			case STRING: {
				String orig = original.getString(key);
				String chng = this.getString(key);
				if (orig == null) {
					if (chng == null)
						continue;
					difference.set(key, chng);
				} else {
					if (!orig.equals(chng))
						difference.set(key, chng);
				}
				break;
			}
			}

		}
		return difference;
	}
	
	
}
