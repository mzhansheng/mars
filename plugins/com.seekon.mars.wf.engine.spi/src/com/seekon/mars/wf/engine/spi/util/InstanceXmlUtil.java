package com.seekon.mars.wf.engine.spi.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.TemplateModel;
import com.seekon.mars.wf.engine.model.runtime.ActionHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceTraceModel;
import com.seekon.mars.wf.engine.model.runtime.PassModel;

public class InstanceXmlUtil {

  private static Document xmlDocFromString(String xmlStr) {
    try {
      return DocumentHelper.parseText(xmlStr);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static Map initNodeList(Document xmlDoc, TemplateModel template) {
    Map transMap = new HashMap();
    Element root = xmlDoc.getRootElement();
    Iterator nodeItr = root.elementIterator();
    while (nodeItr.hasNext()) {
      Element nodeElem = (Element) nodeItr.next();
      Long tmpNodeId = new Long(nodeElem.attributeValue("ID"));//得到节点ID属性
      NodeModel node = template.getNode(tmpNodeId);
      List nodeNextLinkList = new ArrayList();
      Iterator linkItr = nodeElem.elementIterator("link");
      while (linkItr.hasNext()) {
        Element linkElem = (Element) linkItr.next();
        Long tmpLinkId = new Long(linkElem.attributeValue("ID"));//得到linkId属性
        LinkModel link = template.getLink(tmpLinkId);
        nodeNextLinkList.add(link);
      }
      node.setToLinkList(nodeNextLinkList);
      transMap.put(node.getNodeId(), node);
    }

    return transMap;
  }
  
  private static void parseXmlFromNode(List actionList, Document xmlDoc, Long nodeId){
    Node root = xmlDoc.selectSingleNode("/instance");
    List nodeList = root.selectNodes("node[@ID='"+nodeId.toString()+"']/action");
    if(nodeList!=null && nodeList.size()>0){
      Iterator itr=nodeList.iterator();
      while(itr.hasNext()){
        Element actionElem=(Element)itr.next();
        ActionModel action = new ActionModel();
        action.setActionId(new Long(actionElem.attributeValue("ID")));
//        action.setNodeId(new Long(actionElem.attributeValue("NODEID")));
        action.setNodeId(nodeId);
        action.setActionName(actionElem.attributeValue("NAME"));
        action.setExecutor(actionElem.attributeValue("EXECUTOR"));
        action.setDescription(actionElem.attributeValue("DESCRIPTION"));
        action.setOwner(actionElem.attributeValue("OWNER"));
        action.setLimitExecuteTime(actionElem.attributeValue("LIMIT_EXECUTE_TIME"));
        action.setCoCode(actionElem.attributeValue("CO_CODE"));
        action.setOrgCode(actionElem.attributeValue("ORG_CODE"));
        action.setPosiCode(actionElem.attributeValue("POSI_CODE"));
        action.setNd(Integer.parseInt(actionElem.attributeValue("ND")));
        actionList.add(action);
      }
    }
  }
  
  private static void processNodeLinkList(LinkedList nodeLinkedList,List actionList,Document xmlDoc, Map nodeMap, Long startNodeId,Long targetNodeId){
      NodeModel node=(NodeModel)nodeMap.get(startNodeId);
      List nodeLinkList=node.getToLinkList();
      Iterator linkItr=nodeLinkList.iterator();
      while(linkItr.hasNext()){
        LinkModel linkModel=(LinkModel)linkItr.next();
        Long curNodeId=linkModel.getCurrentNodeId();
        nodeLinkedList.addLast(nodeMap.get(curNodeId));
        if(curNodeId.equals(targetNodeId)){
          parseXmlFromNode(actionList,xmlDoc,curNodeId);
          break;
        }
        else{
          Long nextNodeId=linkModel.getNextNodeId();
          processNodeLinkList(nodeLinkedList,actionList,xmlDoc, nodeMap, nextNodeId,targetNodeId);
        }
      }
  }

  private static LinkedList creatNodeKinkList(List actionList,Document xmlDoc, Map nodeMap, Long startNodeId, Long targetNodeId) {
    LinkedList linkList = new LinkedList();
    if (startNodeId.equals(targetNodeId)) {
      linkList.addLast(nodeMap.get(startNodeId));
      parseXmlFromNode(actionList,xmlDoc,startNodeId);
    } else {
      processNodeLinkList(linkList,actionList,xmlDoc,nodeMap,startNodeId,targetNodeId);
    }
    return linkList;
  }

  /**
   * 实例重做时初始化必要信息
   * @param env 环境上下文
   * @param xmlStr 历史执行路径xml字符串
   * @param nodeId 实例重做的开始结点
   * @param actionList 实例重做要恢复的action信息 
   * @return 实例执行过的有序序列
   */
  public static LinkedList instanceTransList(List actionList,TemplateModel template, String xmlStr, Long startNodeId,
    Long targetNodeId) {
    Document xmlDoc = xmlDocFromString(xmlStr);
    Map nodeMap=initNodeList(xmlDoc,template);
    return creatNodeKinkList(actionList,xmlDoc,nodeMap,startNodeId,targetNodeId);
  }

  /**
   * InstanceTraceModel对象转换为xml格式的字符串
   * @param instanceTrace
   * @return
   */
  public static String instanceTrace2Xml(InstanceTraceModel instanceTrace) {
    Element root = DocumentHelper.createElement("instance");
    root.add(DocumentHelper.createAttribute(root, "ID", String.valueOf(instanceTrace.getInstanceId())));
    Document doc = DocumentHelper.createDocument(root);

    Map actionMap = new HashMap();
    Iterator actionIterator = instanceTrace.getActionList().iterator();//处理action列表
    while (actionIterator.hasNext()) {
      ActionHistoryModel action = (ActionHistoryModel) actionIterator.next();
      Long nodeId = action.getNodeId();
      Set actionSet = (Set) actionMap.get(nodeId);
      if (actionSet == null) {
        actionSet = new HashSet();
      }
      actionSet.add(action);
      actionMap.put(nodeId, actionSet);
    }

    Iterator passIterator = instanceTrace.getPassList().iterator();//组装document
    while (passIterator.hasNext()) {
      PassModel pass = (PassModel) passIterator.next();
      String nodeId = String.valueOf(pass.getCurrentNodeId());
      Element node = (Element) doc.selectSingleNode("node[@ID='" + nodeId + "']");
      //      Iterator nodeIterator = root.elementIterator("node");
      //      while(nodeIterator.hasNext()){
      //        Element tmp = (Element)nodeIterator.next();
      //        if(nodeId.equals(tmp.attribute("ID").getValue())){
      //          node = tmp;
      //          break;
      //        }
      //      }

      if (node == null) {
        node = DocumentHelper.createElement("node");
        node.add(DocumentHelper.createAttribute(node, "ID", nodeId));
        root.add(node);
      }

      Element link = DocumentHelper.createElement("link");
      link.add(DocumentHelper.createAttribute(link, "ID", String.valueOf(pass.getNodeLinkId())));
      node.add(link);

      Object tmpActionSet = actionMap.get(pass.getCurrentNodeId());
      if (tmpActionSet == null) {
        continue;
      }

      Iterator iterator = ((Set) tmpActionSet).iterator();
      while (iterator.hasNext()) {
        ActionHistoryModel actionModel = (ActionHistoryModel) iterator.next();
        Element action = DocumentHelper.createElement("action");
        action.add(DocumentHelper.createAttribute(action, "ID", String.valueOf(actionModel.getActionId())));
        action.add(DocumentHelper.createAttribute(action, "NODEID", actionModel.getNodeId().toString()));
        action.add(DocumentHelper.createAttribute(action, "NAME", actionModel.getActionName()));
        action.add(DocumentHelper.createAttribute(action, "EXECUTOR", actionModel.getExecutor()));
        action.add(DocumentHelper.createAttribute(action, "DESCRIPTION", actionModel.getExecuteTime()));
        action.add(DocumentHelper.createAttribute(action, "OWNER", actionModel.getOwner()));
        action.add(DocumentHelper.createAttribute(action, "LIMIT_EXECUTE_TIME", actionModel
          .getLimitExecuteTime()));
        action.add(DocumentHelper.createAttribute(action, "CO_CODE", actionModel.getCoCode()));
        action.add(DocumentHelper.createAttribute(action, "ORG_CODE", actionModel.getOrgCode()));
        action.add(DocumentHelper.createAttribute(action, "POSI_CODE", actionModel.getPosiCode()));
        action.add(DocumentHelper.createAttribute(action, "ND", String.valueOf(actionModel.getNd())));
        node.add(action);
      }
    }

    return document2String(doc);
  }

  /**
   * 将document转换为xml格式的字符串
   * @param document
   * @return
   */
  public static String document2String(Document document) {
    OutputFormat format = OutputFormat.createPrettyPrint();
    format.setEncoding("utf-8");
    StringWriter stringWriter = new StringWriter();
    XMLWriter writer = new XMLWriter(stringWriter, format);
    try {
      writer.write(document);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        writer.close();
      } catch (IOException e) {

      }
    }
    return stringWriter.toString();
  }
}
