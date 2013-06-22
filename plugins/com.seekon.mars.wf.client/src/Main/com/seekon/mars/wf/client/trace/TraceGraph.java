package com.seekon.mars.wf.client.trace;

import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import org.jgraph.JGraph;
import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import com.seekon.mars.wf.engine.model.ExecutorSourceModel;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.participant.NodeStaffModel;

/**
 * 绘制流程跟踪图
 * @author Administrator
 *
 */
public class TraceGraph extends JGraph {
  private String curUserId;
  public TraceGraph(){
  }
  
  public TraceGraph(String userId){
    this.curUserId=userId;
  }

  public TraceGraph(GraphModel arg0) {
    super(arg0);
  }
  
  public TraceGraph(GraphModel arg0,String userId) {
    super(arg0);
    this.curUserId=userId;
  }

  public TraceGraph(GraphLayoutCache arg0) {
    super(arg0);
  }
  
  public TraceGraph(GraphLayoutCache arg0,String userId) {
    super(arg0);
    this.curUserId=userId;
  }

  public TraceGraph(GraphModel arg0, GraphLayoutCache arg1) {
    super(arg0, arg1);
  }
  
  public TraceGraph(GraphModel arg0, GraphLayoutCache arg1,String userId) {
    super(arg0, arg1);
    this.curUserId=userId;
  }

  public TraceGraph(GraphModel arg0, BasicMarqueeHandler arg1) {
    super(arg0, arg1);
  }
  
  public TraceGraph(GraphModel arg0, BasicMarqueeHandler arg1,String userId) {
    super(arg0, arg1);
    this.curUserId=userId;
  }

  public TraceGraph(GraphModel arg0, GraphLayoutCache arg1,
      BasicMarqueeHandler arg2) {
    super(arg0, arg1, arg2);
  }
  
  public TraceGraph(GraphModel arg0, GraphLayoutCache arg1,
    BasicMarqueeHandler arg2,String userId) {
  super(arg0, arg1, arg2);
  this.curUserId=userId;
}

  public String getToolTipText(MouseEvent e) {
    if (e != null) {
      Object obj = getFirstCellForLocation(e.getX(), e.getY());
//      if (obj != null && obj instanceof TraceGraphEdge) {
//        String result = "";
//        Object edgeObj = ((TraceGraphEdge)obj).getUserModel();
//        if (edgeObj instanceof ActionHistoryModel) {
//          ActionHistoryModel action = (ActionHistoryModel) edgeObj;
//          if (action.getDescription() != null) {
//            result += "<html><b>意见："+action.getDescription()+"</b></html>";
//          }
//        } else if (edgeObj instanceof ActionHistoryModel) {
//          ActionHistoryModel history = (ActionHistoryModel) edgeObj;
//          if (history.getDescription() != null) {
//            result += "<html><b>意见:" + history.getDescription()+"</b></html>";
//          }
//        }
//        return result;
//      }
      if(obj!=null && obj instanceof TraceGraphCell){
        TraceGraphCell curGraphCell=(TraceGraphCell)obj;
        Object modelObj=curGraphCell.getUserGraphUnit().getModel();
        NodeModel nodeModel=(NodeModel)modelObj;
        String topTip="<html><body><table>";
        topTip+="<tr><td align=center><font STYLE=\"font: bold 12pt '宋体'\">执行者ID</font>" 
                +"</td><td align=center><font STYLE=\"font: bold 12pt '宋体'\">执行者名称</font></td>"
                +"</td><td align=center><font STYLE=\"font: bold 12pt '宋体'\">执行时间</font></td>"
                +"<td align=center><font STYLE=\"font: bold 12pt '宋体'\">执行情况</font></td>"
                +"<td align=center><font STYLE=\"font: bold 12pt '宋体'\">意见</font></td></tr>";
        List<NodeStaffModel> curTaskExecutor=curGraphCell.getCurTaskAllExecutorList();
        if(curTaskExecutor!=null && curTaskExecutor.size()>0){
          Iterator<?> itr=curTaskExecutor.iterator();
          while(itr.hasNext()){
            NodeStaffModel nodeStaff=(NodeStaffModel)itr.next();
            String passDesc=nodeStaff.getPassDesc()==null?"":nodeStaff.getPassDesc();
            String desc=nodeStaff.getActionDescription()==null?"":nodeStaff.getActionDescription();
            if(curUserId!=null && curUserId.equals(nodeStaff.getCode())){
              if(passDesc.length()>0){
                topTip+="<tr><td><font STYLE=\"font:12pt '宋体'\">"+nodeStaff.getCode()
                +"</font></td><td><font STYLE=\"font:12pt '宋体'\">"+nodeStaff.getName()
                +"</font></td><td><font STYLE=\"font:12pt '宋体'\">"+nodeStaff.getActionTime()
                +"</font></td><td><font STYLE=\"font:12pt '宋体'\">" 
                +passDesc
                +"</font></td><td><font STYLE=\"font:12pt '宋体'\">"
                +desc
                +"</font></td></tr>";
              }
              else{
                topTip+="<tr><td><font STYLE=\"font:12pt '宋体';color:#FF0000\">"+nodeStaff.getCode()
                +"</font></td><td><font STYLE=\"font:12pt '宋体';color:#FF0000\">"+nodeStaff.getName()
                +"</font></td><td><font STYLE=\"font:12pt '宋体';color:#FF0000\">"+nodeStaff.getActionTime()
                +"</font></td><td><font STYLE=\"font:12pt '宋体';color:#FF0000\">" 
                +"待办"
                +"</font></td><td></td></tr>";
              }
              
            }
            else{
              if(passDesc.length()==0){
                topTip+="<tr><td><font STYLE=\"font:12pt '宋体';color:#FF0000\">"+nodeStaff.getCode()
                          +"</font></td><td><font STYLE=\"font:12pt '宋体';color:#FF0000\">"+nodeStaff.getName()
                          +"</font></td><td><font STYLE=\"font:12pt '宋体';color:#FF0000\">"+nodeStaff.getActionTime()
                          +"</font></td><td><font STYLE=\"font:12pt '宋体';color:#FF0000\">待办"
                          +"</font></td><td></td></tr>";  
              }
              else{
                topTip+="<tr><td><font STYLE=\"font:12pt '宋体'\">"+nodeStaff.getCode()
                +"</font></td><td><font STYLE=\"font:12pt '宋体'\">"+nodeStaff.getName()
                +"</font></td><td><font STYLE=\"font:12pt '宋体'\">"+nodeStaff.getActionTime()
                +"</font></td><td><font STYLE=\"font:12pt '宋体'\">"+passDesc
                +"</font></td><td><font STYLE=\"font:12pt '宋体'\">"+desc
                +"</font></td></tr>";  
              }
            }
          }
        }
        else{
          List<?> executorList=nodeModel.getExecutorSourceList();
          if(executorList!=null && executorList.size()>0){
            Iterator<?> itr=executorList.iterator();
            while(itr.hasNext()){
              ExecutorSourceModel executor=(ExecutorSourceModel)itr.next();
              topTip+="<tr><td><font STYLE=\"font:12pt '宋体' #FF0000\">"+executor.getExecutor()
                      +"</font></td><td><font STYLE=\"font:12pt '宋体' #00FF00\">"+executor.getExecutorName()
                      +"</font></td><td></td><td></td><td></td></tr>";
            }
            
          }
        }
        topTip+="</table></body></html>";
        return topTip;
      }
    }
    return null;
  }
}