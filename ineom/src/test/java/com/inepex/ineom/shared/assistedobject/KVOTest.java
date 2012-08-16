package com.inepex.ineom.shared.assistedobject;

import junit.framework.Assert;

import org.junit.Test;

import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.BooleanFDesc;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.DescriptorStore.Marker;
import com.inepex.ineom.shared.descriptor.DoubleFDesc;
import com.inepex.ineom.shared.descriptor.ListFDesc;
import com.inepex.ineom.shared.descriptor.LongFDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.descriptor.StringFDesc;

public class KVOTest {
	
	//----------------------------------------
	//"injected"
	//----------------------------------------
	static final DescriptorStore descriptorStore;
	static final AssistedObjectHandlerFactory objectHandlerFactory;
	
	static final String k_long="lo";
	static final String k_string="s";
	static final String k_double="b";
	static final String k_bool="b";
	static final String k_rel="r";
	static final String k_list="li";
	
	static {
		descriptorStore=new ClientDescriptorStore();
		
		ObjectDesc descriptor2 = new ObjectDesc("test2Kvo");
		descriptor2.addField(new LongFDesc(k_long, "A long"));
		
		ObjectDesc descriptor = new ObjectDesc("testKvo");
		descriptor
			.addField(new StringFDesc(k_string, "A string"))
			.addField(new LongFDesc(k_long, "DispName"))
			.addField(new DoubleFDesc(k_double, "DispName"))
			.addField(new BooleanFDesc(k_bool, "DispName"))
			.addField(new RelationFDesc(k_rel, "A relation", "test2Kvo"))
			.addField(new ListFDesc(k_list, "A list", "test2Kvo"));
		
		descriptorStore.registerDescriptors(Marker.registered, descriptor2);
		descriptorStore.registerDescriptors(Marker.registered, descriptor);
		
		objectHandlerFactory = new AssistedObjectHandlerFactory(descriptorStore);
	}
	
	//----------------------------------------
	//helpers
	//----------------------------------------
	private static IneList getList(Long ... values){
		IneList list = new IneList();
		for (Long value : values){
			list.getRelationList().add(new Relation(getRelKvo(value)));
		}
		return list;
	}
	
	private static KeyValueObject getRelKvo(Long value){
		KeyValueObject kvo = new KeyValueObject("test2Kvo");
		kvo.set(k_long, value);
		return kvo;
	}

	//----------------------------------------
	//tests
	//----------------------------------------
	@Test
	public void testEmptyKVO() {
		KeyValueObject kvo = new KeyValueObject("descName");
		
		Assert.assertEquals(false, kvo.containsBoolean("a"));
		Assert.assertEquals(false, kvo.containsDouble("a"));
		Assert.assertEquals(false, kvo.containsList("a"));
		Assert.assertEquals(false, kvo.containsLong("a"));
		Assert.assertEquals(false, kvo.containsRelation("a"));
		Assert.assertEquals(false, kvo.containsString("a"));
		
		Assert.assertEquals(0, kvo.getKeys().size());
		
		KeyValueObject otherKvo = new KeyValueObject("descName");
		kvo.copyValuesTo(otherKvo);
		
		Assert.assertEquals(0, kvo.getKeys().size());
		Assert.assertEquals(0, otherKvo.getKeys().size());
		
		Assert.assertEquals(true, kvo.equals(otherKvo));
		Assert.assertEquals(true, otherKvo.equals(kvo));
		Assert.assertEquals(true, kvo.equals(kvo));
		Assert.assertEquals(true, otherKvo.equals(otherKvo));
	}
	
	@Test
	public void testCopy() {
		KeyValueObject kvo = new KeyValueObject("testKvo");
		kvo.set(k_string, "StringValue");
		kvo.set(k_double, 1.234);
		kvo.set(k_list, getList(4L, 5L));
		kvo.set(k_rel, new Relation(99L, ""));
		kvo.set(k_bool, false);
		kvo.set(k_long, 44L);
		
		//copy to empty
		KeyValueObject otherKvo = new KeyValueObject("testKvo");
		
		kvo.copyValuesTo(otherKvo);
		
		Assert.assertEquals(true, kvo.equals(otherKvo));
		Assert.assertEquals(true, otherKvo.equals(kvo));
		
		Assert.assertEquals(true, kvo.equals(kvo));
		Assert.assertEquals(true, otherKvo.equals(otherKvo));
		
		//setting other's vaules
		otherKvo.set(k_double, 45.34);
		otherKvo.set(k_bool, true);
		otherKvo.set(k_long, 2345534L);
		
		Assert.assertEquals(false, kvo.equals(otherKvo));
		Assert.assertEquals(false, otherKvo.equals(kvo));
		
		Assert.assertEquals(true, kvo.equals(kvo));
		Assert.assertEquals(true, otherKvo.equals(otherKvo));
		
		//overwriting previous settings
		kvo.copyValuesTo(otherKvo);
		
		Assert.assertEquals(true, kvo.equals(otherKvo));
		Assert.assertEquals(true, otherKvo.equals(kvo));
		
		Assert.assertEquals(true, kvo.equals(kvo));
		Assert.assertEquals(true, otherKvo.equals(otherKvo));
	}
	
	@Test
	public void testCopy2() {
		KeyValueObject kvo = new KeyValueObject("testKvo");
		kvo.set(k_string, "StringValue");
		kvo.set(k_list, getList(4L, 5L));
		kvo.set(k_rel, new Relation(99L, ""));
		kvo.set(k_bool, false);
		
		kvo.set(k_double, 1.234);
		kvo.set(k_long, 44L);
		
		KeyValueObject twoFieldKvo = new KeyValueObject("testKvo");
		twoFieldKvo.set(k_double, -123.1);
		twoFieldKvo.set(k_long, -123L);
		
		twoFieldKvo.copyValuesTo(kvo);
		Assert.assertEquals("StringValue", kvo.getString(k_string));
		Assert.assertEquals(2, kvo.getList(k_list).getRelationList().size());
		Assert.assertEquals(99, kvo.getRelation(k_rel).getId().intValue());
		Assert.assertEquals(false, kvo.getBoolean(k_bool).booleanValue());
		
		Assert.assertEquals(-123.1, kvo.getDouble(k_double));
		Assert.assertEquals(-123, kvo.getLong(k_long).intValue());
	}
	
	@Test
	public void testEquals() {
		KeyValueObject kvo = new KeyValueObject("testKvo");
		kvo.set(k_string, "StringValue");
		kvo.set(k_list, getList(4L, 5L));
		kvo.set(k_rel, new Relation(99L, ""));
		kvo.set(k_bool, false);
		
		kvo.set(k_double, 1.234);
		kvo.set(k_long, 44L);
		
		KeyValueObject other = new KeyValueObject("testKvo");
		other.set(k_string, "StringValue");
		other.set(k_list, getList(4L, 5L));
		other.set(k_rel, new Relation(99L, ""));
		other.set(k_bool, false);
		other.set(k_double, 1.234);
		other.set(k_long, 44L);
		
		Assert.assertEquals(true, kvo.equals(other));
		Assert.assertEquals(true, other.equals(kvo));
	}
}
