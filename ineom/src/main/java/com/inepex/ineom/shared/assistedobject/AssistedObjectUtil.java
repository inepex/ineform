package com.inepex.ineom.shared.assistedobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.inepex.ineom.shared.Relation;

public class AssistedObjectUtil {
	
	public static List<Long> getObjectIds(Collection<AssistedObject> objList){
		List<Long> idList = new ArrayList<Long>();
		for(AssistedObject obj : objList){
			idList.add(obj.getId());
		}
		return idList;
	}
	
	public static String getObjectIdsAsString(Collection<AssistedObject> objList){
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for(AssistedObject obj : objList) {
			sb.append(obj.getId());
			if(i < objList.size() - 1)
				sb.append(";");
			i++;
		}
		return sb.toString();
	}
	
	public static String getIdFieldAsString(Collection<AssistedObject> objList, String idDescName){
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for(AssistedObject obj : objList) {
			sb.append(obj.getLongUnchecked(idDescName));
			if(i < objList.size() - 1)
				sb.append(";");
			i++;
		}
		return sb.toString();
	}

	
	public static String createIdListString(Collection<Long> ids) {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for(Long l : ids) {
			sb.append(l);
			if(i < ids.size() - 1)
				sb.append(";");
			i++;
		}
			
		return sb.toString();
	}
	
	public static List<Relation> mapObjectsToRelation(Collection<AssistedObject> objList){
		List<Relation> relations = new ArrayList<>();
		for(AssistedObject obj : objList){
			relations.add(new Relation(obj));
		}
		return relations;
	}

}
