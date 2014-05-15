package com.inepex.ineom.shared.assistedobject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class AssistedObjectUtilTest {
	
	@Test
	public void utilTest(){
		List<Long> ids = new ArrayList<>();
		ids.add(1l);
		ids.add(2l);
		ids.add(3l);
		String result = AssistedObjectUtil.createIdListString(ids);
		Assert.assertEquals("1;2;3", result);
		
		List<AssistedObject> objects = new ArrayList<>();
		objects.add(new KeyValueObject("", 1l));
		objects.add(new KeyValueObject("", 2l));
		objects.add(new KeyValueObject("", 3l));
		
		List<Long> idListResult = AssistedObjectUtil.getObjectIds(objects);
		Assert.assertEquals(3, idListResult.size());
		Assert.assertEquals(1l, idListResult.get(0).longValue());
		Assert.assertEquals(2l, idListResult.get(1).longValue());
		Assert.assertEquals(3l, idListResult.get(2).longValue());
		
		result = AssistedObjectUtil.getObjectIdsAsString(objects);
		Assert.assertEquals("1;2;3", result);

	}

}
