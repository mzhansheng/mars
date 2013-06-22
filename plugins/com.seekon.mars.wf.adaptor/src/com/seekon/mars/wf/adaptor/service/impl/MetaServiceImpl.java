package com.seekon.mars.wf.adaptor.service.impl;

import java.util.List;

import com.seekon.mars.wf.engine.WFClient;
import com.seekon.mars.wf.engine.model.CompoModel;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.TemplateModel;
import com.seekon.mars.wf.service.MetaService;

public class MetaServiceImpl implements MetaService{

  public List getNodeList(Long templateId) {
    return WFClient.getWFEngine().getRepositoryService().getNodeOfTemplateList(templateId);
  }

  public List getTemplateList(Long tempalteId) {
    return WFClient.getWFEngine().getRepositoryService().getTemplateList(tempalteId);
  }

  public CompoModel getCompo(String compoId) {
    return WFClient.getWFEngine().getRepositoryService().getCompo(compoId);
  }

  public TemplateModel getTemplate(Long templateId) {
    return WFClient.getWFEngine().getRepositoryService().getTemplate(templateId);
  }

  public TemplateModel getTemplateByCompo(String compoId) {
    return WFClient.getWFEngine().getRepositoryService().getTemplateByCompo(compoId);
  }

  public LinkModel getPreLinkOfCurNode(Long instanceId) {
    return WFClient.getWFEngine().getRepositoryService().getPreLinkOfCurNode(instanceId);
  }

}
