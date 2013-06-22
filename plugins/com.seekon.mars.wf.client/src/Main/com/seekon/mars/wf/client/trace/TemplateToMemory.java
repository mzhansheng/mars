package com.seekon.mars.wf.client.trace;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.seekon.mars.wf.client.internal.ServiceFacade;
import com.seekon.mars.wf.client.trace.unit.EndUnit;
import com.seekon.mars.wf.client.trace.unit.GraphUnit;
import com.seekon.mars.wf.client.trace.unit.NavigationUnit;
import com.seekon.mars.wf.client.trace.unit.StartUnit;
import com.seekon.mars.wf.client.trace.unit.TaskUnit;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.TemplateModel;
import com.seekon.mars.wf.service.BusinessService;
import com.seekon.mars.wf.service.MetaService;

/**
 * 从模板生成内存中图结构
 * @author Administrator
 */
public class TemplateToMemory {
  private GraphUnit startUnit;

  private GraphUnit endUnit;

  private Map<NodeModel, List<LinkModel>> nodeLinkMap;

  private Map<NodeModel, Point> nodeLocationMap;

  private Stack<GraphUnit> acessStack;

  private TemplateModel template = null;

  private Long templateId;

  public TemplateToMemory(Long templateId) throws Exception {
    nodeLinkMap = new HashMap<NodeModel, List<LinkModel>>();
    acessStack = new Stack<GraphUnit>();
    startUnit = new StartUnit();
    endUnit = new EndUnit();
    this.templateId = templateId;
    processTemplate();
  }

  /**
   * 遍历图
   */
  //  public void traverseLevelTemplate() {
  //    Stack stk = getAcessStack();
  //    while (!stk.isEmpty()) {
  //      GraphUnit unit = (GraphUnit) stk.pop();
  //      System.out.println(((NodeModel) unit.getModel()).getName());
  //      System.out.println(unit.getLocation());
  //    }
  //  }
  public void initNodeLinkMap(NodeModel curNode, List<LinkModel> linkList) {
    Iterator<LinkModel> itr = linkList.iterator();
    List<LinkModel> tmpLinkList = new ArrayList<LinkModel>();
    while (itr.hasNext()) {
      LinkModel tmpLink = (LinkModel) itr.next();
      NodeModel tmpNode = tmpLink.getCurrentNode();
      if (tmpNode != null && tmpNode.getNodeId().equals(curNode.getNodeId())) {
        tmpLinkList.add(tmpLink);
        NodeModel nextNode=tmpLink.getNextNode();
        if (nextNode != null)
          initNodeLinkMap(nextNode, linkList);
      }
    }
    nodeLinkMap.put(curNode, tmpLinkList);
  }

  private void processDividNode(GraphUnit curNodeUnit, List<LinkModel> nextLinkList, List<LinkModel> linkList) {
    Iterator itr = nextLinkList.iterator();
    while (itr.hasNext()) {
      LinkModel linkTmp = (LinkModel) itr.next();
      NodeModel tmpCurNode = linkTmp.getCurrentNode();
      NodeModel nextNode = linkTmp.getNextNode();
      if (nextNode != null) {
        Point p = nodeLocationMap.get(nextNode);
        GraphUnit unit = null;
        if (Integer.parseInt(nextNode.getType()) == GraphUnit.UNITTYPE_END) {
          unit = new EndUnit();
          unit.setModel(nextNode);
          unit.addPrivousUnit(curNodeUnit);
        } else if (Integer.parseInt(nextNode.getType()) == GraphUnit.UNITTYPE_TASK) {
          unit = new TaskUnit();
          unit.addPrivousUnit(curNodeUnit);
          unit.setModel(nextNode);
          unit.setLocation(p);
          curNodeUnit.addNextUnit(unit);
          processTaskNode(unit, linkList);
        } else if (Integer.parseInt(nextNode.getType()) == GraphUnit.UNITTYPE_NAVIGATION) {
          unit = new NavigationUnit();
          unit.addPrivousUnit(curNodeUnit);
          unit.setModel(nextNode);
          unit.setLocation(p);
          curNodeUnit.addNextUnit(unit);
          processTaskNode(unit, linkList);
        }
        if (unit!=null && !acessStack.contains(unit))
          acessStack.push(unit);
      }
    }
  }

