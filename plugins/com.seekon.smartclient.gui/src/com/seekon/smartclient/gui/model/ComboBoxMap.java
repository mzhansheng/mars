package com.seekon.smartclient.gui.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ComboBoxMap implements Map{
  private Map delegate;

  public ComboBoxMap(Map delegate) {
    super();
    this.delegate = delegate;
  }

  public int size() {
    return delegate.size();
  }

  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  public boolean containsKey(Object key) {
    return delegate.containsKey(key);
  }

  public boolean containsValue(Object value) {
    return delegate.containsValue(value);
  }

  public Object get(Object key) {
    return delegate.get(key);
  }

  public Object put(Object key, Object value) {
    return delegate.put(key, value);
  }

  public Object remove(Object key) {
    return delegate.remove(key);
  }

  public void putAll(Map m) {
    delegate.putAll(m);
  }

  public void clear() {
    delegate.clear();
  }

  public Set keySet() {
    return delegate.keySet();
  }

  public Collection values() {
    return delegate.values();
  }

  public Set entrySet() {
    return delegate.entrySet();
  }

  public boolean equals(Object o) {
    return delegate.equals(o);
  }

  public int hashCode() {
    return delegate.hashCode();
  }
  
  @Override
  public String toString() {
    Object displayName = this.get("name");
    return displayName != null ? displayName.toString() : super.toString();
  }
}
