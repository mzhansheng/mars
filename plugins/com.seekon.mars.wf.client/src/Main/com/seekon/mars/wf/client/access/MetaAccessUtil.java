package com.seekon.mars.wf.client.access;

import java.util.List;

import com.seekon.mars.wf.client.internal.ServiceFacade;
import com.seekon.mars.wf.engine.model.TemplateModel;
import com.seekon.mars.wf.service.MetaService;

public class MetaAccessUtil {
  
  public static List getTemplate(Long tempalteId) {
    return getMetaService().getTemplateList(tempalteId);
  }

  public static List getNodeList(Long templateId) {
    return getMetaService().getNodeList(templateId);
  }
  
  public static TemplateModel getTemplateById(Long templateId){
    return getMetaService().getTemplate(templateId);
  }
  
  private static MetaService getMetaService(){
    return ServiceFacade.getMetaService();
  }
}
