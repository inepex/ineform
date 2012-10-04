package com.inepex.ineForm.server.kvopersist;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.inepex.ineForm.server.customkvo.CustomKVO;
import com.inepex.ineForm.server.customkvo.CustomKVOMapperHelper;
import com.inepex.ineForm.server.customkvo.PersistField;
import com.inepex.ineForm.shared.customkvo.CreatedFdesc;
import com.inepex.ineForm.shared.types.ODFieldType;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;

public class MapIntoCustomKVOTest {
	
	private static final String k_long="kLo";
	private static final String k_bool="kBo";
	private static final String k_double="kDo";
	private static final String k_str="kStr";
	private static final String k_str2="kStr2";
	
	
	private static final String k_bool_toAdd="boolToAdd"; //it will be added
	private static final String k_bool_tillChange="kBoTChange"; //its type will be changed
	
	@Test
	public void testCleanAll(){
		CustomKVO customKVO = getCustomKVO();
		ObjectDesc od = new ObjectDesc(IFConsts.customDescriptorName);
		CustomKVOMapperHelper.mapIntoCustomKVO(customKVO, new KeyValueObject(IFConsts.customDescriptorName), od);
		
		Assert.assertEquals(0, customKVO.getFields().size());
	}
	
	@Test
	public void testCreateField(){
		CustomKVO newKVO = getEmptyCustomKVO();
		CustomKVO expectedKVO = getCustomKVO(); 
		
		ObjectDesc od = CustomKVOMapperHelper.getODFromCustomKVO(expectedKVO);
		AssistedObject ao = CustomKVOMapperHelper.getKVOFromCustomKVO(expectedKVO);
		
		CustomKVOMapperHelper.mapIntoCustomKVO(newKVO, ao, od);
		
		Assert.assertEquals(expectedKVO, newKVO);
	}
	
	@Test
	public void testAddField(){
		CustomKVO kvo = getCustomKVO();
		
		
		ObjectDesc od = CustomKVOMapperHelper.getODFromCustomKVO(kvo);
		od.addField(new CreatedFdesc(k_bool_toAdd, IneT.BOOLEAN));
		
		AssistedObject ao = CustomKVOMapperHelper.getKVOFromCustomKVO(kvo);
		new AssistedObjectChecker(ao, IFConsts.customDescriptorName, od).set(k_bool_toAdd, true);
		
		CustomKVOMapperHelper.mapIntoCustomKVO(kvo, ao, od);
		
		int found=0;
		for(PersistField f : kvo.getFields()) {
			if(k_bool_toAdd.equals(f.getKey())) {
				found++;
				
				Assert.assertNull(f.getId());
				Assert.assertNull(f.getStringVal());
				Assert.assertNull(f.getDoubleVal());
				Assert.assertNull(f.getLongVal());
				Assert.assertEquals(true, f.getBooleanVal().booleanValue());
				Assert.assertEquals(ODFieldType.BOOLEAN, f.getFieldType());
				Assert.assertEquals(new CustomKVO(kvo.getId()), f.getParent());
			}
		}
		
		Assert.assertEquals(6, kvo.getFields().size());
		Assert.assertEquals(1, found);
	}
	
	@Test
	public void testOverwriteField(){
		CustomKVO kvo = getCustomKVO();
		ObjectDesc od = CustomKVOMapperHelper.getODFromCustomKVO(kvo);		
		AssistedObject ao = CustomKVOMapperHelper.getKVOFromCustomKVO(kvo);
		
		new AssistedObjectChecker(ao, IFConsts.customDescriptorName, od).set(k_str, "new string value");
		
		CustomKVOMapperHelper.mapIntoCustomKVO(kvo, ao, od);
		
		int found=0;
		for(PersistField f : kvo.getFields()) {
			if(k_str.equals(f.getKey())) {
				found++;
				
				Assert.assertNotNull(f.getId());
				Assert.assertNull(f.getDoubleVal());
				Assert.assertNull(f.getLongVal());
				Assert.assertNull(f.getBooleanVal());
				Assert.assertEquals("new string value", f.getStringVal());
				Assert.assertEquals(ODFieldType.STRING, f.getFieldType());
				Assert.assertEquals(new CustomKVO(kvo.getId()), f.getParent());
			}
		}
		
		Assert.assertEquals(5, kvo.getFields().size());
		Assert.assertEquals(1, found);
	}
	
	@Test
	public void testFieldTypeModify(){
		CustomKVO kvo = getCustomKVO();
		kvo.getFields().add(new PersistField(54321L, new CustomKVO(1L), k_bool_tillChange, ODFieldType.BOOLEAN, true));
		
		ObjectDesc od = CustomKVOMapperHelper.getODFromCustomKVO(kvo);
		
		FDesc fd = od.getField(k_bool_tillChange);
		od.getFields().remove(fd);
		
		od.addField(new CreatedFdesc(k_bool_tillChange, IneT.STRING, KeyValueObjectValidationManager.EMAIL));
		
		AssistedObject ao = CustomKVOMapperHelper.getKVOFromCustomKVO(kvo);
		new AssistedObjectChecker(ao, IFConsts.customDescriptorName, od).set(k_bool_tillChange, "new_email_type@domain.com");
		
		CustomKVOMapperHelper.mapIntoCustomKVO(kvo, ao, od);
		
		int found=0;
		for(PersistField f : kvo.getFields()) {
			if(k_bool_tillChange.equals(f.getKey())) {
				found++;
				
				Assert.assertEquals(54321, f.getId().intValue());
				Assert.assertNull(f.getDoubleVal());
				Assert.assertNull(f.getLongVal());
				Assert.assertNull(f.getBooleanVal());
				Assert.assertEquals("new_email_type@domain.com", f.getStringVal());
				Assert.assertEquals(ODFieldType.EMAIL, f.getFieldType());
				Assert.assertEquals(new CustomKVO(kvo.getId()), f.getParent());
			}
		}
		
		Assert.assertEquals(6, kvo.getFields().size());
		Assert.assertEquals(1, found);
	}
	
	private static CustomKVO getEmptyCustomKVO() {
		CustomKVO customKVO = new CustomKVO();
		customKVO.setId(1L);		
		return customKVO;
	}
	
	private static CustomKVO getCustomKVO() {
		CustomKVO customKVO = new CustomKVO();
		customKVO.setId(1L);
		
		List<PersistField> persistFields =  new ArrayList<PersistField>();
		persistFields.add(new PersistField(23L, new CustomKVO(1L), k_long, ODFieldType.LONG, 123L));
		persistFields.add(new PersistField(25L, new CustomKVO(1L), k_bool, ODFieldType.BOOLEAN, false));
		persistFields.add(new PersistField(28L, new CustomKVO(1L), k_double, ODFieldType.DOUBLE, 1.3321));
		persistFields.add(new PersistField(32L, new CustomKVO(1L), k_str, ODFieldType.STRING, "a string value"));
		persistFields.add(new PersistField(43L, new CustomKVO(1L), k_str2, ODFieldType.EMAIL, "email@domain.co"));
		
		customKVO.setFields(persistFields);
		return customKVO;
	}
}
