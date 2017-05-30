package com.inepex.ineom.shared.assistedobject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class AssistedObjectUtilTest {

    @Test
    public void utilTest() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        String result = AssistedObjectUtil.createIdListString(ids);
        Assert.assertEquals("1;2;3", result);

        List<AssistedObject> objects = new ArrayList<>();
        objects.add(new KeyValueObject("", 1L));
        objects.add(new KeyValueObject("", 2L));
        objects.add(new KeyValueObject("", 3L));

        List<Long> idListResult = AssistedObjectUtil.getObjectIds(objects);
        Assert.assertEquals(3, idListResult.size());
        Assert.assertEquals(1L, idListResult.get(0).longValue());
        Assert.assertEquals(2L, idListResult.get(1).longValue());
        Assert.assertEquals(3L, idListResult.get(2).longValue());

        result = AssistedObjectUtil.getObjectIdsAsString(objects);
        Assert.assertEquals("1;2;3", result);

    }

}
