package com.inepex.ineom.shared.assistedobject;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.HasProp;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * this abstract class describes the base data storing class like KeyValueObject
 * or DTOAdapter
 * 
 * methods are unchecked!! use {@link AssistedObjectChecker} to type safe
 * manipulating
 * 
 */
@JsonDeserialize(as = KeyValueObject.class)
@SuppressWarnings("serial")
public abstract class AssistedObject implements Serializable, IsSerializable, HasProp {

    /******** ADD methods ********/
    protected abstract void set(String key, Boolean value);

    protected abstract void set(String key, Double value);

    protected abstract void set(String key, IneList value);

    protected abstract void set(String key, Long value);

    protected abstract void set(String key, Relation value);

    protected abstract void set(String key, String value);

    protected abstract void unsetField(String key);

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

    public abstract void setUnchecked(String key, Boolean value);

    public abstract void setUnchecked(String key, Double value);

    public abstract void setUnchecked(String key, IneList value);

    /******** Unchecked GET methods ********/
    public abstract Long getLongUnchecked(String key);

    public abstract Boolean getBooleanUnchecked(String key);

    public abstract String getStringUnchecked(String key);

    public abstract Relation getRelationUnchecked(String key);

    public abstract Double getDoubleUnchecked(String key);

    /******** CONTAINS methods ********/
    protected abstract boolean containsString(String key);

    protected abstract boolean containsBoolean(String key);

    protected abstract boolean containsDouble(String key);

    protected abstract boolean containsList(String key);

    protected abstract boolean containsLong(String key);

    protected abstract boolean containsRelation(String key);

    /******** HELPER methods ********/
    public abstract String getDescriptorName();

    public abstract void setDescriptorName(String descriptorName);

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

    /** custom property **/
    public abstract Map<String, String> getAllPropsJson();

    public abstract String getPropsJson(String id);

    public abstract void setPropsJson(String id, String json);
}