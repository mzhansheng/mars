package com.seekon.mars.wf.engine.model.participant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.seekon.mars.wf.engine.model.ExecutorSourceModel;

/**
 * 
 * 职位模型，某一职位可以挂接多个角色，也可以挂接到多个内部机构上
 *
 */
public class PositionModel implements java.io.Serializable {

  private static final long serialVersionUID = 8573387977169934296L;

  private String code = null;

	private String name = null;

	private String description = null;
	
	private List roleList = new ArrayList();
	
	public PositionModel(){
	  
	}
	
	public PositionModel(Map sessionMap){
	  this.code = (String)sessionMap.get("svPoCode");
    this.name = (String)sessionMap.get("svPoName");
	}
	
	public PositionModel(ExecutorSourceModel executorSource){
	  this.code = executorSource.getPosiCode();
	}
	
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

  public List getRoleList() {
    return roleList;
  }

  public void setRoleList(List roleList) {
    this.roleList = roleList;
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((code == null) ? 0 : code.hashCode());
    return result;
  }

  public boolean equals(Object obj){
    if(obj == null || !(obj instanceof PositionModel)){
      return false;
    }
    
    PositionModel tmp = (PositionModel)obj;
    return (this.code == null && tmp.code == null)
      || (this.code != null && this.code.equals(tmp.code));
  }
  
}
