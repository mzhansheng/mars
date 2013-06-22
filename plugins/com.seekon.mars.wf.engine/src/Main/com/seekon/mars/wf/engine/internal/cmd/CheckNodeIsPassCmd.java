package com.seekon.mars.wf.engine.internal.cmd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.TemplateModel;
import com.seekon.mars.wf.engine.model.runtime.PassModel;
import com.seekon.mars.wf.engine.util.WFConst;

/**
 * 检查下一结点是否已经处理完成（查看linkModel中是否存在以下一结点开始的linkModel）
 * @author Administrator
 *
 */
public class CheckNodeIsPassCmd extends AbstractCommand{

  private static final long serialVersionUID = -4687937971725311451L;

  public void doExecute(Environment env) throws CommandException {
		Long instanceId = env.getContext().getInstanceId();
		Long currentNodeId = env.getContext().getCurrentNodeId();
		TemplateModel template = env.getTemplate();
		List nextList= new ArrayList();
		List passList =new ArrayList();
		WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
		List passModelLst=runtimeDao.getInstancePass(instanceId);//得到实例已经执行过的passPath
		processNext(nextList,passList,template,passModelLst,currentNodeId);
		if(isNextNodeExistAction(runtimeDao,instanceId,nextList)){
		  //下一结点有代办任务
			throw new CommandException("任务已经提交，您的任务已经不能退回！");
		}
		env.setPreviousNodeList(nextList);
		env.setPassPathList(passList);
	}
	
	private void processNext(List nextNodeList,List passList,TemplateModel templateModel,List passNodeList,Long curnodeId){
		Iterator itr=passNodeList.iterator();
		while(itr.hasNext()){
			PassModel passMode=(PassModel)itr.next();
			if(passMode.getCurrentNodeId().equals(curnodeId)){
				Long nextNodeId=passMode.getNextNodeId();
				NodeModel curNode=templateModel.getNode(nextNodeId);
				String type=curNode.getType();
				if(type.equals(WFConst.NODE_TYPE_TASK)){
					nextNodeList.add(curNode);
				}
				else if(type.equals(WFConst.NODE_TYPE_NAVIGATION)){
					processNext(nextNodeList,passList,templateModel,passNodeList,curNode.getNodeId());//递归计算前一节点
				}
				else if(type.equals(WFConst.NODE_TYPE_END)){
					throw new CommandException("任务已经结束！");
				}
				passList.add(passMode);
			}
		}
	}
	
	/**
	 * 下一结点是否存在ACTION
	 * @param nodeList
	 * @param passList
	 * @return
	 */
	private boolean isNextNodeExistAction(WFRuntimeMapper runtimeDao,Long instanceId, List nextList){
		List nodeIdList=new ArrayList();
		Iterator itr=nextList.iterator();
		while(itr.hasNext()){
		  NodeModel node=(NodeModel)itr.next();
		  nodeIdList.add(node.getNodeId());
		}
		List actionList=null;
		if(nodeIdList.size()>0){
		  actionList=runtimeDao.getActionFromNodeList(instanceId, nodeIdList);
		}
		if(actionList!=null && actionList.size()>0){
		  return true;
		}
		return false;
	}

}
