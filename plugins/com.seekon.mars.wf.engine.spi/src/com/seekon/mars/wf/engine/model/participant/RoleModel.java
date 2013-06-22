package com.seekon.mars.wf.engine.model.participant;

/**
 * 
 * 角色模型
 *
 */
public class RoleModel implements java.io.Serializable {

  private static final long serialVersionUID = -376171697846624466L;

  private String code = null;

	private String name = null;

	private String description = null;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((code == null) ? 0 : code.hashCode());
    return result;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RoleModel other = (RoleModel) obj;
    if (code == null) {
      if (other.code != null)
        return false;
    } else if (!code.equals(other.code))
      return false;
    return true;
  }

}
