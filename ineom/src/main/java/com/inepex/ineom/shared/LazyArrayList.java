package com.inepex.ineom.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LazyArrayList<E> implements List<E>{

	private ArrayList<E> list = null;
	
	@Override
	public int size() {
		if(list==null)
			return 0;
		
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		if(list==null)
			return true;
		
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		if(list==null)
			return false;
		
		return list.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		if(list==null)
			return Collections.<E>emptyList().iterator();
		
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		if(list==null)
			return Collections.emptyList().toArray();
		
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		if(list==null)
			return Collections.emptyList().toArray(a);
		
		return list.toArray(a);
	}

	@Override
	public boolean add(E e) {
		if(list==null)
			list=new ArrayList<E>();
			
		return list.add(e);
	}

	@Override
	public boolean remove(Object o) {
		if(list==null)
			return false;
		
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		if(list==null)
			return false;
		
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if(list==null)
			list=new ArrayList<E>();
			
		return list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if(list==null)
			list=new ArrayList<E>();
			
		return list.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if(list==null)
			return false;
		
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if(list==null)
			return false;
		
		return list.retainAll(c);
	}

	@Override
	public void clear() {
		if(list==null)
			return;
		
		list.clear();
	}

	@Override
	public E get(int index) {
		if(list==null)
			throw new ArrayIndexOutOfBoundsException(index);
		
		return list.get(index);
	}

	@Override
	public E set(int index, E element) {
		if(list==null)
			list=new ArrayList<E>();
		
		return list.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		if(list==null)
			list=new ArrayList<E>();
		
		list.add(index, element);
	}

	@Override
	public E remove(int index) {
		if(list==null)
			throw new ArrayIndexOutOfBoundsException(index);
		
		return list.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		if(list==null)
			return -1;
		
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		if(list==null)
			return -1;
		
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		if(list==null)
			return Collections.<E>emptyList().listIterator();
		
		return list.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		if(list==null)
			return Collections.<E>emptyList().listIterator();
		
		return list.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if(list==null)
			throw new ArrayIndexOutOfBoundsException("empty list. Invalid from:"+fromIndex+" and to: "+toIndex);
		
		return list.subList(fromIndex, toIndex);
	}

}
