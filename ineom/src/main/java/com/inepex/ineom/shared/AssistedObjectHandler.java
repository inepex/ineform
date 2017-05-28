package com.inepex.ineom.shared;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.util.SharedUtil;

import java.util.List;

public class AssistedObjectHandler extends AssistedObjectChecker {

    private final DescriptorStore descriptorStore;

    public AssistedObjectHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
        super(
            assistedObject,
            assistedObject.getDescriptorName(),
            descriptorStore.getOD(assistedObject.getDescriptorName()));
        this.descriptorStore = descriptorStore;

        if (descriptorName == null)
            throw new IllegalArgumentException("No descriptorName!");

        if (objectDescriptor == null)
            throw new IllegalArgumentException(
                "No object descriptor registered for '" + descriptorName + "'");
    }

    protected AssistedObjectHandler(String descriptorName) {
        super(descriptorName);
        this.descriptorStore = null;
    }

    public static AssistedObjectHandler createUnchecked(String descName) {
        return new AssistedObjectHandler(descName);
    }

    // -------------------------------------------------------
    // related methods
    // -------------------------------------------------------
    public Boolean getRelatedBoolean(String dotSeparatedKeys) {
        List<String> id = SharedUtil.listFromDotSeparated(dotSeparatedKeys);
        AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
        return handler.getBoolean(id.get(id.size() - 1));
    }

    public Double getRelatedDouble(String dotSeparatedKeys) {
        List<String> id = SharedUtil.listFromDotSeparated(dotSeparatedKeys);
        AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
        return handler.getDouble(id.get(id.size() - 1));
    }

    public IneList getRelatedList(String dotSeparatedKeys) {
        List<String> id = SharedUtil.listFromDotSeparated(dotSeparatedKeys);
        AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
        return handler.getList(id.get(id.size() - 1));
    }

    public Long getRelatedLongUnchecked(String dotSeparatedKeys) {
        List<String> id = SharedUtil.listFromDotSeparated(dotSeparatedKeys);
        AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
        return handler.getLongUnchecked(id.get(id.size() - 1));
    }

    public Long getRelatedLong(String dotSeparatedKeys) {
        List<String> id = SharedUtil.listFromDotSeparated(dotSeparatedKeys);
        AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
        return handler.getLong(id.get(id.size() - 1));
    }

    public Relation getRelatedRelation(String dotSeparatedKeys) {
        List<String> id = SharedUtil.listFromDotSeparated(dotSeparatedKeys);
        AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
        return handler.getRelation(id.get(id.size() - 1));
    }

    public String getRelatedString(String dotSeparatedKeys) {
        List<String> id = SharedUtil.listFromDotSeparated(dotSeparatedKeys);
        AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
        return handler.getString(id.get(id.size() - 1));
    }

    public String getRelatedStringUnchecked(String dotSeparatedKeys) {
        List<String> id = SharedUtil.listFromDotSeparated(dotSeparatedKeys);
        AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
        return handler.getStringUnchecked(id.get(id.size() - 1));
    }

    // -------------------------------------------------------
    // helper methods
    // -------------------------------------------------------

    public AssistedObjectHandler getRelatedKVOMultiLevel(List<String> path) {
        AssistedObjectHandler actual = this;
        for (int i = 0; i < path.size() - 1; i++) {
            Relation rel = actual.getRelation(path.get(i));
            if (rel == null) {
                rel = new Relation(IFConsts.NEW_ITEM_ID, "");
                actual.set(path.get(i), rel);
            }

            if (rel.getKvo() == null) {
                RelationFDesc relDescriptor = (RelationFDesc) descriptorStore
                    .getOD(actual.getDescriptorName())
                    .getField(path.get(i));
                rel.setKvo(new KeyValueObject(relDescriptor.getRelatedDescriptorName()));
            }

            actual = new AssistedObjectHandler(rel.getKvo(), descriptorStore);
        }

        return actual;
    }

    public <T extends Enum<T>> T getEnum(String key, Class<T> enumType) {
        Long l = getLong(key);
        if (l == null)
            return null;
        else
            return enumType.getEnumConstants()[l.intValue()];
    }
}