  private void processTaskNode(GraphUnit curNodeUnit, List<LinkModel> linkList) {
    NodeModel curNode = (NodeModel) curNodeUnit.getModel();
    Iterator itr = linkList.iterator();
    while (itr.hasNext()) {
      LinkModel linkTmp = (LinkModel) itr.next();
      NodeModel curLinkNode=linkTmp.getCurrentNode();
      if (curLinkNode!=null && curLinkNode.getNodeId().equals(curNode.getNodeId())) {
      
        List<LinkModel> nextLinkList = nodeLinkMap.get(curNode);
        if (nextLinkList.size() > 1) {
          processDividNode(curNodeUnit, nextLinkList, linkList);
        } else {
          if (nextLinkList.size() == 1) {
            LinkModel nextLink = nextLinkList.get(0);
            NodeModel nextNode = nextLink.getNextNode();
            Point p = nodeLocationMap.get(nextNode);
            GraphUnit unit = null;
            if (nextNode != null) {
              if (Integer.parseInt(nextNode.getType()) == GraphUnit.UNITTYPE_END) {
                unit = new EndUnit();
                unit.setModel(nextNode);
                unit.addPrivousUnit(curNodeUnit);
              } else if (Integer.parseInt(nextNode.getType()) == GraphUnit.UNITTYPE_TASK) {
                unit = new TaskUnit();
                unit.addPrivousUnit(curNodeUnit);
                unit.setModel(nextNode);
                unit.setLocation(p);
                curNodeUnit.addNextUnit(unit);
                processTaskNode(unit, linkList);
              } else if (Integer.parseInt(nextNode.getType()) == GraphUnit.UNITTYPE_NAVIGATION) {
                unit = new NavigationUnit();
                unit.addPrivousUnit(curNodeUnit);
                unit.setModel(nextNode);
                unit.setLocation(p);
                curNodeUnit.addNextUnit(unit);
                processTaskNode(unit, linkList);
              }
              if (unit != null && !acessStack.contains(unit))
                acessStack.push(unit);
            }
          }
        }
      }
    }
  }

  public TemplateModel getTemplate() {
    if (template == null) {
      template=ServiceFacade.getMetaService().getTemplate(templateId);
    }
    return template;
  }

  public void processTemplate() throws Exception {
    TemplateModel template = getTemplate();
    List linkList = template.getLinkList();
    NodeModel startNode = template.getStartNode();
    if(startNode==null) throw new Exception("模板错误，请联系管理员！");
    initNodeLinkMap(startNode, linkList);
    try {
      nodeLocationMap = ServiceFacade.getBusinessService().getNodeLocationMap(templateId);
    } catch (Exception e) {
      e.printStackTrace();
    }

    TaskUnit taskUnit = new TaskUnit();
    taskUnit.setModel(startNode);
    startUnit.addNextUnit(taskUnit);
    taskUnit.addPrivousUnit(taskUnit);
    Point p = nodeLocationMap.get(startNode);
    taskUnit.setLocation(p);

    //处理其他节点
    processTaskNode(taskUnit, linkList);
    if (!acessStack.contains(taskUnit))
      acessStack.push(taskUnit);
  }

  public GraphUnit getStartUnit() {
    return startUnit;
  }

  public Stack<GraphUnit> getAcessStack() {
    return acessStack;
  }

  //  public static void main(String[] args) {
  //    TemplateToMemory tm = new TemplateToMemory(new Long(2023));
  //    tm.traverseLevelTemplate();
  //  }
}
