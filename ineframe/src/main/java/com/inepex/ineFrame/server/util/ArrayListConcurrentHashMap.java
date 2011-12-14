package com.inepex.ineFrame.server.util;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ArrayListConcurrentHashMap<T, K> extends ConcurrentHashMap<T, ArrayList<K>>{
	
	public ArrayListConcurrentHashMap() {
	}
	
	public ArrayList<K> getListById(T id){
		return get(id);
	}
	public boolean isListEmptyById(T id){
		return ((get(id) == null) || (get(id).size() == 0));
	}
	public void addToListById(T id, K element){
		ArrayList<K> list = get(id);
		if(list == null){
			list = new ArrayList<K>();
			put(id, list);
		}
		list.add(element);
	}
	public void addElementListById(T id, ArrayList<K> elementList){
		ArrayList<K> list = get(id);
		if(list == null){
			put(id, elementList);
		}
		else{
			list.addAll(elementList);
		}
	}
}
