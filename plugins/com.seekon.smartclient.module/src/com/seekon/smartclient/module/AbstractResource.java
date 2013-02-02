package com.seekon.smartclient.module;

import java.io.Serializable;

public abstract class AbstractResource implements Serializable, Cloneable {

	private static final long serialVersionUID = -7524540752496321827L;

	protected String id;

	protected String name;

	protected AbstractResource parent;
	
	public AbstractResource() {
		super();
	}

	public AbstractResource(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AbstractResource getParent() {
    return parent;
  }

  public void setParent(AbstractResource parent) {
    this.parent = parent;
  }

  public AbstractResource getContainsResource(AbstractResource resource){
		if(this.equals(resource)){
			return this;
		}
		return null;
	}
	
	public boolean contains(AbstractResource resource) {
		return this.equals(resource);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractResource other = (AbstractResource) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return this.name;
	}
}
