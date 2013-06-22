package com.seekon.mars.wf.engine.internal.cmd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.spi.util.DateUtil;
import com.seekon.mars.wf.engine.util.WFConst;

/**
 * 为上下文中回退结点创建代办任务
 * @author Administrator
 */
public class CreatTaskFromNodeListCmd extends AbstractCommand {

  private static final long serialVersionUID = -8108158400001836837L;

  public void doExecute(Environment env) throws CommandException {
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    String type = env.getContext().getAction();
    Long instanceId = env.getInstance().getInstanceId();
    List nodeIdList = new ArrayList();
    Iterator itr = null;
    List nodeActionList = null;
    if (type.equals(WFConst.ACTION_TYPE_UNTREAD_FLOW)) {
      List preNodeList = env.getPreviousNodeList();
      itr = preNodeList.iterator();
      while (itr.hasNext()) {
        NodeModel tmpNode = (NodeModel) itr.next();
        nodeIdList.add((Long)tmpNode.getNodeId());
      }
    } else {
      nodeIdList.add(env.getCurrentNode().getNodeId());
    }
    nodeActionList = runtimeDao.getActionFromNodeList((Long)instanceId, nodeIdList);
    //创建者是下一结点的创建者
    if (nodeActionList != null && nodeActionList.size() > 0) {
      itr = nodeActionList.iterator();
      while (itr.hasNext()) {
        ActionModel actionModel = (ActionModel) itr.next();
        CurrentTaskModel currentTaskModel = new CurrentTaskModel();
        currentTaskModel.setInstanceId(env.getContext().getInstanceId());
        currentTaskModel.setNodeId(actionModel.getNodeId());
        currentTaskModel.setExecutor(actionModel.getExecutor());
        currentTaskModel.setCreator(env.getCurrentExecutor().getCode());
        currentTaskModel.setOwner(actionModel.getExecutor());
        StaffModel executor = env.getCurrentExecutor();
        currentTaskModel.setCoCode(actionModel.getCoCode());
        currentTaskModel.setOrgCode(actionModel.getOrgCode());
        currentTaskModel.setPosiCode(actionModel.getPosiCode());
        currentTaskModel.setCreateTime(DateUtil.getTime());
        currentTaskModel.setNd(executor.getStaffPosition().getOrg().getNd());

        runtimeDao.createCurrentTask(currentTaskModel);
      }
    }
  }

}
