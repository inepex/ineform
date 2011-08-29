package com.inepex.ineForm.server.kvopersist;

import java.util.ArrayList;
import java.util.Iterator;

import com.inepex.ineForm.shared.types.ODFieldType;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class CustomKVOMapperHelper {

	public static AssistedObject getKVOFromCustomKVO(CustomKVO customKVO) {
		UncheckedKVO ao = new UncheckedKVO(IFConsts.customDescriptorName);
		ao.setId(customKVO.getId());
		
		if(customKVO.getFields()!=null) {
			for(PersistField pf : customKVO.getFields()) {
				switch (pf.getFieldType().ineT) {
				case BOOLEAN:
					ao.set(pf.getKey(), pf.getBooleanVal());
					break;
				case DOUBLE:
					ao.set(pf.getKey(), pf.getDoubleVal());
					break;
				case STRING:
					ao.set(pf.getKey(), pf.getStringVal());
					break;
				case LONG:
					ao.set(pf.getKey(), pf.getLongVal());
					break;
				}
			}
		}
		
		return ao;
	}
	
	public static ObjectDesc getODFromCustomKVO(CustomKVO customKVO) {
		ObjectDesc od = new ObjectDesc(IFConsts.customDescriptorName);
		
		if(customKVO.getFields()!=null) {
			for(PersistField pf : customKVO.getFields()) {
				od.addField(new CreatedFdesc(pf.getKey(), pf.getFieldType().ineT, pf.getFieldType().validators));
			}
		}
		
		return od;
	}
	
	public static void mapIntoCustomKVO(CustomKVO target, AssistedObject sourceAO, ObjectDesc sourceOd) {
		AssistedObjectChecker checker = new AssistedObjectChecker(sourceAO, IFConsts.customDescriptorName, sourceOd);
		
		if(target.getFields()==null) {
			target.setFields(new ArrayList<PersistField>());
		}
		
		//deleting old rows
		Iterator<PersistField> it = target.getFields().iterator();
		while(it.hasNext()) {
			PersistField pf = it.next();
			if(sourceOd.getField(pf.getKey())==null)
				it.remove();
		}
		
		//adding new ones and editing existed rows
		for(FDesc fd : sourceOd.getFields().values()) {
			PersistField pf = searchOrCreateAndSetType(target, fd);
			clearFields(fd.getType(), pf);
			
			String key = fd.getKey();
			switch (fd.getType()) {
			case BOOLEAN:
				if(checker.containsBoolean(key))
					pf.setBooleanVal(checker.getBoolean(key));
				break;
			case STRING:
				if(checker.containsString(key))
					pf.setStringVal(checker.getString(key));
				break;
			case DOUBLE:
				if(checker.containsDouble(key))
					pf.setDoubleVal(checker.getDouble(key));
				break;
			case LONG:
				if(checker.containsLong(key))
					pf.setLongVal(checker.getLong(key));
				break;
			default:
				throw new IllegalArgumentException();
			}
		}
	}
	
	/**
	 * this method search or create the suitable {@link PersistField} for {@link FDesc} 
	 * 
	 * @return an existing or a created PersistField (never returns null)
	 * 
	 */
	private static PersistField searchOrCreateAndSetType(CustomKVO target, FDesc fd) {
		for(PersistField pf : target.getFields()) {
			if(pf.getKey().equals(fd.getKey())) {
				pf.setFieldType(ODFieldType.searchByFDesc(fd));
				return pf;
			}
		}
		
		PersistField pf = new PersistField();
		pf.setParent(new CustomKVO(target.getId()));
		pf.setFieldType(ODFieldType.searchByFDesc(fd));
		pf.setKey(fd.getKey());
		target.getFields().add(pf);
		
		return pf;
	}

	/**
	 * this method clean the invalid values when field type was changed
	 * 
	 */
	private static void clearFields(IneT ineT, PersistField pf) {
		switch (ineT) {
		case BOOLEAN:
			pf.setLongVal(null);
			pf.setDoubleVal(null);
			pf.setStringVal(null);
			break;
		case DOUBLE:
			pf.setLongVal(null);
			pf.setBooleanVal(null);
			pf.setStringVal(null);
			break;
		case LONG:
			pf.setDoubleVal(null);
			pf.setBooleanVal(null);
			pf.setStringVal(null);
			break;
		case STRING:
			pf.setLongVal(null);
			pf.setDoubleVal(null);
			pf.setBooleanVal(null);
			break;
			
		default:
			throw new IllegalArgumentException(ineT.toString());
		}
	}
	
	
	@SuppressWarnings("serial")
	static class CreatedFdesc extends FDesc {

		public CreatedFdesc(String key, IneT type, String... properties) {
			super(key, type, properties);
		}
	}
	
	@SuppressWarnings("serial")
	static class UncheckedKVO extends KeyValueObject {

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
		
	}
}
