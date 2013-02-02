package com.seekon.system.org.model;

import java.io.Serializable;
import java.util.Date;

public class AbstractOrgItem implements Serializable, Cloneable {

  private static final long serialVersionUID = -3720631318351951987L;

  private String id;

  private String code;

  private String name;

  private int year;

  protected String type;

  private String creator;

  private Date createTime;

  private String lastModifier;

  private Date lastModifyTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getLastModifier() {
    return lastModifier;
  }

  public void setLastModifier(String lastModifier) {
    this.lastModifier = lastModifier;
  }

  public Date getLastModifyTime() {
    return lastModifyTime;
  }

  public void setLastModifyTime(Date lastModifyTime) {
    this.lastModifyTime = lastModifyTime;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    //if (getClass() != obj.getClass())
    //   return false;
    AbstractOrgItem other = (AbstractOrgItem) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(name);
    if (code != null) {
      sb.append("[" + code + "]");
    }
    return sb.toString();
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
