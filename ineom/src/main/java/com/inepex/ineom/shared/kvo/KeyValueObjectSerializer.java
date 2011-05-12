package com.inepex.ineom.shared.kvo;

import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class KeyValueObjectSerializer {

	final AssistedObject kvo;

	public KeyValueObjectSerializer(AssistedObject kvo, String fieldSeparator, String equalsSign) {
		this.kvo = kvo;
	}

	public String serializeToString(String fieldSeparator, String equalsSign) {
		StringBuilder sb = new StringBuilder();

		ObjectDesc od = ExposedDescStore.get().getOD(kvo.getDescriptorName());

		sb.append(kvo.getDescriptorName()).append(fieldSeparator);
		
		for (String key : od.getFields().keySet()) {
			String value = kvo.getValueAsString(key);
			if (value == null)
				continue;
			sb.append(key);
			sb.append(equalsSign);
			sb.append(value);
			sb.append(fieldSeparator);
		}
		
		return sb.substring(0, sb.length()-1);

	}
	
	public void deserializeFromString() {
		//TODO: implement
	}
	
}
