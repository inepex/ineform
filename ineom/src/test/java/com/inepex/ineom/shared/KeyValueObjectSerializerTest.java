package com.inepex.ineom.shared;

import junit.framework.Assert;
import org.junit.Test;

public class KeyValueObjectSerializerTest {

    @Test
    public void defaultSerialize() {
        String serialized = new KeyValueObjectSerializer(TestUtil.getTestKvo(), "{sep}", "=")
            .serializeToString();
        System.out.println(serialized);
        Assert.assertEquals("stringField=hello{sep}listField=2 items", serialized);
    }

    @Test
    public void customListSerializer() {
        String serialized = new KeyValueObjectSerializer(TestUtil.getTestKvo(), "{sep}", "=")
            .setListSerializer(list -> {
                StringBuffer sb = new StringBuffer();
                for (Relation rel : list.getRelationList()) {
                    AssistedObjectHandler handler = TestUtil
                        .createObjectHandlerFactory()
                        .createHandler(rel.getKvo());
                    sb.append(handler.getValueAsString("longField"));
                    sb.append(",");
                }
                return sb.toString();
            })
            .serializeToString();
        Assert.assertEquals("stringField=hello{sep}listField=4,5,", serialized);
    }

    @Test
    public void customRelationSerializer() {
        String serialized = new KeyValueObjectSerializer(TestUtil.getTestKvo(), "{sep}", "=")
            .setRelationSerializer(relation -> {
                AssistedObjectHandler handler = TestUtil
                    .createObjectHandlerFactory()
                    .createHandler(relation.getKvo());
                return "value in rel is: " + handler.getValueAsString("longField");
            })
            .serializeToString();
        Assert.assertEquals(
            "stringField=hello{sep}relField=value in rel is: 3{sep}listField=2 items",
            serialized);
    }
}
