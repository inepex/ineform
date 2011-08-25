package com.inepex.ineom.shared;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class NullableHashMap<K, V> implements Map<K, V>, Serializable {
	
	private HashMap<K, V> map;

	@Override
	public void clear() {
		if(map==null)
			return;
		
		map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		if(map==null)
			return false;
		
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		if(map==null)
			return false;
		
		return map.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		if(map==null)
			return new HashSet<Map.Entry<K,V>>(0);
		
		return map.entrySet();
	}

	@Override
	public V get(Object key) {
		if(map==null)
			return null;
		
		return map.get(key);
	}

	@Override
	public boolean isEmpty() {
		if(map==null)
			return true;
		
		return map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		if(map==null)
			return new HashSet<K>(0);
		
		return map.keySet();
	}

	@Override
	public V put(K key, V value) {
		if(map==null)
			map= new HashMap<K, V>();
		
		return map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		if(m==null)
			return;
		
		if(map==null)
			map= new HashMap<K, V>();
		
		map.putAll(m);
	}

	@Override
	public V remove(Object key) {
		if(map==null)
			return null;
		
		return map.remove(key);
	}

	@Override
	public int size() {
		if(map==null)
			return 0;
		
		return map.size();
	}

	@Override
	public Collection<V> values() {
		if(map==null)
			return new LinkedList<V>();
		
		return map.values();
	}
	
	@Override
	public String toString() {
		if(map==null)
			return "{}";
		
		return map.toString();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NullableHashMap other = (NullableHashMap) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		
		return true;
	}
}