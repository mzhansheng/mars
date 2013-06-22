package com.seekon.mars.wf.engine.dao;

import com.seekon.mars.wf.engine.WFClient;
import com.seekon.mars.wf.engine.model.CompoModel;
import com.seekon.mars.wf.engine.model.TemplateModel;

public class TestWFMetaDao extends TestCase{

  public void testGetCompoModel(){
    String compoId = "BI_DBI_ADJ";
    CompoModel compo = WFClient.getWFEngine().getRepositoryService().getCompo(compoId);
    assertNotNull(compo);
  }
  
  public void testGetWFTemplate(){
    Long templateId = new Long("40084");
    TemplateModel temp = WFClient.getWFEngine().getRepositoryService().getTemplate(templateId);
    assertTrue(temp != null && temp.getStartNode() != null && temp.getEndNode() != null);
    assertNotNull(temp.getLink(new Long("40104")));
  }
}
