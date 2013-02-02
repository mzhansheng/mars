package com.seekon.system.auth.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.Resource;

public class ResourceWrapper implements Serializable, Cloneable {

  private static final long serialVersionUID = -2754818260302700777L;

  private AbstractResource resource;

  private List<ResourceWrapper> children = new ArrayList<ResourceWrapper>();

  private boolean assigned;

  public ResourceWrapper() {
    super();
  }

  public ResourceWrapper(AbstractResource resource) {
    super();
    this.resource = resource;
    wrapper();
  }

  public void setResource(AbstractResource resource) {
    this.resource = resource;
    wrapper();
  }

  private void wrapper() {
    if (resource instanceof Resource) {
      List<AbstractResource> resourceChildren = ((Resource) resource).getChildren();
      for (AbstractResource child : resourceChildren) {
        children.add(new ResourceWrapper(child));
      }
    }
  }

  public AbstractResource getResource() {
    return resource;
  }

  public ResourceWrapper getChild(int index) {
    return this.children.get(index);
  }

  public int getChildCount() {
    return this.children.size();
  }

  public boolean contains(ResourceWrapper resourceWrapper) {
    return this.resource.contains(resourceWrapper.getResource());
  }

  public void cleanUnAssignedResource() {
    Iterator<AbstractResource> iteratorRes = null;
    if (resource instanceof Resource) {
      iteratorRes = ((Resource) resource).getChildren().iterator();
    }

    Iterator<ResourceWrapper> iterator = children.iterator();
    while (iterator.hasNext()) {
      iteratorRes.next();
      ResourceWrapper child = iterator.next();
      if (!child.getAssigned()) {
        iterator.remove();
        iteratorRes.remove();
      } else {
        child.cleanUnAssignedResource();
      }
    }
  }

  public int hashCode() {
    return resource.hashCode();
  }

  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof ResourceWrapper) {
      return resource.equals(((ResourceWrapper) obj).getResource());
    }
    return false;
  }

  public String toString() {
    return getDiaplayMessage(1);
  }

  private String getDiaplayMessage(int depth) {
    StringBuffer gapString = new StringBuffer();
    for (int i = 0; i < depth; i++) {
      gapString.append("  ");
    }

    StringBuffer sb = new StringBuffer();
    sb.append(gapString + "+" + resource.getName() + "\n");
    for (ResourceWrapper sw : children) {
      sb.append(gapString + "   " + sw.getDiaplayMessage(depth + 1));
    }
    return sb.toString();
  }

  public boolean getAssigned() {
    return assigned;
  }

  public void setAssigned(boolean assigned) {
    this.assigned = assigned;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
