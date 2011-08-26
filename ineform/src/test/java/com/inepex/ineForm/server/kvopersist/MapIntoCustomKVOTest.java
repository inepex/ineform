package com.inepex.ineForm.server.kvopersist;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.inepex.ineForm.shared.types.OdFieldType;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class MapIntoCustomKVOTest {
	
	private static final String k_long="kLo";
	private static final String k_bool="kBo";
	private static final String k_double="kDo";
	private static final String k_str="kStr";
	private static final String k_str2="kStr2";
	
	
	private static final String k_boll_toAdd="boolToAdd"; //it will be added
	private static final String k_bool_tillChange="kBoTChange"; //its type will be changed
	
	@Test
	public void testCleanAll(){
		CustomKVO customKVO = getCustomKVO();
		ObjectDesc od = new ObjectDesc(IFConsts.customDescriptorName);
		CustomKVOMapperHelper.mapIntoCustomKVO(customKVO, new KeyValueObject(IFConsts.customDescriptorName), od);
		
		Assert.assertEquals(0, customKVO.getFields().size());
	};
	
	@Test
	public void testCreateField(){
		CustomKVO newKVO = getEmptyCustomKVO();
		CustomKVO expectedKVO = getCustomKVO(); 
		
		ObjectDesc od = CustomKVOMapperHelper.getODFromCustomKVO(expectedKVO);
		AssistedObject ao = CustomKVOMapperHelper.getKVOFromCustomKVO(expectedKVO);
		
		CustomKVOMapperHelper.mapIntoCustomKVO(newKVO, ao, od);
		
		//TODO
//		Assert.assertEquals(expectedKVO, newKVO);
	};
	
	@Test
	public void testAddField(){
		
	};
	
	@Test
	public void testOverwriteField(){
		
	};
	
	@Test
	public void testFieldTypeModify(){
		
	};
	
	private static CustomKVO getEmptyCustomKVO() {
		CustomKVO customKVO = new CustomKVO();
		customKVO.setId(1L);		
		return customKVO;
	}
	
	private static CustomKVO getCustomKVO() {
		CustomKVO customKVO = new CustomKVO();
		customKVO.setId(1L);
		
		List<PersistField> persistFields =  new ArrayList<PersistField>();
		persistFields.add(new PersistField(23L, null, k_long, OdFieldType.LONG, 123L));
		persistFields.add(new PersistField(25L, null, k_bool, OdFieldType.BOOLEAN, false));
		persistFields.add(new PersistField(28L, null, k_double, OdFieldType.DOUBLE, 1.3321));
		persistFields.add(new PersistField(32L, null, k_str, OdFieldType.STRING, "a string value"));
		persistFields.add(new PersistField(43L, null, k_str2, OdFieldType.EMAIL, "email@domain.co"));
		
		customKVO.setFields(persistFields);
		return customKVO;
	}
}
