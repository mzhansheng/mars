package com.seekon.smartclient.framework.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;


public class OrderableMap<K, V> implements Serializable, Map<K, V> {

	private static final long serialVersionUID = 1293157930280540166L;
	List<K> keys = new Vector<K>();
	List<V> values = new Vector<V>();
	private OrderableMap<K, V> self = this;

	public int size() {
		return keys.size();
	}

	public boolean isEmpty() {
		return keys.size() <= 0;
	}

	public boolean containsKey(Object key) {
		return keys.indexOf(key) > -1;
	}

	public boolean containsValue(Object value) {
		return values.indexOf(value) > -1;
	}

	public V get(Object key) {
		int index = keys.indexOf(key);
		if (index > -1) {
			return values.get(index);
		}
		return null;
	}

	/**
	 * 根据参照key put元素，如果ref不存在，将采取添加到最后
	 * 
	 * @param key
	 *          K
	 * @param value
	 *          V
	 * @param ref
	 *          K - 参照key
	 * @param before
	 *          boolean - 是否在参照key前插入
	 * @return V
	 */
	public V put(K key, V value, K ref, boolean before) {
		int index = keys.indexOf(ref);
		if (index > -1) {
			if (!before)
				index++;
			keys.add(index, key);
			values.add(index, value);
			return value;
		} else {
			return put(key, value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public V put(K key, V value) {
		int index = keys.indexOf(key);
		if (index > -1) {
			values.set(index, value);
			return value;
		}

		keys.add(key);
		values.add(value);
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public V remove(Object key) {
		int index = keys.indexOf(key);
		if (index > -1) {
			V value = values.get(index);
			keys.remove(index);
			values.remove(index);
			return value;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet()) {
			V value = m.get(key);
			put(key, value);
		}
	}

	public void clear() {
		keys.clear();
		values.clear();
	}

	public Set<K> keySet() {

		return new OrderableSet<K>(keys);
	}

	public Collection<V> values() {
		return values;
	}

	class XEntry<K, V> implements Map.Entry<K, V> {
		private K key = null;
		private OrderableMap<K, V> map = null;

		public XEntry(K key, OrderableMap<K, V> map) {
			this.key = key;
			this.map = map;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return map.get(key);
		}

		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (!(o instanceof XEntry))
				return false;
			XEntry obj = (XEntry) o;
			return obj.getKey().equals(getKey()) && obj.getValue().equals(getValue());
		}

		public int hashCode() {
			return getKey().hashCode() * getValue().hashCode();
		}

		public V setValue(V value) {
			return map.put(key, value);
		}
	}

	public Set<Map.Entry<K, V>> entrySet() {
		HashSet<Map.Entry<K, V>> retValue = new HashSet<Map.Entry<K, V>>();
		for (K key : keySet()) {
			XEntry<K, V> entry = new XEntry<K, V>(key, self);
			retValue.add(entry);
		}
		return retValue;
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof OrderableMap))
			return false;
		OrderableMap omap = (OrderableMap) o;
		return omap.keys.equals(keys) && omap.values.equals(values);
	}

	public int hashCode() {
		return keys.hashCode() + values.hashCode();
	}

	public static void main(String[] args) {
		OrderableMap<String, String> map = new OrderableMap<String, String>();
		map.put("a", "a");
		map.put("b", "b");
		map.put("d", "d");
		for (String s : map.keys)
			System.out.println(s + ":" + map.get(s));
		System.out
				.println("-------------------------------------------------------------");
		map.put("c", "c", "a", true);
		for (String s : map.keys)
			System.out.println(s + ":" + map.get(s));
	}
}
