package com.seekon.smartclient.gui.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public   class TreeNodeMap implements Map {

  private Map delegate;

  private List<Map> children = null;

  public TreeNodeMap(Map delegate) {
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
  
  public List<Map> getChildren() {
    return children;
  }

  public void setChildren(List<Map> children) {
    this.children = children;
  }

  public void addChild(Map child) {
    children.add(child);
  }

  public Map getChild(int index) {
    return children.get(index);
  }

  public int getChildCount() {
    return children.size();
  }

  @Override
  public String toString() {
    return this.get("name") == null ? super.toString() : this.get("name")
      .toString();
  }
}
