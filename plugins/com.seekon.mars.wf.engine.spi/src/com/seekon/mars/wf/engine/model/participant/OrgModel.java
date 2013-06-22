package com.seekon.mars.wf.engine.model.participant;

import java.util.Map;

import com.seekon.mars.wf.engine.model.ExecutorSourceModel;

/**
 * 
 * 内部机构模型，内部机构区分上下级次与年度
 *
 */
public class OrgModel implements java.io.Serializable {

  private static final long serialVersionUID = 5841951201303077752L;
  
  private String id;//作为主键
  
  private String code;
  
  private String name;
  
  private int nd;
  
  private String description;
  
  private OrgModel parent;
	
  private CompanyModel company;//直接上级单位，若company不为null，则parent应该为null；反之亦然

  public OrgModel(){
    
  }
  
  public OrgModel(Map sessionMap){
    this.company = new CompanyModel(sessionMap);
    this.code = (String)sessionMap.get("svOrgCode");
    this.name = (String)sessionMap.get("svOrgName");
    this.nd = company.getNd();
  }
  
  public OrgModel(ExecutorSourceModel executorSource){
    this.company = new CompanyModel(executorSource);
    this.code = executorSource.getOrgCode();
    this.nd = this.company.getNd();
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

  public int getNd() {
    return nd;
  }

  public void setNd(int nd) {
    this.nd = nd;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public OrgModel getParent() {
    return parent;
  }

  public void setParent(OrgModel parent) {
    this.parent = parent;
  }

  public CompanyModel getCompany() {
    return company;
  }

  public void setCompany(CompanyModel company) {
    this.company = company;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((code == null) ? 0 : code.hashCode());
    result = prime * result + ((company == null) ? 0 : company.hashCode());
    return result;
  }
  
  public boolean equals(Object obj){
    if(obj == null || !(obj instanceof OrgModel)){
      return false;
    }
    
    OrgModel tmp = (OrgModel)obj;
    return (this.code == null && tmp.code == null) || (this.code != null && this.code.equals(tmp.code))
    && ((this.company == null && tmp.company == null) || (this.company != null && this.company.equals(tmp.company)));
  }
}
