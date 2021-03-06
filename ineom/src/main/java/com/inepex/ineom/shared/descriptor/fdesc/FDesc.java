package com.inepex.ineom.shared.descriptor.fdesc;

import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;

import java.util.Arrays;
import java.util.TreeSet;

@SuppressWarnings("serial")
public abstract class FDesc extends DescriptorBase {

    private String key;

    // the field can be edited
    private boolean editable = true;

    protected TreeSet<String> validatorNames = new TreeSet<>();

    protected IneT type = null;

    private String defaultDisplayName = null;

    private boolean nullable = true;

    private boolean excludeFromDesc = false;

    public FDesc() {}

    public FDesc(String key, IneT type) {
        this.key = key;
        this.type = type;
    }

    public FDesc(String key, IneT type, String defaultDisplayName) {
        this.key = key;
        this.type = type;
        this.defaultDisplayName = defaultDisplayName;
    }

    public FDesc(String key, IneT type, String defaultDisplayName, String... properties) {
        this.key = key;
        this.type = type;
        this.defaultDisplayName = defaultDisplayName;
        addProps(properties);
    }

    public FDesc(String key, IneT type, String... properties) {
        this.key = key;
        this.type = type;
        addProps(properties);
    }

    public FDesc addValidators(String... names) {
        validatorNames.addAll(Arrays.asList(names));
        return this;
    }

    public TreeSet<String> getValidatorNames() {
        return validatorNames;
    }

    public boolean hasValidator(String validatorName) {
        return validatorNames.contains(validatorName);
    }

    public String getKey() {
        return key;
    }

    public boolean isEditable() {
        return editable;
    }

    public FDesc setEditable(boolean editable) {
        this.editable = editable;
        return this;
    }

    public IneT getType() {
        return type;
    }

    public String getDefaultDisplayName() {
        return defaultDisplayName;
    }

    public void setDefaultDisplayName(String defaultDisplayName) {
        this.defaultDisplayName = defaultDisplayName;
    }

    public boolean isNullable() {
        return nullable;
    }

    public FDesc setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public FDesc mandatory() {
        validatorNames.add(KeyValueObjectValidationManager.MANDATORY);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isExcludeFromDesc() {
        return excludeFromDesc;
    }

    public FDesc setExcludeFromDesc(boolean excludeFromDesc) {
        this.excludeFromDesc = excludeFromDesc;
        return this;
    }

    public FDesc excl() {
        return setExcludeFromDesc(true);
    }

}
