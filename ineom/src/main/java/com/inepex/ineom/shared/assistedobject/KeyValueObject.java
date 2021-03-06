package com.inepex.ineom.shared.assistedobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.LazyHashMap;
import com.inepex.ineom.shared.Relation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Istvan Szoboszlai Class for representing an object with key-value
 *         pairs. Data is stored in 6 different maps, in each by type.
 */
@SuppressWarnings("serial")
public class KeyValueObject extends AssistedObject {

    protected String descriptorName = null;

    protected Map<String, Boolean> booleanValues = new LazyHashMap<>();
    protected Map<String, Double> doubleValues = new LazyHashMap<>();
    protected Map<String, IneList> listValues = new LazyHashMap<>();
    protected Map<String, Long> longValues = new LazyHashMap<>();
    protected Map<String, Relation> relationValues = new LazyHashMap<>();
    protected Map<String, String> stringValues = new LazyHashMap<>();
    protected Map<String, String> propJsons = new LazyHashMap<>();

    /**
     * Default constructor needed for the type to be serializable, although the
     * other constructor that specifies descriptorName should be used
     */
    public KeyValueObject() {}

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
    public void setDescriptorName(String descriptorName) {
        this.descriptorName = descriptorName;
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
        // TODO unused yet
        Boolean deleted = booleanValues.get(IFConsts.KEY_ISDELETED);
        return deleted != null || deleted;
    }

    /**
     * TODO unused yet
     * 
     */
    void setDeleleted(boolean b) {
        // TODO unused yet
        booleanValues.put(IFConsts.KEY_ISDELETED, b);
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return getId().equals(IFConsts.NEW_ITEM_ID);
    }

    @Override
    public String toString() {
        return String.valueOf(booleanValues) + "\n" +
                doubleValues + "\n" +
                listValues + "\n" +
                longValues + "\n" +
                relationValues + "\n" +
                stringValues + "\n" +
                propJsons + "\n";
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
    protected void unsetField(String key) {
        booleanValues.remove(key);
        doubleValues.remove(key);
        listValues.remove(key);
        longValues.remove(key);
        relationValues.remove(key);
        stringValues.remove(key);
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
    public void setUnchecked(String key, IneList value) {
        listValues.put(key, value);
    }

    @Override
    public void setUnchecked(String key, Double value) {
        doubleValues.put(key, value);
    }

    @Override
    public void setUnchecked(String key, Boolean value) {
        booleanValues.put(key, value);
    }

    @Override
    public Long getLongUnchecked(String key) {
        return longValues.get(key);
    }

    @Override
    public Boolean getBooleanUnchecked(String key) {
        return booleanValues.get(key);
    }

    @Override
    public String getStringUnchecked(String key) {
        return stringValues.get(key);
    }

    @Override
    public Relation getRelationUnchecked(String key) {
        return relationValues.get(key);
    }

    @Override
    public Double getDoubleUnchecked(String key) {
        return doubleValues.get(key);
    }

    @Override
    protected boolean containsString(String key) {
        return stringValues.containsKey(key);
    }

    @Override
    protected boolean containsBoolean(String key) {
        return booleanValues.containsKey(key);
    }

    @Override
    protected boolean containsDouble(String key) {
        return doubleValues.containsKey(key);
    }

    @Override
    protected boolean containsList(String key) {
        return listValues.containsKey(key);
    }

    @Override
    protected boolean containsLong(String key) {
        return longValues.containsKey(key);
    }

    @Override
    protected boolean containsRelation(String key) {
        return relationValues.containsKey(key);
    }

    @JsonIgnore
    @Override
    public List<String> getKeys() {
        List<String> allKeys = new ArrayList<>();

        allKeys.addAll(booleanValues.keySet());
        allKeys.addAll(doubleValues.keySet());
        allKeys.addAll(listValues.keySet());
        allKeys.addAll(longValues.keySet());
        allKeys.addAll(relationValues.keySet());
        allKeys.addAll(stringValues.keySet());
        allKeys.addAll(propJsons.keySet());

        return allKeys;
    }

    @Override
    protected void copyValuesTo(AssistedObject target) {

        for (String key : booleanValues.keySet()) {
            Boolean bool = this.booleanValues.get(key);
            target.set(key, bool);
        }

        for (String key : doubleValues.keySet()) {
            Double dbl = this.doubleValues.get(key);
            target.set(key, dbl);
        }

        for (String key : listValues.keySet()) {
            IneList ineList = this.listValues.get(key);
            target.set(key, ineList == null ? null : new IneList(ineList));
        }

        for (String key : longValues.keySet()) {
            Long lng = this.longValues.get(key);
            target.set(key, lng);
        }

        for (String key : relationValues.keySet()) {
            Relation relation = this.relationValues.get(key);
            Relation targetRelation = target.getRelation(key);

            if (targetRelation == null || relation == null || targetRelation.getKvo() == null
                || targetRelation.getId() == null
                || !targetRelation.getId().equals(relation.getId())) {
                target.set(key, relation == null ? null : new Relation(relation));
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
            target.set(key, str);
        }

        for (String key : propJsons.keySet()) {
            target.setPropsJson(key, propJsons.get(key));
        }
    }

    /**
     * NOT GENERATED FOR TESTING
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof KeyValueObject))
            throw new IllegalArgumentException();

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

    @JsonIgnore
    @Override
    public Set<String> getLongKeys() {
        return longValues.keySet();
    }

    @JsonIgnore
    @Override
    public Set<String> getBooleanKeys() {
        return booleanValues.keySet();
    }

    @JsonIgnore
    @Override
    public Set<String> getDoubleKeys() {
        return doubleValues.keySet();
    }

    @JsonIgnore
    @Override
    public Set<String> getStringKeys() {
        return stringValues.keySet();
    }

    @JsonIgnore
    @Override
    public Set<String> getListKeys() {
        return listValues.keySet();
    }

    @JsonIgnore
    @Override
    public Set<String> getRelationKeys() {
        return relationValues.keySet();
    }

    public Map<String, Boolean> getBooleanValues() {
        return booleanValues;
    }

    public Map<String, Double> getDoubleValues() {
        return doubleValues;
    }

    public Map<String, IneList> getListValues() {
        return listValues;
    }

    public Map<String, Long> getLongValues() {
        return longValues;
    }

    public Map<String, Relation> getRelationValues() {
        return relationValues;
    }

    public Map<String, String> getStringValues() {
        return stringValues;
    }

    @Override
    public String getPropsJson(String id) {
        return propJsons.get(id);
    }

    @Override
    public void setPropsJson(String id, String json) {
        propJsons.put(id, json);
    }

    @Override
    public Map<String, String> getAllPropsJson() {
        return propJsons;
    }
}
