package com.inepex.ineFrame.server.util;

import java.util.ArrayList;
import java.util.Collection;
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
public class ArrayListConcurrentHashMap<T, K> {
	
	private final ConcurrentHashMap<T, List<K>> map = new ConcurrentHashMap<>();
	
	public List<K> getListById(T id){
		return map.get(id);
	}
	
	public boolean containsKey(T id) {
		return map.containsKey(id);
	}
	
	public boolean isListEmptyOrNullById(T id) {
		List<K> list = map.get(id);
		return (list == null) || (list.isEmpty());
	}
	
	public void addToListById(T id, K element){
		List<K> list = ensureListById(id);
		list.add(element);
	}
	
	public void remove(T id) {
		map.remove(id);
	}
	
	/**
	 * Ensures that a list be created for the given id. The list returned by this method
	 * can be a parameter of a synchronized block.
	 * 
	 * @param id
	 * @return
	 */
	public List<K> ensureListById(T id) {
		List<K> list = map.get(id);
		if(list!=null)
			return list;
		
		list = new ArrayList<K>(1);
		List<K> oldVal = map.putIfAbsent(id, list);
		if(oldVal!=null)
			return oldVal;
		else
			return list;
	}

	public void addAllToListById(T id, Collection<K> elements) {
		List<K> list = ensureListById(id);
		list.addAll(elements);
	}
	
	public boolean contains(T id, K element){
		if(isListEmptyOrNullById(id)) return false;
		List<K> list = map.get(id);
		if(list==null)
			return false;
		
		return list.contains(element);
	}

	public void clear() {
		map.clear();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}
}
