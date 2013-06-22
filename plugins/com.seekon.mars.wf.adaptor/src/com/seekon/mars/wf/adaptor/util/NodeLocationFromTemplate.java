package com.seekon.mars.wf.adaptor.util;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.model.Point;
import com.seekon.mars.wf.service.BusinessService;
/**
 * 得到node的坐标
 * @author Administrator
 *
 */
public class NodeLocationFromTemplate {
  private static BusinessService wfTraceService;
  private static String NODEPATH = "//xpdl:Activities/xpdl:Activity";
  private static String XPATH = "xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XOffset']";
  private static String YPATH = "xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='YOffset']";
  private static NodeLocationFromTemplate instance;
//  private NodeLocationFromTemplate(){
////    RemoteServiceFactory remoteFacotry=new RemoteServiceFactory();
////    wfTraceService=remoteFacotry.getService(BusinessService.class);
//  }
  
  public static Map getNodeLocationMap(String xml) throws Exception {
    SAXBuilder builder = new SAXBuilder();
    Document doc = builder.build(new StringReader(xml));
    Map result = new HashMap();
    XPath nodePath = XPath.newInstance(NODEPATH);
    nodePath.addNamespace("xpdl", "http://www.wfmc.org/2002/XPDL1.0");
    XPath xPath = XPath.newInstance(XPATH);
    xPath.addNamespace("xpdl", "http://www.wfmc.org/2002/XPDL1.0");
    XPath yPath = XPath.newInstance(YPATH);
    yPath.addNamespace("xpdl", "http://www.wfmc.org/2002/XPDL1.0");
    
    Element ne = null;
    Element xe = null;
    Element ye = null;
    List nodeList = nodePath.selectNodes(doc);
    for (int i = 0; i < nodeList.size(); i++) {
      ne = (Element)nodeList.get(i);
      NodeModel node = new NodeModel();
      node.setNodeId(new Long(ne.getAttributeValue("Id")));
      node.setName(ne.getAttributeValue("Name"));
      
      xe = (Element)xPath.selectSingleNode(ne);
      ye = (Element)yPath.selectSingleNode(ne);
      Point location=new Point(Integer.parseInt(xe.getAttributeValue("Value")), 
                                Integer.parseInt(ye.getAttributeValue("Value")));
      
      
      result.put(node, location);
    }
    return result;
  }
  
//  private String getTemplateXml(Long templateId) throws Exception{
//   return wfTraceService.getTemplate(templateId); 
//  }
//  
//  public static Map getNodeLocationMap(Long templateId) throws Exception{
//    instance=new NodeLocationFromTemplate();
//    String xml=instance.getTemplateXml(templateId);
//    return instance.getNodeLocationMap(xml);
//  }
  
  
//  public static void main(String []args) throws Exception{
//    Map map=NodeLocationFromTemplate.getNodeLocationMap(new Long(2023));
//    Iterator itr=map.keySet().iterator();
//    while(itr.hasNext()){
//      NodeModel k=(NodeModel)itr.next();
//      Point p=(Point)map.get(k);
//      System.out.println(k.getName()+"["+k.getNodeId()+"]"+":"+"("+p.getX()+","+p.getY()+")");
//    }
//  }
}
