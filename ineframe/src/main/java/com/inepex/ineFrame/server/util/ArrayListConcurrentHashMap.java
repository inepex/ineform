package com.inepex.ineFrame.server.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author balint.steinbach@inepex.com, istvan.szoboszlai@inepex.com
 *
 * ConcurrentHashMap of a {@link List} of Items. Ensures creation of the list, and provides convenience functions.
 *
 * @param <T> Type of the Key
 * @param <K> Generic type of the List
 */
public class ArrayListConcurrentHashMap<T, K> extends ConcurrentHashMap<T, List<K>>{
	
	private static final long serialVersionUID = 1L;
	
	
	public List<K> getListById(T id){
		return get(id);
	}
	
	public boolean isListEmptyOrNullById(T id){
		List<K> list = get(id);
		return (list == null) || (list.size() == 0);
	}

	public boolean isListNullById(T id){
		return (get(id) == null);
	}
	
	public void addToListById(T id, K element){
		List<K> list = ensureListById(id);
		list.add(element);
	}
	
	/**
	 * Ensures that a list be created for the given id. The list retoruned by this method
	 * can be a parameter of a synchronized block.
	 * 
	 * @param id
	 * @return
	 */
	public List<K> ensureListById(T id) {
		List<K> list = get(id);
		if(list == null) {
			list = new ArrayList<K>(1);
			put(id, list);
		}
		return list;
		
	}
	
	public void addElementListById(T id, List<K> elementList){
		List<K> list = get(id);
		if(list == null){
			put(id, elementList);
		}
		else{
			list.addAll(elementList);
		}
	}
	
	public void addEmptyListById(T id){
		put(id, new ArrayList<K>());
	}
}
