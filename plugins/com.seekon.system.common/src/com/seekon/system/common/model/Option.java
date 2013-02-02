package com.seekon.system.common.model;

import java.io.Serializable;

public class Option implements Serializable {

  private static final long serialVersionUID = -7683919821863874348L;

  private String id;

  private String optCode;

  private Object optValue;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOptCode() {
    return optCode;
  }

  public void setOptCode(String optCode) {
    this.optCode = optCode;
  }

  public Object getOptValue() {
    return optValue;
  }

  public void setOptValue(Object optValue) {
    this.optValue = optValue;
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
    Option other = (Option) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
