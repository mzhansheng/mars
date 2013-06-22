package com.seekon.mars.wf.engine.internal.cmd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.TemplateModel;
import com.seekon.mars.wf.engine.model.runtime.PassModel;
import com.seekon.mars.wf.engine.util.WFConst;

public class UntreadProcessCmd extends AbstractCommand{

  private static final long serialVersionUID = 4956884837559748193L;

  /**
	 * 1.先计算出前一节点
	 */
	public void doExecute(Environment env) throws CommandException {
	  Long instanceId = env.getContext().getInstanceId();
    Long currentNodeId = env.getCurrentNode().getNodeId();
    TemplateModel template = env.getTemplate();
    List preNodeIdList = new ArrayList();//缓存计算出的NodeId列表
    List passList = new ArrayList(); //缓存计算出的pass列表
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    List passModelLst = runtimeDao.getInstancePass(instanceId);
    processPrevious(preNodeIdList, passList, template, passModelLst, currentNodeId);
    if (preNodeIdList.size() > 0) {
      env.setPreviousNodeList(preNodeIdList);
      env.setPassPathList(passList);
    } else {
      throw new WorkflowException("回退条件不符合！");
    }
  }

  /**
   * 计算所有节点的所有前驱任务节点
   * @param previousNodeList<Long> 缓存计算出的NodeId列表
   * @param emplateModel 模板
   * @param passNodeList 当前实例流程节点执行记录
   * @param curnodeId //要计算Node的nodeId
   */
  private void processPrevious(List previousNodeList, List passList, TemplateModel templateModel,
    List passNodeList, Long curnodeId) throws WorkflowException {
    Iterator itr = passNodeList.iterator();
    while (itr.hasNext()) {
      PassModel passMode = (PassModel) itr.next();
      if (passMode.getNextNodeId().equals(curnodeId)) {
        Long preNodeId = passMode.getCurrentNodeId();
        NodeModel preNode = templateModel.getNode(preNodeId);
        String type = preNode.getType();
        if (type.equals(WFConst.NODE_TYPE_TASK)) {
          previousNodeList.add(preNode);
        } else if (type.equals(WFConst.NODE_TYPE_NAVIGATION)) {
          processPrevious(previousNodeList, passList, templateModel, passNodeList, preNode.getNodeId());//递归计算前一节点
        } else if (type.equals(WFConst.NODE_TYPE_START)) {
          throw new WorkflowException("不能回退到开始结点！");
        }

        passList.add(passMode);
      }
    }
  }
	

}
