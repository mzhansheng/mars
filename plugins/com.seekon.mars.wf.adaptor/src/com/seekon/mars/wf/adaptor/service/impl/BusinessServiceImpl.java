package com.seekon.mars.wf.adaptor.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.seekon.mars.wf.adaptor.mapper.BusinessMapper;
import com.seekon.mars.wf.adaptor.util.NodeLocationFromTemplate;
import com.seekon.mars.wf.model.AsCompoModel;
import com.seekon.mars.wf.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {
  
  @Inject
  private BusinessMapper businessDao;

  public Object getCompanyById(String parentId,Integer nd){
    return businessDao.getCompanyById(parentId, nd);
  }

  public List getLevelCompany(String parentId,Integer nd) {
    return businessDao.getLevelCompany(parentId, nd);
  }

  public List getAllOrg(String companyId,Integer nd) {
    return businessDao.getAllOrg(companyId, nd);
  }

  public List getDownLevelCompany(String companyId,Integer nd) {
    return businessDao.getDownLevelCompany(companyId, nd);
  }

  public List getDownOrg(String companyId, String orgId,Integer nd) {
    return businessDao.getDownOrg(companyId, orgId, nd);
  }

  public List getPosition(String positionId,String orgId,String onOff,Integer nd){
    return businessDao.getPosition(positionId,orgId,onOff, nd);
  }

  public List getStaff(String staffid,String positionId, String coCode, String orgCode,String onOff,Integer nd){
    return businessDao.getStaff(staffid,positionId, coCode, orgCode,onOff, nd);
  }

  public Object getUpLevelCompany(String companyId,Integer nd) {
    return businessDao.getUpLevelCompany(companyId, nd);
  }
  
  public List getUpLevelCompanyTree(String companyId,Integer nd) {
    return businessDao.getUpLevelCompanyTree(companyId, nd);
  }

  public Object getUpOrg(String companyId, String orgId,Integer nd) {
    return businessDao.getUpOrg(companyId, orgId, nd);
  }
  
  public List getUpOrgTree(String companyId, String orgId,Integer nd) {
    return businessDao.getUpOrgTree(companyId, orgId, nd);
  }

  public AsCompoModel getAsCompoFromId(String compId) {
    return businessDao.getAsCompoFromId(compId);
  }

  public boolean isStartedTrance(Integer draftId) {
    return businessDao.isStartedTrance(draftId);
  }

  public Map getNodeLocationMap(Long templateId) throws Exception {
    String templateXml=businessDao.getTemplate(templateId);
    return NodeLocationFromTemplate.getNodeLocationMap(templateXml);
  }
  public boolean isUserOfInstance(String userId, String compoId, Long instanceId) {
    return businessDao.isUserOfInstance(userId, compoId, instanceId);
  }
  public Long getCurNodeIdFromNoCommitTask(Long instanceId) {
    return businessDao.getCurNodeIdFromNoCommitTask(instanceId);
  }
  public int findCurTaskOfUser(Long instanceId, String userId)
  {
     return businessDao.findCurTaskOfUser(instanceId, userId);
  }
  public boolean isInstanceFinish(Long instanceId) {
    return businessDao.isInstanceFinish(instanceId);
  }
  public boolean isCreatorOfCurTask(Long instanceId, String userId) {
    return businessDao.isCreatorOfCurTask(instanceId, userId);
  }
  public List getCurNodeIdFromNoCommitTask(Map map){
    return businessDao.getCurNodeIdFromNoCommitTask(map);
  }

}
