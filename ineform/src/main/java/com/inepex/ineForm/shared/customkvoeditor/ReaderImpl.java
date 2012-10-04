package com.inepex.ineForm.shared.customkvoeditor;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.shared.customkvoeditor.CustomKVOReader.Reader;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

class ReaderImpl implements Reader {
	
	private final Map<String, IneT> typeByKeys;
	private final AssistedObjectChecker checker; 
	private final NumberUtil numberUtil;
	
	ReaderImpl(ObjectDesc od, AssistedObject kvo, NumberUtil numberUtil) {
		this.numberUtil=numberUtil;
		
		checker = new AssistedObjectChecker(kvo, IFConsts.customDescriptorName, od);
		
		typeByKeys = new TreeMap<String, IneT>();
		for(Entry<String, FDesc> entry : od.getFields().entrySet()) {
			typeByKeys.put(entry.getKey(), entry.getValue().getType());
		}
	}

	@Override
	public Collection<String> getKeys() {
		return typeByKeys.keySet();
	}

	@Override
	public String getValueAsString(String key) {
		if(!typeByKeys.containsKey(key))
			return null;
		
		switch(typeByKeys.get(key)) {
		case BOOLEAN:
			Boolean b = checker.getBoolean(key);
			return b==null 
					? IneFormI18n.falseText()
					: (b ? IneFormI18n.trueText() : IneFormI18n.falseText()); 
		case DOUBLE:
			Double d = checker.getDouble(key);
			return d == null 
					? null
					: numberUtil.formatNumberToFractial(d); 
		case STRING:
			return checker.getString(key);
		case LONG:
			Long l = checker.getLong(key);
			return l == null
					? null
					: numberUtil.formatNumberGroupThousands(l);
		default:
			return IneFormI18n.cantDisplay();
		}
	}
}