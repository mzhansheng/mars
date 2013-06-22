package com.seekon.mars.wf.engine.internal.svc;

import com.seekon.mars.wf.engine.ManagementService;
import com.seekon.mars.wf.engine.WFClient;
import com.seekon.mars.wf.engine.WorkflowContext;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.cmd.CommandChain;
import com.seekon.mars.wf.engine.internal.cmd.CreateActionHistoryCmd;
import com.seekon.mars.wf.engine.internal.cmd.CreateDraftCmd;
import com.seekon.mars.wf.engine.internal.cmd.CreateInstanceHistoryCmd;
import com.seekon.mars.wf.engine.internal.cmd.InstanceManagerCmd;
import com.seekon.mars.wf.engine.internal.cmd.RemoveActionByNodeCmd;
import com.seekon.mars.wf.engine.internal.cmd.RemoveInstanceByNodeCmd;
import com.seekon.mars.wf.engine.internal.cmd.RemovePassCmd;
import com.seekon.mars.wf.engine.internal.cmd.RemoveTaskByNodeCmd;
import com.seekon.mars.wf.engine.util.WFConst;

public class ManagementServiceImpl extends AbstractServiceImpl implements ManagementService {
  public static final int DEACTIVATE_TYPE = 0;

  public static final int ACTIVATE_TYPE = 1;

  public static final int SUSPEND_TYPE = 2;

  public static final int RESTART_TYPE = 3;

  public static final int TERMINATED_TYPE = 4;

  private WorkflowContext context;

  public ManagementServiceImpl() {
  }

  private void instanceProcess(WorkflowContext ctx, int processType, int status) {
    context = ctx;
    CommandChain chain = new CommandChain();
    chain.addCommand(new InstanceManagerCmd(processType, status));
    chain.addCommand(new CreateActionHistoryCmd());
    if (processType == ManagementServiceImpl.TERMINATED_TYPE
        || processType == ManagementServiceImpl.RESTART_TYPE) {
      chain.addCommand(new CreateInstanceHistoryCmd());
      chain.addCommand(new RemoveTaskByNodeCmd());//删除当前任务
      chain.addCommand(new RemovePassCmd()); // 删除相关执行路径
      chain.addCommand(new RemoveActionByNodeCmd());// 删除动作相关操作
      chain.addCommand(new RemoveInstanceByNodeCmd());//删除实例
    }
    commandService.execute(chain);
  }

  /**
   * 实例激活流程： 1.更新当前实例状态。 2.添加更新状态历史记录。
   */
  public void activate(WorkflowContext ctx) {
    ctx.setAction(WFConst.ACTION_TYPE_ACTIVATE_INSTANCE);
    instanceProcess(ctx, ManagementServiceImpl.ACTIVATE_TYPE, WFConst.INSTANCE_STATUS_ACTIVE);
    Environment env = Environment.getEnvironment();
    env.getWorkflowEventBroker().fireProcessInstanceActivate(env.getInstance(), env);
  }

  /**
   * 实例冻结流程：
   */
  public void deactivate(WorkflowContext ctx) {
    ctx.setAction(WFConst.ACTION_TYPE_DEACTIVATE_INSTANCE);
    instanceProcess(ctx, ManagementServiceImpl.DEACTIVATE_TYPE, WFConst.INSTANCE_STATUS_DEACTIVE);
    Environment env = Environment.getEnvironment();
    env.getWorkflowEventBroker().fireProcessInstanceDeactivate(env.getInstance(), env);
  }

  /**
   * 实例挂起流程：（实例废弃）
   * 
   */
  public void suspend(WorkflowContext ctx) {
    ctx.setAction(WFConst.ACTION_TYPE_SUSPEND_INSTANCE);
    instanceProcess(ctx, ManagementServiceImpl.SUSPEND_TYPE, WFConst.INSTANCE_STATUS_SUSPEND);
    Environment env = Environment.getEnvironment();
    env.getWorkflowEventBroker().fireProcessInstanceSuspend(env.getInstance(), env);
  }

  /**
   * 实例重启流程
   */
  public void restart(WorkflowContext ctx) {
    ctx.setAction(WFConst.ACTION_TYPE_RESTART_INSTANCE);
    instanceProcess(ctx, ManagementServiceImpl.RESTART_TYPE, WFConst.INSTANCE_STATUS_RESTART);
    WFClient.getWFEngine().getExecutionService().startProcessInstance(context);
    Environment env = Environment.getEnvironment();
    env.getWorkflowEventBroker().fireProcessInstanceRestart(env.getInstance(), env);
  }

  /**
   * 实例结束流程（实例流程终止完成）
   */
  public void terminated(WorkflowContext ctx) {
    ctx.setAction(WFConst.ACTION_TYPE_TERMINATED_INSTANCE);
    instanceProcess(ctx, ManagementServiceImpl.TERMINATED_TYPE, WFConst.INSTANCE_STATUS_TERMINATED);
    Environment env = Environment.getEnvironment();
    env.getWorkflowEventBroker().fireProcessInstanceTerminate(env.getInstance(), env);
  }

  /**
   * 创建工作流草稿
   */
  public void createDraft(String draftName, String compoId, String masterTableId, WorkflowContext ctx) {
    commandService.execute(new CreateDraftCmd(draftName, compoId, masterTableId));
  }
}
