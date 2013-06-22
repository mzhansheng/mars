package com.seekon.mars.wf.service;

import java.util.List;

import com.seekon.mars.wf.engine.model.CompoModel;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.TemplateModel;

/**
 * 
 * 工作流meta信息服务接口，根据此接口可以获取工作流模板及模板相关的信息
 *
 */
public interface MetaService {

  public List getTemplateList(Long tempalteId);
  
  public List getNodeList(Long templateId);
  
  public TemplateModel getTemplate(Long templateId);

  public TemplateModel getTemplateByCompo(String compoId);
  
  public CompoModel getCompo(String compoId);
  
  public LinkModel getPreLinkOfCurNode(Long instanceId);//得到当前节点的前置LINK
}
