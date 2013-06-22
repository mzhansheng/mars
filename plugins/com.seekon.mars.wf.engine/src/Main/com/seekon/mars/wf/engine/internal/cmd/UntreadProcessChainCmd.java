package com.seekon.mars.wf.engine.internal.cmd;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.internal.svc.DefaultCommandService;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.TemplateModel;
import com.seekon.mars.wf.engine.model.runtime.PassModel;
import com.seekon.mars.wf.engine.util.WFConst;

public class UntreadProcessChainCmd extends AbstractCommand{

  private static final long serialVersionUID = -7253528932091528760L;
  private CommandService commandService;
  private CommandChain chain;
  public void doExecute(Environment env) throws CommandException {
    Long instanceId = env.getContext().getInstanceId();
    Long currentNodeId = env.getContext().getCurrentNodeId();
    Long selectedRebackLinkId =env.getContext().getSelectedRebackLinkId();
    TemplateModel template = env.getTemplate();
    LinkModel linkModel=template.getLink(selectedRebackLinkId);
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    List passModelLst=runtimeDao.getInstancePass(instanceId);
    commandService=new DefaultCommandService();
    commandService.execute(new CreateActionHistoryCmd());
    commandService.execute(new RemoveTaskByNodeCmd());
    initCommandChain();
    LinkedList  nextQueue=new LinkedList ();
    nextQueue.addLast(env.getCurrentNode());
    Long targetNodeId=linkModel==null?currentNodeId:linkModel.getCurrentNodeId();
    processNext(nextQueue,template,passModelLst,currentNodeId,targetNodeId);
    while(!nextQueue.isEmpty()){
      NodeModel curNode=(NodeModel)nextQueue.removeFirst();
      env.setCurrentNode(curNode);
      if(curNode.getNodeId().equals(targetNodeId)){
        ////commandService.execute(new RemoveActionByNodeCmd());
        break;
      }
      commandService.execute(chain);
    }
  }
  
  private void processNext(LinkedList nextQueue,TemplateModel templateModel,List passNodeList,Long curNodeId,Long targetNodeId){
    Iterator itr=passNodeList.iterator();
    while(itr.hasNext()){
      PassModel passMode=(PassModel)itr.next();
      if(passMode.getNextNodeId().equals(curNodeId)){
        Long preNodeId = passMode.getCurrentNodeId();
        
        NodeModel preNode = templateModel.getNode(preNodeId);
        String type = preNode.getType();
        if (type.equals(WFConst.NODE_TYPE_TASK)) {
            nextQueue.addLast(preNode);
            if(!passMode.getCurrentNodeId().equals(targetNodeId)){
              processNext(nextQueue,templateModel, passNodeList, preNode.getNodeId(),targetNodeId);
            }
        } else if (type.equals(WFConst.NODE_TYPE_NAVIGATION)) {
          processNext(nextQueue,templateModel, passNodeList, preNode.getNodeId(),targetNodeId);//递归计算前一节点
        } else if (type.equals(WFConst.NODE_TYPE_START)) {
          throw new CommandException("任务已经结束！");
        }
      }
    }
  }
  
  private void initCommandChain(){
    chain=new CommandChain();
    chain.addCommand(new UntreadProcessCmd()); // 1.处理回退
    chain.addCommand(new RemovePassCmd()); // 2.删除执行路径Pass信息
    //chain.addCommand(new RemoveTaskByNodeCmd()); // 3.删除节点的任务
    chain.addCommand(new RemoveActionByNodeCmd()); // 4.删除节点的动作
//    chain.addCommand(new CreateActionHistoryCmd());// 5.添加历史
  }

}
