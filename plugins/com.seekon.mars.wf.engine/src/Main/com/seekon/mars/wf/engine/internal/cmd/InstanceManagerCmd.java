package com.seekon.mars.wf.engine.internal.cmd;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.internal.svc.ManagementServiceImpl;
import com.seekon.mars.wf.engine.model.runtime.InstanceModel;
import com.seekon.mars.wf.engine.util.WFConst;

public class InstanceManagerCmd extends AbstractCommand {

  private static final long serialVersionUID = 8847940515615013577L;

  private WFRuntimeMapper runtimeDao;

  private int processType;

  private int status;

  /**
   * 
   * @param ctx
   *            上下文
   * @param processType
   *            处理类型 （激活：WFConst.INSTANCE_STATUS_ACTIVE、
   *            冻结：WFConst.INSTANCE_STATUS_DEACTIVE、 重启:
   *            WFConst.INSTANCE_STATUS_RESTART、
   *            挂起：WFConst.INSTANCE_STATUS_SUSPEND、
   *            完成:WFConst.INSTANCE_STATUS_TERMINATED）
   */
  public InstanceManagerCmd(int processType, int status) {
    runtimeDao = WFDaoFactory.getWFRuntimeDao();
    this.processType = processType;
    this.status = status;
  }

  public void doExecute(Environment env) throws CommandException {
    instanceProcess(env, processType, status);
  }

  /**
   * 得到执行者
   * 
   * @return
   */
  // private String getExecutor() {
  // return env.getCurrentExecutor().getCode();
  // }
  /**
   * 得到实例ID
   * 
   * @return
   */
  //	private Long getInstanceId() {
  //		return context.getInstanceId();
  //	}

  //	private String getDescription() {
  //		return (String) context.getVariable("actionDescription");
  //	}

  private void instanceProcess(Environment env, int processType, int status) {
    //		String user = env.getCurrentExecutor().getCode();
    Long instanceId = env.getContext().getInstanceId();
    InstanceModel instance = runtimeDao.getInstanceById(instanceId);
    Long curStatus = instance.getStatus();
    try {
      switch (processType) {
      case ManagementServiceImpl.DEACTIVATE_TYPE:
        if (curStatus.longValue() == WFConst.INSTANCE_STATUS_DEACTIVE) {
          return;
        } else if (curStatus.longValue() == WFConst.INSTANCE_STATUS_TERMINATED
          || curStatus.longValue() == WFConst.INSTANCE_STATUS_SUSPEND) {
          throw new Exception("工作流实例已经结束或者挂起，不能 冻结。");
        }
        instance.setName("工作流实例冻结！");
        break;
      case ManagementServiceImpl.ACTIVATE_TYPE:
        if (curStatus.longValue() == WFConst.INSTANCE_STATUS_ACTIVE) {
          return;
        } else if (curStatus.longValue() == WFConst.INSTANCE_STATUS_TERMINATED
          || curStatus.longValue() == WFConst.INSTANCE_STATUS_SUSPEND) {
          throw new Exception("工作流实例已经结束或者挂起，不能激活。");
        }
        instance.setName("工作流实例激活！");
        break;
      case ManagementServiceImpl.SUSPEND_TYPE:
        if (curStatus.longValue() == WFConst.INSTANCE_STATUS_SUSPEND) {
          return;
        } else if (curStatus.longValue() == WFConst.INSTANCE_STATUS_TERMINATED) {
          throw new Exception("工作流实例已经结束，不能 挂起。");
        }
        instance.setName("工作流实例挂起！");
        break;
      case ManagementServiceImpl.RESTART_TYPE:
        if (curStatus.longValue() == WFConst.INSTANCE_STATUS_RESTART) {
          return;
        } else if (curStatus.longValue() == WFConst.INSTANCE_STATUS_TERMINATED
          || curStatus.longValue() == WFConst.INSTANCE_STATUS_SUSPEND) {
          throw new Exception("工作流实例已经结束或者挂起，不能 重启。");
        }
        instance.setName("工作流实例重启！");
        break;
      case ManagementServiceImpl.TERMINATED_TYPE:
        if (curStatus.longValue() == WFConst.INSTANCE_STATUS_TERMINATED) {
          return;
        } else if (curStatus.longValue() == WFConst.INSTANCE_STATUS_SUSPEND) {
          throw new Exception("工作流实例已经挂起，不能 结束。");
        }
        instance.setName("工作流实例终止！");
        break;
      }
      instance.setStatus(Long.valueOf(Integer.toString(status)));// 设置实例状态
      runtimeDao.updateInstance(instance);// 更新实例状态

      // createActionHistory(instanceId, user, getDescription());
    } catch (Exception ex) {
      throw new WorkflowException(ex);
    }
  }
}
