package com.seekon.mars.wf.client.trace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;

import org.jgraph.JGraph;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;

import com.seekon.mars.wf.client.access.TraceAccessUtil;
import com.seekon.mars.wf.client.trace.unit.EndUnit;
import com.seekon.mars.wf.client.trace.unit.GraphUnit;
import com.seekon.mars.wf.client.trace.unit.LinkUnit;
import com.seekon.mars.wf.client.trace.unit.TaskUnit;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.TemplateModel;
import com.seekon.mars.wf.engine.model.participant.NodeStaffModel;
import com.seekon.mars.wf.engine.model.runtime.ActionHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;

/**
 * 绘制流程图
 * @author Administrator
 *
 */
public class TranceGraphProcessor extends JPanel {
  private int curX = 10;//绘制开始坐标X

  private int curY = 5;//绘制开始坐标Y

  private Map<GraphUnit, DefaultGraphCell> graphCellMap;

  private Map<DefaultGraphCell, DefaultPort> cellPortMap;

  private Map<LinkUnit, TraceGraphEdge> edgeMap;

  private Map<Long, ActionHistoryModel> instanceActionMap;

  private Map<Long, List<CurrentTaskModel>> instanceCurTaskMap;

  private TraceGraph traceGraph;//工作流流程跟踪
  
  private boolean isInstanceFinish=false; //流程跟踪实例是否结束

  private TemplateToMemory memoryGraph;//工作流流程图内存描述

  private Map attrGraphMap = new HashMap();
  
  private Map<Long,List<NodeStaffModel>> curTaskExecutorListMap;

  private DefaultGraphModel graphModel;

  private LinkUnit testLink;

  public TranceGraphProcessor(TemplateToMemory memoryGraph) {
    this.memoryGraph = memoryGraph;
    cellPortMap = new HashMap<DefaultGraphCell, DefaultPort>();
    graphCellMap = new HashMap<GraphUnit, DefaultGraphCell>();
    edgeMap = new HashMap<LinkUnit, TraceGraphEdge>();
    setSize(800, 700);
    this.setLayout(new BorderLayout());
    graphModel = new DefaultGraphModel();
  }

  /**
   * 展示模板流程
   * @throws IOException 
   */
  public void showTemplateGraph() throws IOException {
    this.add(new JScrollPane(createTemplateTraceGraph()), BorderLayout.CENTER);
    ToolTipManager.sharedInstance().registerComponent(traceGraph);
  }

  /**
   * 展示实例流程
   * @param instanceId 模板实例ID
   * @throws IOException 
   */
  public void showTraceGraph(Long instanceId,String userId) throws IOException {
    this.add(new JScrollPane(createInstanceTranceGraph(instanceId,userId)), BorderLayout.CENTER);
    ToolTipManager.sharedInstance().registerComponent(traceGraph);
  }

  private void initCellPortMap(Stack<GraphUnit> graphStack) {
    while (!graphStack.isEmpty()) {
      GraphUnit tmpUnit = (GraphUnit) graphStack.pop();
      TraceGraphCell cell = new TraceGraphCell(tmpUnit, attrGraphMap);
      DefaultPort port = new DefaultPort();
      cell.add(port);
      
      NodeModel node=(NodeModel)tmpUnit.getModel();
      if(curTaskExecutorListMap!=null && curTaskExecutorListMap.get(node.getNodeId())!=null){
        cell.addCurTaskExecutor(curTaskExecutorListMap.get(node.getNodeId()));
      }
      if(instanceCurTaskMap!=null && instanceCurTaskMap.get(node.getNodeId())!=null){
        TaskUnit taskUnit=(TaskUnit)tmpUnit;
        taskUnit.setCommitTaskIcon();
        cell.refreshIcon(taskUnit.getIcon());
        Point point = tmpUnit.getLocation();
        Rectangle2D targetBounds = new Rectangle2D.Double(point.getX(), point.getY(), TraceGraphCell.ICON_WIDTH,
          TraceGraphCell.COMMIT_ICON_HEIGHT);
        cell.refreshBounds(targetBounds);
      }
      graphCellMap.put(tmpUnit, cell);
      cellPortMap.put(cell, port);
      Object[] cells = new Object[] { cell };
      graphModel.insert(cells, attrGraphMap, null, null, null);
    }
  }

