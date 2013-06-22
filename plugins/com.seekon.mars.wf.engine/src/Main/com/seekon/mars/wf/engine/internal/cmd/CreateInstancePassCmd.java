package com.seekon.mars.wf.engine.internal.cmd;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.runtime.LinkStaffModel;
import com.seekon.mars.wf.engine.model.runtime.PassModel;

/**
 * 
 * 创建流程实例的执行情况信息
 *
 */
public class CreateInstancePassCmd extends AbstractCommand{

  private static final long serialVersionUID = -2717975135851456696L;

  public void doExecute(Environment env) throws CommandException {
    Set linkSet = new HashSet();
    Iterator iterator = env.getNextLinkStaffSet().iterator();//1、处理分支节点的情况
    while(iterator.hasNext()){
      LinkStaffModel linkStaff = (LinkStaffModel)iterator.next();
      LinkModel link = linkStaff.getLink();
      recursiveProcessPassLink(env.getCurrentNode(), link, linkSet);
    }
    
    iterator = linkSet.iterator();//2、创建执行情况信息
    while(iterator.hasNext()){
      LinkModel link = (LinkModel)iterator.next();
      PassModel pass = new PassModel();
      pass.setCurrentNodeId(link.getCurrentNodeId());
      pass.setInstanceId(env.getInstance().getInstanceId());
      pass.setNextNodeId(link.getNextNodeId());
      pass.setNodeLinkId(link.getNodeLinkId());
      
      WFDaoFactory.getWFRuntimeDao().createPass(pass);
    }
  }
  
  private void recursiveProcessPassLink(NodeModel sNode, LinkModel link, Set linkSet){
    linkSet.add(link);
    NodeModel currentNode = link.getCurrentNode();
    if(currentNode.equals(sNode)){
      return;
    }
    
    recursiveProcessPassLink(sNode, (LinkModel)currentNode.getFromLinkList().get(0), linkSet);
  }
}
