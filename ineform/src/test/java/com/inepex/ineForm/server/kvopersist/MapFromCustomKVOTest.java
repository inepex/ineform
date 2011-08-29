package com.inepex.ineForm.server.kvopersist;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.inepex.ineForm.shared.types.ODFieldType;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;

public class MapFromCustomKVOTest {
	
	private static final String k_long="kLo";
	private static final String k_bool="kBo";
	private static final String k_double="kDo";
	private static final String k_str="kStr";
	private static final String k_str2="kStr2";
	
	private static CustomKVO customKVO;
	
	static {
		customKVO = new CustomKVO();
		customKVO.setId(1L);
		
		List<PersistField> persistFields =  new ArrayList<PersistField>();
		persistFields.add(new PersistField(23L, null, k_long, ODFieldType.LONG, 123L));
		persistFields.add(new PersistField(25L, null, k_bool, ODFieldType.BOOLEAN, false));
		persistFields.add(new PersistField(28L, null, k_double, ODFieldType.DOUBLE, 1.3321));
		persistFields.add(new PersistField(32L, null, k_str, ODFieldType.STRING, "a string value"));
		persistFields.add(new PersistField(43L, null, k_str2, ODFieldType.EMAIL, "email@domain.co"));
		
		customKVO.setFields(persistFields);
	}
	
	@Test
	public void test() {
		AssistedObject ao = CustomKVOMapperHelper.getKVOFromCustomKVO(customKVO);

		//assisted object check
		Assert.assertEquals(IFConsts.customDescriptorName, ao.getDescriptorName());
		Assert.assertEquals(1, ao.getId().intValue());
		
		List<String> keys = ao.getKeys();
		Assert.assertEquals(6, keys.size());
		Assert.assertEquals(true, keys.contains(IFConsts.KEY_ID));
		Assert.assertEquals(true, keys.contains(k_bool));
		Assert.assertEquals(true, keys.contains(k_double));
		Assert.assertEquals(true, keys.contains(k_long));
		Assert.assertEquals(true, keys.contains(k_str));
		Assert.assertEquals(true, keys.contains(k_str2));
		
		//od check
		ObjectDesc od = CustomKVOMapperHelper.getODFromCustomKVO(customKVO);
		Assert.assertEquals(IFConsts.customDescriptorName, od.getName());
		Assert.assertEquals(5, od.getFieldCount());
		Assert.assertEquals(true, od.containsKey(k_bool));
		Assert.assertEquals(true, od.containsKey(k_double));
		Assert.assertEquals(true, od.containsKey(k_long));
		Assert.assertEquals(true, od.containsKey(k_str));
		Assert.assertEquals(true, od.containsKey(k_str2));
		
		Assert.assertEquals(IneT.BOOLEAN, od.getField(k_bool).getType());
		Assert.assertEquals(IneT.LONG, od.getField(k_long).getType());
		Assert.assertEquals(IneT.DOUBLE, od.getField(k_double).getType());
		Assert.assertEquals(IneT.STRING, od.getField(k_str).getType());
		Assert.assertEquals(IneT.STRING, od.getField(k_str2).getType());
		Assert.assertEquals(true, od.getField(k_str2).hasProp(KeyValueObjectValidationManager.EMAIL));
		
		//check together
		AssistedObjectChecker checker = new AssistedObjectChecker(ao, IFConsts.customDescriptorName, od) {};
		
		Assert.assertEquals(1, checker.getId().intValue());
		Assert.assertEquals(123, checker.getLong(k_long).intValue());
		Assert.assertEquals(false, checker.getBoolean(k_bool).booleanValue());
		Assert.assertEquals(1.3321, checker.getDouble(k_double).doubleValue());
		Assert.assertEquals("a string value", checker.getString(k_str));
		Assert.assertEquals("email@domain.co", checker.getString(k_str2));
	}
	
}
