package com.inepex.ineForm.shared;

import java.util.ArrayList;
import java.util.List;

import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;


public abstract class BaseMapper<E> {
	
	public abstract E kvoToEntity(AssistedObject from, E to, CustomKVOObjectDesc... descs) throws Exception;
	
	public abstract AssistedObject entityToKvo(E entity);
	
	public abstract Relation toRelation(E entity, boolean includeKvo);
	
	public List<Relation> toRelationList(List<E> entityList){
		return toRelationList(entityList, false);
	}
	
	public List<Relation> toRelationList(List<E> entityList, boolean includeKvo){
		List<Relation> result = new ArrayList<Relation>();
		for (E entity : entityList) {
			result.add(toRelation(entity, includeKvo));
		}
		return result;
	}
	
	public ArrayList<AssistedObject> entityListToKvoList(List<E> entityList){
		ArrayList<AssistedObject> result = new ArrayList<AssistedObject>();
		for (E o: entityList){
			result.add(entityToKvo(o));
		}
		return result;
	}	

}
