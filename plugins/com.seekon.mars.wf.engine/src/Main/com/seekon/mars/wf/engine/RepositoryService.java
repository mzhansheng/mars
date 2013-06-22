package com.seekon.mars.wf.engine;

import java.util.List;

import com.seekon.mars.wf.engine.model.CompoModel;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.TemplateModel;

/**
 * 工作流模板管理
 * 
 * @author Administrator
 * 
 */
public interface RepositoryService {

	public TemplateModel getTemplate(Long templateId);// 得到工作流模板

	public TemplateModel getTemplateByCompo(String compoId);// 根据部件代码获取工作流模板
	
	public CompoModel getCompo(String compoId); //获取部件对象
	
	public List getTemplateList(Long id);

	public List getNodeOfTemplateList(Long templateId);
	
	public LinkModel getPreLinkOfCurNode(Long instanceId);//得到当前节点的前置LINK
}
