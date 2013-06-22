package com.seekon.mars.wf.engine;

/**
 * 工作流实例管理
 * 
 * @author Administrator
 * 
 */
public interface ManagementService {
  public void createDraft(String draftName, String compoId,
    String masterTableId, WorkflowContext ctx);

	public void suspend(WorkflowContext ctx);// 实例挂起

	public void restart(WorkflowContext ctx);// 实例重启

	public void terminated(WorkflowContext ctx); // 实例结束

	public void activate(WorkflowContext ctx);// 任务激活

	public void deactivate(WorkflowContext ctx); // 冻结
}