  private boolean isEndNode(NodeModel nNode){
    TemplateModel template = memoryGraph.getTemplate();
    return template.getEndNode().equals(nNode);
  }
  
  private LinkModel processAutoJumpNextNode(NodeModel nNode) {
    LinkModel tempLink = null;
    boolean isJump = false;
    TemplateModel template = memoryGraph.getTemplate();
    NodeModel endNode=template.getEndNode();
    List linkList = template.getLinkList();
    Iterator itr = linkList.iterator();
    while (itr.hasNext()) {
      LinkModel link = (LinkModel) itr.next();
      if (link.getCurrentNodeId().equals(nNode.getNodeId())) {
        NodeModel nextNode = link.getNextNode();
        if(nextNode != null && curTaskExecutorListMap.get(nextNode.getNodeId())!=null){
          tempLink = link;
          isJump = true;
        }
        else{
          if ((nextNode != null && instanceActionMap.get(nextNode.getNodeId()) != null)
                //&& (nextNode != null && nextNode.getNodeId().equals(endNode.getNodeId()))
                ) {
            tempLink = link;
            isJump = true;
          }
        }
      }
      if (isJump)
        break;
    }
    return tempLink;
  }

  private String getLinkExpress(NodeModel curNode, NodeModel nextNode) {
    String express = null;
    TemplateModel template = memoryGraph.getTemplate();
    List linkList = template.getLinkList();
    Iterator itr = linkList.iterator();
    while (itr.hasNext()) {
      LinkModel link = (LinkModel) itr.next();
      if (link.getCurrentNodeId().equals(curNode.getNodeId())
        && link.getNextNodeId().equals(nextNode.getNodeId())) {
        express = link.getExpression();
      }
    }
    return express;
  }

  //////////////////////////////////////INSTANCE_GRAPH_START/////////////////////////////////////////////
  public JGraph createInstanceTranceGraph(Long instanceId,String userId) throws IOException {
    traceGraph = new TraceGraph(graphModel,userId);
    traceGraph.setSelectionEnabled(true);
    //    traceGraph.addMouseListener(new GraphMouseEvent());
    traceGraph.setLocation(0, 0);
    instanceTraceAttribute(instanceId, memoryGraph.getStartUnit());
    return traceGraph;
  }

  private void instanceTraceAttribute(Long instaceId, GraphUnit startUnit) throws IOException {
    instanceCurTaskMap = TraceAccessUtil.instanceCurTaskMap(instaceId);
    instanceActionMap = TraceAccessUtil.instanceActionMap(instaceId);
    curTaskExecutorListMap= TraceAccessUtil.getCurTaskAllExecutorMap(instaceId);
    isInstanceFinish=TraceAccessUtil.isInstanceFinish(instaceId);
    templateTraceAttribute(startUnit);

  }

  //////////////////////////////////////INSTANCE_GRAPH_END/////////////////////////////////////////////

  //////////////////////////////////////TEMPLATE_GRAPH_START/////////////////////////////////////////////
  /**
   * 绘制工作流流程图
   * @return
   * @throws IOException 
   */
  public JGraph createTemplateTraceGraph() throws IOException {
    traceGraph = new TraceGraph(graphModel);
    traceGraph.setSelectionEnabled(true);
    traceGraph.setLocation(0, 0);
    templateTraceAttribute(memoryGraph.getStartUnit());
    return traceGraph;
  }

