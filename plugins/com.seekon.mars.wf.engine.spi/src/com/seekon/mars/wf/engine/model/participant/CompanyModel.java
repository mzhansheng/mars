package com.seekon.mars.wf.engine.model.participant;

import java.util.Map;

import com.seekon.mars.wf.engine.model.ExecutorSourceModel;
import com.seekon.mars.wf.engine.spi.util.DateUtil;

/**
 * 
 * 单位模型，单位区分上下级次与年度
 *
 */
public class CompanyModel implements java.io.Serializable {

  private static final long serialVersionUID = -8896608879145692973L;

  private String code = null;

  private String name = null;

  private String description = null;

  private int nd;

  private CompanyModel parent = null;

  public CompanyModel() {

  }

  public CompanyModel(Map sessionMap) {
    this.code = (String) sessionMap.get("svCoCode");
    this.name = (String) sessionMap.get("svCoName");
    try {
      this.nd = Integer.parseInt((String) sessionMap.get("nd"));
    } catch (Exception e) {
      this.nd = DateUtil.getYear();
    }
  }

  public CompanyModel(ExecutorSourceModel executorSource) {
    this.code = executorSource.getCoCode();
    this.nd = executorSource.getNd();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  public int getNd() {
    return nd;
  }

  public void setNd(int nd) {
    this.nd = nd;
  }

  public CompanyModel getParent() {
    return parent;
  }

  public void setParent(CompanyModel parent) {
    this.parent = parent;
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((code == null) ? 0 : code.hashCode());
    result = prime * result + nd;
    return result;
  }

  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof CompanyModel)) {
      return false;
    }

    CompanyModel tmp = (CompanyModel) obj;
    return ((this.code == null && tmp.code == null) || (this.code != null && this.code
      .equals(tmp.code)))
      && this.nd == tmp.nd;
  }
}
