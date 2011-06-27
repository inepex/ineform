package com.inepex.ineom.shared;

import junit.framework.Assert;

import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptor.ListFDesc;
import com.inepex.ineom.shared.descriptor.LongFDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.descriptor.StringFDesc;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.IneList;
import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.Relation;

public class T_e_s_tUtil {

	public static KeyValueObject getTestKvo(ClientDescriptorStore store){
		ObjectDesc descriptor2 = new ObjectDesc("test2Kvo");
		descriptor2.addField(new LongFDesc("longField", "A long"));
		
		ObjectDesc descriptor = new ObjectDesc("testKvo");
		descriptor
			.addField(new StringFDesc("stringField", "A string"))
			.addField(new RelationFDesc("relField", "A relation", "test2Kvo"))
			.addField(new ListFDesc("listField", "A list", "test2Kvo"));
		
		store.registerDescriptors(descriptor2);
		store.registerDescriptors(descriptor);		
		
		KeyValueObject kvo = new KeyValueObject("testKvo");
		kvo.set("stringField", "hello");
		kvo.set("relField", new Relation(getRelKvo(3L)));
		kvo.set("listField", getList(4L, 5L));
		return kvo;
	}
	
	private static KeyValueObject getRelKvo(Long value){
		KeyValueObject relKvo = new KeyValueObject("test2Kvo");
		relKvo.set("longField", value);
		return relKvo;
	}
	
	private static IneList getList(Long ... values){
		IneList list = new IneList();
		for (Long value : values){
			list.getRelationList().add(new Relation(getRelKvo(value)));
		}
		return list;
	}
	
	public static void assertEquals(AssistedObject expected, AssistedObject actual){
		for (String key : expected.getKeys()){
			Assert.assertEquals(expected.getValueAsString(key), actual.getValueAsString(key));
		}
	}
}
