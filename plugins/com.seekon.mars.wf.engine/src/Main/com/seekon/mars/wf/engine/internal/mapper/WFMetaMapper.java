package com.seekon.mars.wf.engine.internal.mapper;

import java.util.List;

import com.seekon.mars.wf.engine.model.CompoModel;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.TemplateModel;

/**
 * 
 * 主要负责工作流模板的组装
 */
public interface WFMetaMapper {
	
	public TemplateModel getTemplateById(Long id);
	
	public List getTemplateList(Long id);

	public List getNodeListByTemplateId(Long templateId);
	
	public CompoModel getCompoById(String compoId);
	
	public LinkModel getPreLinkOfCurNode(Long instanceId);
}
