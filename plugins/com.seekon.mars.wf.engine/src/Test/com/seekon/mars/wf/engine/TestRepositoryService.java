package com.seekon.mars.wf.engine;

import com.seekon.mars.wf.engine.model.TemplateModel;

public class TestRepositoryService extends TestCase{

  public void testGetTemplate(){
    Long templateId = Long.valueOf("2023");
    
    RepositoryService service = WFClient.getWFEngine().getRepositoryService();
    TemplateModel template = service.getTemplate(templateId);
    
    assertNotNull(template);
    assertNotNull(template.getStartNode());
    //assertEquals(template.getStartNode().getNodeId(), Long.valueOf("41195158"));
    assertNotNull(template.getEndNode());
    //assertEquals(template.getEndNode().getNodeId(), Long.valueOf("41195163"));
    assertTrue(template.getNodeList().size() > 0);
    assertTrue(template.getLinkList().size() > 0);
  }
}
