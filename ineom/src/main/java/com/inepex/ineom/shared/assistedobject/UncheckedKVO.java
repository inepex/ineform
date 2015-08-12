package com.inepex.ineom.shared.assistedobject;

import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;

@SuppressWarnings("serial")
public class UncheckedKVO extends KeyValueObject {

    /**
     * only for serialization
     */
    public UncheckedKVO() {
        super();
    }

    public UncheckedKVO(String descriptorName) {
        super(descriptorName);
    }

    @Override
    public void set(String key, Boolean value) {
        super.set(key, value);
    }

    @Override
    public void set(String key, Double value) {
        super.set(key, value);
    }

    @Override
    public void set(String key, Long value) {
        super.set(key, value);
    }

    @Override
    public void set(String key, String value) {
        super.set(key, value);
    }

    @Override
    public void set(String key, IneList value) {
        super.set(key, value);
    }

    @Override
    public void set(String key, Relation value) {
        super.set(key, value);
    }

}