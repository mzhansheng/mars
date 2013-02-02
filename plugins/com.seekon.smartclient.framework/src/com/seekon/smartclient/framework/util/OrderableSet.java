package com.seekon.smartclient.framework.util;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;


public class OrderableSet<E> extends AbstractSet<E> implements Set<E>,
		Serializable {

	private static final long serialVersionUID = -6337714692411577025L;
	private List<E> values = new Vector<E>();

	public OrderableSet() {

	}

	public OrderableSet(Collection<? extends E> c) {
		addAll(c);
	}

	@Override
	public boolean add(E e) {
		if (!contains(e))
			return values.add(e);
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean retValue = false;
		for (E e : c) {
			if (add(e))
				retValue = true;
		}
		return retValue;
	}

	@Override
	public void clear() {
		values.clear();
	}

	@Override
	public boolean contains(Object o) {
		return values.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return values.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return values.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return values.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return values.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return values.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return values.retainAll(c);
	}

	@Override
	public int size() {
		return values.size();
	}

	@Override
	public Object[] toArray() {
		return values.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return values.toArray(a);
	}

}
