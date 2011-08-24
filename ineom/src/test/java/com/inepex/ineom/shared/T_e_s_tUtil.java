package com.inepex.ineom.shared;

import junit.framework.Assert;

import com.inepex.ineom.shared.AssistedObjectHandlerFactory.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.ListFDesc;
import com.inepex.ineom.shared.descriptor.LongFDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.descriptor.StringFDesc;

public class T_e_s_tUtil {
	
	public static DescriptorStore descriptorStore;
	public static AssistedObjectHandlerFactory objectHandlerFactory;
	
	static {
		descriptorStore=new ClientDescriptorStore();
		
		ObjectDesc descriptor2 = new ObjectDesc("test2Kvo");
		descriptor2.addField(new LongFDesc("longField", "A long"));
		
		ObjectDesc descriptor = new ObjectDesc("testKvo");
		descriptor
			.addField(new StringFDesc("stringField", "A string"))
			.addField(new RelationFDesc("relField", "A relation", "test2Kvo"))
			.addField(new ListFDesc("listField", "A list", "test2Kvo"));
		
		descriptorStore.registerDescriptors(descriptor2);
		descriptorStore.registerDescriptors(descriptor);
		
		objectHandlerFactory = new AssistedObjectHandlerFactory(descriptorStore);
	}
	
	

	public static AssistedObjectChecker getTestKvo(){
		AssistedObjectHandler checker = objectHandlerFactory.createHandler(new KeyValueObject("testKvo"));
		
		checker.set("stringField", "hello");
		checker.set("relField", new Relation(getRelKvo(3L).getAssistedObject()));
		checker.set("listField", getList(4L, 5L));
		return checker;
	}
	
	private static AssistedObjectChecker getRelKvo(Long value){
		AssistedObjectHandler checker = objectHandlerFactory.createHandler(new KeyValueObject("test2Kvo"));
		checker.set("longField", value);
		return checker;
	}
	
	private static IneList getList(Long ... values){
		IneList list = new IneList();
		for (Long value : values){
			list.getRelationList().add(new Relation(getRelKvo(value).getAssistedObject()));
		}
		return list;
	}
	
	public static void assertEquals(AssistedObject expected, AssistedObject actual){
		Assert.assertEquals(expected.getKeys().size(), actual.getKeys().size());
		
		AssistedObjectHandler handlerE = objectHandlerFactory.createHandler(expected);
		AssistedObjectHandler handlerA = objectHandlerFactory.createHandler(actual);
		
		for (String key : expected.getKeys()){
			Assert.assertEquals(true, actual.getKeys().contains(key));
			Assert.assertEquals(handlerE.getValueAsString(key), handlerA.getValueAsString(key));
		}
	}
}
