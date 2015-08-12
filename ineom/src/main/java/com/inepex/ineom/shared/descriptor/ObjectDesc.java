package com.inepex.ineom.shared.descriptor;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

/**
 * a tree of FieldDescriptors
 *
 */
public class ObjectDesc implements Serializable, IsSerializable {
    private static final long serialVersionUID = 7161148180827105250L;

    private LinkedHashMap<String, FDesc> fields = new LinkedHashMap<String, FDesc>();

    private String name;
    private String titleKey;

    private String defaultOrderKey = "";

    /**
     * Default constructor only used for serialization, should not be used to
     * create the class
     */
    public ObjectDesc() {

    }

    public ObjectDesc(String name) {
        this.name = name;
    }

    public ObjectDesc(String name, FDesc... fields) {
        this(name);
        for (FDesc fieldDescriptor : fields) {
            this.fields.put(fieldDescriptor.getKey(), fieldDescriptor);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectDesc addField(FDesc fieldDescriptor) {
        fields.put(fieldDescriptor.getKey(), fieldDescriptor);
        return this;
    }

    public boolean containsKey(String key) {
        return fields.containsKey(key);
    }

    public Map<String, FDesc> getFields() {
        return fields;
    }

    public Set<String> getKeys() {
        return fields.keySet();
    }

    public int getFieldCount() {
        return fields.size();
    }

    public FDesc getField(String key) {
        return fields.get(key);
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public ObjectDesc setTKey(String titleKey) {
        this.titleKey = titleKey;
        return this;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public String getDefaultOrderKey() {
        return defaultOrderKey;
    }

    public void setDefaultOrderKey(String defaultOrderKey) {
        this.defaultOrderKey = defaultOrderKey;
    }

    @Override
    public String toString() {
        return "ObjectDesc [name=" + name + "]";
    }

}
