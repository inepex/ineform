package com.inepex.ineom.shared.kvo;

import junit.framework.Assert;

import org.junit.Test;

import com.inepex.ineom.shared.AssistedObjectHandlerFactory.AssistedObjectHandler;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.KeyValueObjectSerializer;
import com.inepex.ineom.shared.KeyValueObjectSerializer.ListSerializer;
import com.inepex.ineom.shared.KeyValueObjectSerializer.RelationSerializer;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.T_e_s_tUtil;

public class KeyValueObjectSerializerTest {

	@Test
	public void defaultSerialize(){
		String serialized = new KeyValueObjectSerializer(
				T_e_s_tUtil.getTestKvo(), 
				"{sep}", "=")
		.serializeToString();
		System.out.println(serialized);
		Assert.assertEquals("stringField=hello{sep}listField=2 items", serialized);
	}
	
	@Test
	public void customListSerializer(){
		String serialized = new KeyValueObjectSerializer(
				T_e_s_tUtil.getTestKvo(), 
				"{sep}", "=").setListSerializer(new ListSerializer() {
					
					@Override
					public String serialize(IneList list) {
						StringBuffer sb = new StringBuffer();
						for (Relation rel : list.getRelationList()){
							AssistedObjectHandler handler = T_e_s_tUtil.objectHandlerFactory.createHandler(rel.getKvo());
							sb.append(handler.getValueAsString("longField"));
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
				T_e_s_tUtil.getTestKvo(), 
				"{sep}", "=")
		.setRelationSerializer(new RelationSerializer() {
			
			@Override
			public String serialize(Relation relation) {
				AssistedObjectHandler handler = T_e_s_tUtil.objectHandlerFactory.createHandler(relation.getKvo());
				return "value in rel is: " + handler.getValueAsString("longField");
			}
		})
		.serializeToString();
		Assert.assertEquals("stringField=hello{sep}relField=value in rel is: 3{sep}listField=2 items", serialized);
	}
}
