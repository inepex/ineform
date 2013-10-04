package com.inepex.ineom.shared.assistedobject;

import java.util.ArrayList;
import java.util.List;

public class AssistedObjectUtil {
	
	public static List<Long> getObjectIds(List<AssistedObject> objList){
		List<Long> idList = new ArrayList<Long>();
		for(AssistedObject obj : objList){
			idList.add(obj.getId());
		}
		return idList;
	}

}
