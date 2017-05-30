package com.inepex.ineom.shared.assistedobject;

import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;

import java.util.Set;

@SuppressWarnings("serial")
public abstract class DtoAdapter extends AssistedObject {

    private static final Object lock = new Object();
    private static long counter = 1L;

    private Long id;

    protected DtoAdapter() {
        synchronized (lock) {
            id = counter++;
        }
    }

    @Override
    public abstract DtoAdapter clone();

    protected abstract void setObject(String key, Object o);

    protected abstract Object getObject(String key);

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return false;
    }

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
        set(key, value);
    }

    @Override
    public void setUnchecked(String key, String value) {
        set(key, value);
    }

    @Override
    public void setUnchecked(String key, IneList value) {
        set(key, value);
    }

    @Override
    public Long getLongUnchecked(String key) {
        return getLong(key);
    }

    @Override
    public String getStringUnchecked(String key) {
        return getString(key);
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
    public void setUnchecked(String key, Boolean value) {
        set(key, value);
    }

    @Override
    public void setUnchecked(String key, Double value) {
        set(key, value);
    }

    @Override
    public Boolean getBooleanUnchecked(String key) {
        return getBoolean(key);
    }

    @Override
    public Relation getRelationUnchecked(String key) {
        return getRelation(key);
    }

    @Override
    public Double getDoubleUnchecked(String key) {
        return getDouble(key);
    }

    @Override
    public void setDescriptorName(String descriptorName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getLongKeys() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getBooleanKeys() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getDoubleKeys() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getStringKeys() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getListKeys() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getRelationKeys() {
        throw new UnsupportedOperationException();
    }
}
