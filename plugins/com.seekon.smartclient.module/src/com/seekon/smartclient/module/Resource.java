package com.seekon.smartclient.module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Resource extends AbstractResource {

  private static final long serialVersionUID = -5648036851052865808L;

  private List<AbstractResource> children = new ArrayList<AbstractResource>();

  public Resource() {
    super();
  }

  public Resource(String id, String name) {
    super(id, name);
  }
  
  public Resource(String id, String name, boolean addDefaultActionChildren){
    super(id, name);
    if(addDefaultActionChildren){
      this.addChild(ActionHelper.getAddAction());
      this.addChild(ActionHelper.getDeleteAction());
      this.addChild(ActionHelper.getEditAction());
      this.addChild(ActionHelper.getWatchAction());
      this.addChild(ActionHelper.getHelpAction());
    }
  }
  public List<AbstractResource> getChildren() {
    return children;
  }

  public void setChildren(List<AbstractResource> children) {
    this.children = children;
  }

  public void addChild(AbstractResource resource) {
    this.addChild(resource, false);
  }

  public void removeChild(AbstractResource resource){
    Iterator<AbstractResource> iterator = this.children.iterator();
    while(iterator.hasNext()){
      AbstractResource tmp = iterator.next();
      if(resource.equals(tmp)){
        iterator.remove();
        break;
      }
    }
    resource.parent = null;
  }
  
  public void addChild(AbstractResource resource, boolean depth) {

    if (depth) {
      AbstractResource convertRes = getContainsResource(resource);
      if (convertRes != null) {
        if (convertRes instanceof Resource) {//找到符合的child并且为resource则进行合并
          mergeChildren((Resource) convertRes, resource);
          return;
        } else {
          if (resource instanceof Resource) {
            //nothing
          } else {//都为abstract，直接返回
            return;
          }
        }
      }
    }

    if (!this.children.contains(resource)) {
      addChild(this, resource);
    }
  }

  private void mergeChildren(Resource resource1, AbstractResource resource2) {
    List<AbstractResource> children = resource1.getChildren();
    if (resource2 instanceof Resource) {
      for (AbstractResource child : ((Resource) resource2).getChildren()) {
        if (!children.contains(child)) {
          addChild(resource1, child);
        }
      }
    } else {
      if (!children.contains(resource2)) {
        addChild(resource1, resource2);
      }
    }
  }

  private void addChild(Resource resource,  AbstractResource child){
    List<AbstractResource> children = resource.getChildren();
    children.add(child);
    child.parent = resource;
  }
  
  public AbstractResource getChild(int index) {
    return this.children.get(index);
  }

  public int getChildCount() {
    return this.children.size();
  }

  public AbstractResource getContainsResource(AbstractResource resource) {// 递归查找所有的resource
    if (resource == null) {
      return null;
    }
    if (this.equals(resource)) {
      return this;
    }
    for (AbstractResource child : children) {
      AbstractResource obj = child.getContainsResource(resource);
      if (obj != null) {
        return obj;
      }
    }
    return null;
  }

  public boolean contains(AbstractResource resource) {
    return getContainsResource(resource) != null;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
