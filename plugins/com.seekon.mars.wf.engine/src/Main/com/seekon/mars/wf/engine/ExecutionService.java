package com.seekon.mars.wf.engine;

/**
 * 工作流业务处理
 * 
 * @author Administrator
 * 
 */
public interface ExecutionService {

	public void startProcessInstance(WorkflowContext ctx);

	/**
	 * 送审
	 */
	public void commit(WorkflowContext ctx);

	/**
	 * 收回(流程实例未完成，由本人收回)
	 */
	public void callback(WorkflowContext ctx);

	/**
	 * 移交
	 */
	public void handover(WorkflowContext ctx);

	/**
	 * 重做(流程实例已经完成的回退)
	 */
	public void rework(WorkflowContext ctx);

	/**
	 * 回退（流程实例未完成的回退，其他人（也可能本人）的强制回退）
	 */
	public void untread(WorkflowContext ctx);
	
	/**
	 * 运行期增加执行者
	 * @param ctx
	 */
	public void addRuntimeExecutor(WorkflowContext ctx);
}
