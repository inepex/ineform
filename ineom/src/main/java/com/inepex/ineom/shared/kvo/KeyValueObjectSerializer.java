package com.inepex.ineom.shared.kvo;

import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class KeyValueObjectSerializer {
	public static interface ListSerializer {
		public String serialize(IneList list);
	}
	
	public static interface RelationSerializer {
		public String serialize(Relation relation);
	}
	
	AssistedObject kvo;
	String fieldSeparator;
	String equalsSign;
	
	boolean includeDescriptorName = false;
	ListSerializer listSerializer;
	RelationSerializer relationSerializer;
	
	ObjectDesc od;
	
	boolean includeId = true;
	
	public KeyValueObjectSerializer(AssistedObject kvo, String fieldSeparator, String equalsSign) {
		this.kvo = kvo;
		this.fieldSeparator = fieldSeparator;
		this.equalsSign = equalsSign;
		od = ExposedDescStore.get().getOD(kvo.getDescriptorName());
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

	public KeyValueObjectSerializer setIncludeId(boolean includeId) {
		this.includeId = includeId;
		return this;
	}

	public String serializeToString() {
		StringBuilder sb = new StringBuilder();
		if (includeDescriptorName)
			sb.append(kvo.getDescriptorName()).append(fieldSeparator);
		
		for (String key : od.getFields().keySet()) {
			if (!includeId && key.equals(IFConsts.KEY_ID)
					|| od.getField(key).hasProp("notserialize")) continue;
			String value = getValueAsString(key);
			if (value == null)
				continue;
			sb.append(key);
			sb.append(equalsSign);
			sb.append(value);
			sb.append(fieldSeparator);
		}
		
		return sb.substring(0, sb.length()-fieldSeparator.length());
	}

	
	private String getValueAsString(String key) {
		Object o;

		if (od != null) {
			switch (od.getField(key).getType()) {
			case BOOLEAN:
				o = kvo.getBoolean(key) ? IFConsts.TRUE : IFConsts.FALSE;
				return o == null ? null : o.toString();

			case DOUBLE:
				o = kvo.getDouble(key);
				return o == null ? null : o.toString();

			case LIST:
				o = kvo.getList(key);
				if (listSerializer != null) return  o == null ? null : listSerializer.serialize((IneList)o);
				else return o == null ? null : o.toString();

			case LONG:
				o = kvo.getLong(key);
				return o == null ? null : o.toString();

			case RELATION:
				Relation r = kvo.getRelation(key);
				if (relationSerializer != null) return  r == null ? null : relationSerializer.serialize((Relation)r);
				return r == null ? null : r.getDisplayName();

			case STRING:
				o = kvo.getString(key);
				return o == null ? null : o.toString();
			}
		}

		return null;
	}

	
}