  public TraceGraph getTraceGraph() {
    if (traceGraph == null) {
      try {
        traceGraph = (TraceGraph) createTemplateTraceGraph();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return traceGraph;
  }

  private void templateTraceAttribute(GraphUnit startUnit) throws IOException {
    boolean isStart = false;
    LinkUnit passLink=null;
    GraphUnit endPrevUnit = null;
    TraceGraphCell curCell = null;
    DefaultGraphCell sourceCell = createGraphCell(startUnit);
    DefaultPort sourcePort = new DefaultPort();
    sourceCell.add(sourcePort);
    Object[] cells = new Object[] { sourceCell };
    graphModel.insert(cells, attrGraphMap, null, null, null);
    Stack<GraphUnit> graphStack = memoryGraph.getAcessStack();
    Stack<GraphUnit> tempStack = (Stack<GraphUnit>) graphStack.clone();
    initCellPortMap(tempStack);
    while (!graphStack.isEmpty()) {
      GraphUnit tmpUnit = (GraphUnit) graphStack.pop();
      if (graphStack.size() == 0)
        endPrevUnit = tmpUnit;
      TraceGraphCell targetCell = (TraceGraphCell) graphCellMap.get(tmpUnit);
      DefaultPort targetPort = (DefaultPort) cellPortMap.get(targetCell);
      targetCell.add(targetPort);

      if (!isStart) {
        LinkUnit link = new LinkUnit(startUnit, tmpUnit);
        testLink = link;
        TraceGraphEdge edge = new TraceGraphEdge(link, attrGraphMap);
        if (instanceActionMap != null) {
          edge.setLineColor(Color.GREEN);
          edge.setLineLabel("启动");
        }
        edgeMap.put(testLink, edge);
        ConnectionSet cs = new ConnectionSet(edge, sourcePort, targetPort);
        Object[] edgeCells = new Object[] { edge, targetCell };
        graphModel.insert(edgeCells, attrGraphMap, cs, null, null);
        isStart = true;
      }
      LinkUnit linkUnit=processCurGraphUnit(targetCell, targetPort,passLink);
      if(linkUnit!=null)passLink=linkUnit;
    }

    GraphUnit endUnit = new EndUnit();
    endUnit.setLocation(new Point((int)(endPrevUnit.getLocation().getX() + 160), (int)endPrevUnit.getLocation().getY()));
    DefaultGraphCell endCell = createGraphCell(endUnit);
    DefaultPort endPort = new DefaultPort();
    endCell.add(endPort);
    LinkUnit link = new LinkUnit(endPrevUnit, endUnit);
    TraceGraphEdge endEdge = new TraceGraphEdge(link, attrGraphMap);
    if(isInstanceFinish)endEdge.setLineColor(Color.GREEN);
    //    endEdge.setLineLabel("完成");
    TraceGraphCell endPrevCell = (TraceGraphCell) graphCellMap.get(endPrevUnit);
    DefaultPort endPrevtargetPort = (DefaultPort) cellPortMap.get(endPrevCell);
    ConnectionSet cs = new ConnectionSet(endEdge, endPrevtargetPort, endPort);
    Object[] endEdgeCells = new Object[] { endEdge, endCell };
    graphModel.insert(endEdgeCells, attrGraphMap, cs, null, null);
  }

  /**
   * 处理流程
   * @param sourceCell
   * @param sourcePort
   */
  private LinkUnit processCurGraphUnit(TraceGraphCell sourceCell, DefaultPort sourcePort,LinkUnit passLink) {
    LinkUnit returnLinkUnit=null;
    GraphUnit curUnit = sourceCell.getUserGraphUnit();
    List<LinkUnit> curNextUnitLst = curUnit.getNextUnitList();
    Iterator itr = curNextUnitLst.iterator();
    while (itr.hasNext()) {
      LinkUnit linkUnit = (LinkUnit) itr.next();
      if(passLink!=null && passLink.equals(linkUnit))continue;
      GraphUnit unit = linkUnit.getNextUnit();
      TraceGraphCell targetCell = (TraceGraphCell) graphCellMap.get(unit);
      DefaultPort targetPort = (DefaultPort) cellPortMap.get(targetCell);
      targetCell.add(targetPort);
      LinkUnit link = new LinkUnit(curUnit, unit);
      TraceGraphEdge edge = new TraceGraphEdge(link, attrGraphMap);
      if (instanceActionMap != null) {
        NodeModel pNode = (NodeModel) linkUnit.getPreviousUnit().getModel();
        NodeModel nNode = (NodeModel) linkUnit.getNextUnit().getModel();
        ActionHistoryModel pActionTmp = instanceActionMap.get(pNode.getNodeId());
        ActionHistoryModel nActionTmp = instanceActionMap.get(nNode.getNodeId());
        if (pActionTmp != null && nActionTmp != null) {
          edge.setLineColor(Color.GREEN);
          edge.setLineLabel(pActionTmp.getActionName());
          edge.setUserModel(pActionTmp);
        } else {
          if(pActionTmp != null && isEndNode(nNode)){
            if(!"untread_flow".equals(pActionTmp.getActionName())){
              edge.setLineColor(Color.GREEN);
              edge.setLineLabel(pActionTmp.getActionName());
            }
            edge.setUserModel(pActionTmp);
          }
          else{
            LinkModel tmpLinkModel = processAutoJumpNextNode(nNode);
            if (pActionTmp != null && tmpLinkModel != null) {
              edge.setLineColor(Color.GREEN);
              edge.setLineLabel(pActionTmp.getActionName());
              edge.setUserModel(pActionTmp);
  
              TraceGraphCell tmpCell = (TraceGraphCell) getCellFromgraphCellMap(tmpLinkModel);
              if (tmpCell != null) {
                TraceGraphEdge tmpEdge = new TraceGraphEdge(link, attrGraphMap);
                tmpEdge.setLineColor(Color.GREEN);
                DefaultPort tmpPort = (DefaultPort) getPortFromCellPortMap(tmpLinkModel);
                targetCell.add(tmpPort);
                ConnectionSet cs = new ConnectionSet(tmpEdge, targetPort, tmpPort);
                Object[] edgetmpCells = new Object[] { tmpEdge, targetCell, tmpCell };
                graphModel.insert(edgetmpCells, attrGraphMap, cs, null, null);
                edgeMap.put(link, tmpEdge);
                NodeModel passNode=tmpLinkModel.getNextNode();
                GraphUnit returnUnit=getUnitFromgraphCellMap(passNode);
                returnLinkUnit=new LinkUnit(unit,returnUnit);
              }
            }
          }
          //分支表达式判断
          //          if (pActionTmp == null && nActionTmp != null) {
          //            String exp = getLinkExpress((NodeModel) curUnit.getModel(), (NodeModel) unit.getModel());
          //            if (exp != null && exp.equals("('01' != isAM) && ('jiangxl' != isjiangxl)")) {
          //              edge.setLineColor(Color.GREEN);
          //              edge.setLineLabel(nActionTmp.getActionName());
          //              edge.setUserModel(nActionTmp);
          //            }
          //          }
        }
        
        if(instanceCurTaskMap!=null && instanceActionMap!=null){
          List<CurrentTaskModel> curTaskList=(List<CurrentTaskModel>)instanceCurTaskMap.get(nNode.getNodeId());
          if((curTaskList!=null && curTaskList.size()>0) && instanceActionMap.get(pNode.getNodeId())!=null){
            edge.setLineColor(Color.GREEN);
          }
        }
      }
      
      ConnectionSet cs = new ConnectionSet(edge, sourcePort, targetPort);
      Object[] edgeCells = new Object[] { edge, sourceCell, targetCell };
      graphModel.insert(edgeCells, attrGraphMap, cs, null, null);
      edgeMap.put(link, edge);
    }
    return returnLinkUnit;
  }

  private TraceGraphCell getCellFromgraphCellMap(LinkModel linkModel) {
    TraceGraphCell cell = null;
    Iterator<?> itr = graphCellMap.keySet().iterator();
    while (itr.hasNext()) {
      GraphUnit key = (GraphUnit) itr.next();
      NodeModel node = (NodeModel) key.getModel();
      if (linkModel.getNextNode().equals(node)) {
        cell = (TraceGraphCell) graphCellMap.get(key);
      }
    }
    return cell;
  }
  
  private GraphUnit getUnitFromgraphCellMap(NodeModel nodeModel) {
    GraphUnit unit = null;
    Iterator itr = graphCellMap.keySet().iterator();
    while (itr.hasNext()) {
      GraphUnit key = (GraphUnit) itr.next();
      NodeModel node = (NodeModel) key.getModel();
      if (nodeModel.equals(node)) {
        TraceGraphCell cell = (TraceGraphCell) graphCellMap.get(key);
        unit=cell.getUserGraphUnit();
      }
    }
    return unit;
  }

  private DefaultPort getPortFromCellPortMap(LinkModel linkModel) {
    DefaultPort port = null;
    Iterator itr = cellPortMap.keySet().iterator();
    while (itr.hasNext()) {
      Object key = itr.next();
      TraceGraphCell cell = (TraceGraphCell) key;
      GraphUnit unit = cell.getUserGraphUnit();
      NodeModel node = (NodeModel) unit.getModel();
      if (linkModel.getNextNode().equals(node)) {
        port = (DefaultPort) cellPortMap.get(key);
      }
    }
    return port;
  }

  private DefaultGraphCell createGraphCell(GraphUnit unit) {
    DefaultGraphCell nodeCell = new DefaultGraphCell();
    Map nodeAttrMap = new Hashtable();
    attrGraphMap.put(nodeCell, nodeAttrMap);
    Point p = unit.getLocation();
    Rectangle2D targetBounds = null;
    if (p == null) {
      targetBounds = new Rectangle2D.Double(curX, curY, TraceGraphCell.ICON_WIDTH, TraceGraphCell.ICON_HEIGHT);//node位置
    } else {
      targetBounds = new Rectangle2D.Double(p.getX(), p.getY(), TraceGraphCell.ICON_WIDTH,
        TraceGraphCell.ICON_HEIGHT);
    }
    GraphConstants.setBounds(nodeAttrMap, targetBounds);
    GraphConstants.setIcon(nodeAttrMap, unit.getIcon());
    GraphConstants.setBorder(nodeAttrMap, BorderFactory.createCompoundBorder());
    GraphConstants.setEditable(nodeAttrMap, false);
    GraphConstants.setFont(nodeAttrMap, new Font(null, Font.PLAIN, 11));
    return nodeCell;
  }

  ////////////////////////////////////TEMPLATE_GRAPH_END///////////////////////////////////////////

  public void freshTraceGraph() {
    TraceGraphEdge edge = (TraceGraphEdge) edgeMap.get(testLink);
    System.out.println(edge.getLinkUnit().toString());
    edge.refreshLineColor(Color.GREEN);
    if (traceGraph != null) {
      traceGraph.getGraphLayoutCache().reload();
      traceGraph.revalidate();
      traceGraph.updateUI();
      traceGraph.repaint();
    }
  }

  public static void main(String[] arg) throws Exception {
    JFrame frame = new JFrame();
    frame.setTitle("流程设置");
    frame.setSize(860, 600);
    //40611?,40194,40309,40084?，40002(开始结点有问题)?
    TranceGraphProcessor panel = new TranceGraphProcessor(new TemplateToMemory(new Long(40002)));
    panel.showTemplateGraph();
    //    panel.freshTraceGraph();
    //    panel.showTraceGraph(new Long(9013));

    frame.getContentPane().add(panel, BorderLayout.CENTER);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
