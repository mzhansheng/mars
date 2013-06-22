package com.seekon.mars.wf.engine.internal.svc;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.seekon.mars.wf.engine.Configuration;
import com.seekon.mars.wf.engine.RepositoryService;
import com.seekon.mars.wf.engine.internal.mapper.WFMetaMapper;
import com.seekon.mars.wf.engine.model.CompoModel;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.TemplateModel;

public class RepositoryServiceImpl implements RepositoryService {

	public static Map templateCache = new ConcurrentHashMap(30);
	
	public static Map compoCache = new ConcurrentHashMap(30);
	
	private WFMetaMapper metaDao;

	public RepositoryServiceImpl() {
	}

	public WFMetaMapper getMetaDao() {
		return metaDao;
	}

	public void setMetaDao(WFMetaMapper metaDao) {
		this.metaDao = metaDao;
	}

	public synchronized TemplateModel getTemplate(Long templateId) {
		boolean isCache = this.isCache();
		TemplateModel template = null;
		if (isCache) {
			template = (TemplateModel) templateCache.get(templateId);
		}
		if (template == null) {
			template = metaDao.getTemplateById(templateId);
		}
		
		if(template != null){
		  template.assembleGraph();
      templateCache.put(templateId, template);
		}
    return template;
  }

  public TemplateModel getTemplateByCompo(String compoId) {
    CompoModel compo = this.getCompo(compoId);
    if(compo == null){
      return null;
    }
    return this.getTemplate(compo.getTempolateId());
  }

  public synchronized CompoModel getCompo(String compoId){
    CompoModel compo = null;
    boolean isCache = this.isCache();
    if(isCache){
      compo = (CompoModel) compoCache.get(compoId);
    }
    if(compo == null){
      compo = metaDao.getCompoById(compoId);
      compoCache.put(compoId, compo);
    }
    return compo;
  }
  
  private boolean isCache() {
    return Boolean.valueOf((String) Configuration.get("isCache")).booleanValue();
  }
	
  public List getTemplateList(Long id){
    return this.metaDao.getTemplateList(id);
  }

  public List getNodeOfTemplateList(Long templateId){
    return metaDao.getNodeListByTemplateId(templateId);
  }

  public LinkModel getPreLinkOfCurNode(Long instanceId) {
    return metaDao.getPreLinkOfCurNode(instanceId);
  }
}
