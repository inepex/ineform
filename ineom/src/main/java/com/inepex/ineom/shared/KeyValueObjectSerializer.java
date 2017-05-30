package com.inepex.ineom.shared;

import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;

public class KeyValueObjectSerializer {
    public interface ListSerializer {
        String serialize(IneList list);
    }

    public interface RelationSerializer {
        String serialize(Relation relation);
    }

    public interface StringEncoder {
        String encode(String value);
    }

    AssistedObjectChecker checker;
    String fieldSeparator;
    String equalsSign;

    boolean includeDescriptorName = false;
    ListSerializer listSerializer;
    RelationSerializer relationSerializer;
    StringEncoder stringEncoder;

    boolean includeId = true;
    boolean skipEmptyValues = false;

    public KeyValueObjectSerializer(
        AssistedObjectChecker checker,
        String fieldSeparator,
        String equalsSign) {
        this.checker = checker;
        this.fieldSeparator = fieldSeparator;
        this.equalsSign = equalsSign;
    }

    public KeyValueObjectSerializer setListSerializer(ListSerializer listSerializer) {
        this.listSerializer = listSerializer;
        return this;
    }

    public KeyValueObjectSerializer setRelationSerializer(RelationSerializer relationSerializer) {
        this.relationSerializer = relationSerializer;
        return this;
    }

    public KeyValueObjectSerializer setIncludeDescriptorName(boolean includeDescriptorName) {
        this.includeDescriptorName = includeDescriptorName;
        return this;
    }

    public KeyValueObjectSerializer setStringEncoder(StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
        return this;
    }

    public KeyValueObjectSerializer setIncludeId(boolean includeId) {
        this.includeId = includeId;
        return this;
    }

    public KeyValueObjectSerializer setSkipEmptyValues(boolean skipEmptyValues) {
        this.skipEmptyValues = skipEmptyValues;
        return this;
    }

    public String serializeToString() {
        StringBuilder sb = new StringBuilder();
        if (includeDescriptorName)
            sb.append(checker.getDescriptorName()).append(fieldSeparator);

        for (String key : checker.objectDescriptor.getFields().keySet()) {
            if (!includeId && key.equals(IFConsts.KEY_ID)
                || checker.objectDescriptor.getField(key).hasProp("notserialize"))
                continue;
            String value = getValueAsString(key);
            if (value == null)
                continue;
            if (skipEmptyValues && value.equals(""))
                continue;
            sb.append(key);
            sb.append(equalsSign);
            sb.append(value);
            sb.append(fieldSeparator);
        }

        return sb.substring(0, sb.length() - fieldSeparator.length());
    }

    private String getValueAsString(String key) {
        Object o;

        switch (checker.objectDescriptor.getField(key).getType()) {
            case BOOLEAN:
                o = checker.getBoolean(key) ? IFConsts.TRUE : IFConsts.FALSE;
                return o == null ? null : o.toString();

            case DOUBLE:
                o = checker.getDouble(key);
                return o == null ? null : o.toString();

            case LIST:
                o = checker.getList(key);
                if (listSerializer != null)
                    return o == null ? null : listSerializer.serialize((IneList) o);
                else
                    return o == null ? null : o.toString();

            case LONG:
                o = checker.getLong(key);
                return o == null ? null : o.toString();

            case RELATION:
                Relation r = checker.getRelation(key);
                if (relationSerializer != null)
                    return r == null ? null : relationSerializer.serialize(r);
                return r == null ? null : r.getDisplayName();

            case STRING:
                o = checker.getString(key);
                if (o == null)
                    return null;

                if (stringEncoder == null)
                    return o.toString();

                return stringEncoder.encode(o.toString());
        }

        return null;
    }

}
