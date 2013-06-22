package com.seekon.mars.wf.engine.internal.cmd;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.model.runtime.ActionHistoryModel;
import com.seekon.mars.wf.engine.spi.util.DateUtil;

public class CreateActionHistoryCmd extends AbstractCommand {

  private static final long serialVersionUID = 2508923503680253530L;

  public void createActionHistory(Environment evn) {
		WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
		String user = evn.getCurrentExecutor().getCode();
		ActionHistoryModel actionHistory = new ActionHistoryModel();
		actionHistory.setInstanceId(evn.getContext().getInstanceId());
		actionHistory.setNodeId(evn.getCurrentNode().getNodeId());
		actionHistory.setActionName(evn.getContext().getAction());//操作名称
		actionHistory.setExecutor(user);
		if(evn.getTaskArrivalTime()!=null){
		  actionHistory.setArrivalTime(evn.getTaskArrivalTime());
		}
		actionHistory.setExecuteTime(DateUtil.getTime());
		actionHistory.setDescription((String) evn.getContext()
				.getVariable("actionDescription"));
		actionHistory.setOwner(user);
		StaffModel executor = evn.getCurrentExecutor();
		actionHistory.setCoCode(executor.getStaffPosition().getOrg().getCompany().getCode());
		actionHistory.setOrgCode(executor.getStaffPosition().getOrg().getCode());
		actionHistory.setPosiCode(executor.getStaffPosition().getPosition().getCode());
		actionHistory.setLimitExecuteTime(DateUtil.getTime());
		actionHistory.setNd(executor.getStaffPosition().getOrg().getNd());
		runtimeDao.createActionHistory(actionHistory);// 添加历史
	}

	public void doExecute(Environment env) throws CommandException {
		createActionHistory(env);
	}
}
