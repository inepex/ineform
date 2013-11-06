package com.inepex.ineom.shared.assistedobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AssistedObjectUtil {
	
	public static List<Long> getObjectIds(List<AssistedObject> objList){
		List<Long> idList = new ArrayList<Long>();
		for(AssistedObject obj : objList){
			idList.add(obj.getId());
		}
		return idList;
	}
	
	public static String createIdListString(List<AssistedObject> objList) {
		return createIdListString(getObjectIds(objList));
	}
	
	public static String createIdListString(Collection<Long> ids) {
		StringBuffer sb = new StringBuffer();
		for(Long l : ids) {
			if(sb.length()>0)
				sb.append(";");
			sb.append(l);
		}
			
		return sb.toString();
	}

}
