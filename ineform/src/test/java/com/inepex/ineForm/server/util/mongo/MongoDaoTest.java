package com.inepex.ineForm.server.util.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.inepex.ineForm.server.prop.mongo.PropDao;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;

public class MongoDaoTest {
	
//	@Test
	public void test(){
		PropDao dao = new PropDao("localhost", "", "");
		List<AssistedObject> obj = new ArrayList<>();
		obj.add(new KeyValueObject("treeElementDescriptor", 28L));
		obj.add(new KeyValueObject("treeElementDescriptor", 30L));
		dao.mapPropGroups(obj, Arrays.asList("admin"));
		System.out.println(obj);
	}

}
