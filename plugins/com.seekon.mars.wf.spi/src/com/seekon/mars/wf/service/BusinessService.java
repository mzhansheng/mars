package com.seekon.mars.wf.service;

import java.util.List;
import java.util.Map;

import com.seekon.mars.wf.model.AsCompoModel;

public interface BusinessService {
  public Object getCompanyById(String parentId,Integer nd);
  /**
   * 得到所有单�?
   * @return
   */
  public List getLevelCompany(String parentId,Integer nd);

  /**
   * 得到所有下级单�?
   * @param companyId
   * @return
   */
  public List getDownLevelCompany(String companyId,Integer nd);

  /**
   * 得到直接上级单位
   * @param companyId
   * @return
   */
  public Object getUpLevelCompany(String companyId,Integer nd);
  
  /**
   * 得到所有上级级单位�?
   * @param companyId
   * @return
   */
  public List getUpLevelCompanyTree(String companyId,Integer nd);

  /**
   * 得到单位的所有组�?
   * @param companyId
   * @return
   */
  public List getAllOrg(String companyId,Integer nd);

  /**
   * 得到单位的某组织的下级单�?
   * @param companyId
   * @param orgId
   * @return
   */
  public List getDownOrg(String companyId, String orgId,Integer nd);

  /**
   * 得到单位的某组织的上级单�?
   * @param companyId
   * @param orgId
   * @return
   */
  public Object getUpOrg(String companyId, String orgId,Integer nd);
  
  public List getUpOrgTree(String companyId, String orgId,Integer nd);

  /**
   * 得到某组织的职位
   * @param orgId
   * @return
   */
  public List getPosition(String positionId,String orgId,String onOff,Integer nd);

  /**
   * 得到某组织的人员
   * @param positionId
   * @return
   */
  public List getStaff(String staffid,String positionId, String coCode, String orgCode,String onOff,Integer nd);
  
  /**
   * 查找compId对应的模�?
   * @param compId
   * @return
   */
  public AsCompoModel getAsCompoFromId(String compId);
  
  /**
   * 实例是否已经开�?
   * @param draftId
   * @return
   */
  public boolean isStartedTrance(Integer draftId);
  
//  String getTemplate(Long tempId) throws Exception;
  
  public Map getNodeLocationMap(Long templateId) throws Exception;
  
  public boolean isUserOfInstance(String userId,String compoId,Long instanceId);
  
  public Long getCurNodeIdFromNoCommitTask(Long instanceId);
  
  public int findCurTaskOfUser(Long instanceId,String userId);
  
  public boolean isInstanceFinish(Long instanceId);
  
  public boolean isCreatorOfCurTask(Long instanceId,String userId);
  
  public List getCurNodeIdFromNoCommitTask(Map map);
}
