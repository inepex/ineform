package com.inepex.ineom.shared.kvo;

import junit.framework.Assert;

import org.junit.Test;

import com.inepex.ineom.shared.TestUtil;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;
import com.inepex.ineom.shared.kvo.KeyValueObjectSerializer.ListSerializer;
import com.inepex.ineom.shared.kvo.KeyValueObjectSerializer.RelationSerializer;

public class KeyValueObjectSerializerTest {

	@Test
	public void defaultSerialize(){
		String serialized = new KeyValueObjectSerializer(
				TestUtil.getTestKvo(new ClientDescriptorStore()), 
				"{sep}", "=")
		.serializeToString();
		System.out.println(serialized);
		Assert.assertEquals("stringField=hello{sep}listField=2 items", serialized);
	}
	
	@Test
	public void customListSerializer(){
		String serialized = new KeyValueObjectSerializer(
				TestUtil.getTestKvo(new ClientDescriptorStore()), 
				"{sep}", "=").setListSerializer(new ListSerializer() {
					
					@Override
					public String serialize(IneList list) {
						StringBuffer sb = new StringBuffer();
						for (Relation rel : list.getRelationList()){
							sb.append(rel.getKvo().getValueAsString("longField"));
							sb.append(",");
						}
						return sb.toString();
					}
				})
		.serializeToString();
		Assert.assertEquals("stringField=hello{sep}listField=4,5,", serialized);
	}
	
	@Test
	public void customRelationSerializer(){
		String serialized = new KeyValueObjectSerializer(
				TestUtil.getTestKvo(new ClientDescriptorStore()), 
				"{sep}", "=")
		.setRelationSerializer(new RelationSerializer() {
			
			@Override
			public String serialize(Relation relation) {
				
				return "value in rel is: " + relation.getKvo().getValueAsString("longField");
			}
		})
		.serializeToString();
		Assert.assertEquals("stringField=hello{sep}relField=value in rel is: 3{sep}listField=2 items", serialized);
	}
}
