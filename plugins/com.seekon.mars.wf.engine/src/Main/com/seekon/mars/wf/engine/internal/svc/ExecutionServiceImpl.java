package com.seekon.mars.wf.engine.internal.svc;

import com.seekon.mars.wf.engine.ExecutionService;
import com.seekon.mars.wf.engine.WorkflowContext;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.cmd.AddRuntimeExecutorCmd;
import com.seekon.mars.wf.engine.internal.cmd.CheckNodeIsPassCmd;
import com.seekon.mars.wf.engine.internal.cmd.CommandChain;
import com.seekon.mars.wf.engine.internal.cmd.CompleteTaskCmd;
import com.seekon.mars.wf.engine.internal.cmd.CreatTaskFromNodeListCmd;
import com.seekon.mars.wf.engine.internal.cmd.CreateActionCmd;
import com.seekon.mars.wf.engine.internal.cmd.CreateActionHistoryCmd;
import com.seekon.mars.wf.engine.internal.cmd.CreateInstanceHistoryCmd;
import com.seekon.mars.wf.engine.internal.cmd.CreateInstancePassCmd;
import com.seekon.mars.wf.engine.internal.cmd.CreateTaskCmd;
import com.seekon.mars.wf.engine.internal.cmd.FinishProcessInstanceCmd;
import com.seekon.mars.wf.engine.internal.cmd.InitHandoverEnvCmd;
import com.seekon.mars.wf.engine.internal.cmd.ProcessReworkInstanceCmd;
import com.seekon.mars.wf.engine.internal.cmd.RemoveActionByNodeCmd;
import com.seekon.mars.wf.engine.internal.cmd.RemovePassCmd;
import com.seekon.mars.wf.engine.internal.cmd.RemoveTaskByNodeCmd;
import com.seekon.mars.wf.engine.internal.cmd.StartProcessInstanceCmd;
import com.seekon.mars.wf.engine.internal.cmd.SyncBusinessStateCmd;
import com.seekon.mars.wf.engine.internal.cmd.UntreadProcessChainCmd;
import com.seekon.mars.wf.engine.internal.cmd.UntreadStrategyCmd;
import com.seekon.mars.wf.engine.util.WFConst;

public class ExecutionServiceImpl extends AbstractServiceImpl implements
		ExecutionService {

	public void startProcessInstance(WorkflowContext ctx) {
	  if(ctx.getAction() == null){
	    ctx.setAction(WFConst.ACTION_TYPE_START_INSTANCE);
	  }
		CommandChain chain = new CommandChain();
		chain.addCommand(new StartProcessInstanceCmd());//1、启动流程实例
		chain.addCommand(new CreateTaskCmd());//2、创建第一节点的代办任务
		chain.addCommand(new CreateActionCmd());//3、记录操作日志
		chain.addCommand(new CreateInstancePassCmd());
		chain.addCommand(new SyncBusinessStateCmd());//4、同步业务单据的状态
		chain.addCommand(new FinishProcessInstanceCmd());//5、确认是否结束流程实例
		chain.addCommand(new CreateInstanceHistoryCmd());
		commandService.execute(chain);
		
		Environment env = Environment.getEnvironment();
    env.getWorkflowEventBroker().fireProcessInstanceStart(env.getInstance(), env);
	}

	public void callback(WorkflowContext ctx) {
		ctx.setAction(WFConst.ACTION_TYPE_CALLBACK_FLOW);
		CommandChain chain = new CommandChain();
		//chain.addCommand(new CheckEnvironmentCmd());
		chain.addCommand(new CheckNodeIsPassCmd());// 检查是否可以回收
		chain.addCommand(new RemoveTaskByNodeCmd());// 删除任务相关操作--？？更新任务时间
		chain.addCommand(new CreateActionHistoryCmd());// 添加历史
		chain.addCommand(new CreatTaskFromNodeListCmd());
		chain.addCommand(new RemovePassCmd()); // 删除相关执行路径
		chain.addCommand(new RemoveActionByNodeCmd());// 删除动作相关操作
		
		commandService.execute(chain);
	}

	public void commit(WorkflowContext ctx) {
		ctx.setAction(WFConst.ACTION_TYPE_COMMIT_TASK);
		CommandChain chain = new CommandChain();
		//chain.addCommand(new CheckEnvironmentCmd());
		chain.addCommand(new CompleteTaskCmd());// 1、完成当前任务
		chain.addCommand(new RemoveTaskByNodeCmd());// 2、删除当前任务
		chain.addCommand(new CreateActionCmd());// 3、记录执行动作
		chain.addCommand(new CreateInstancePassCmd());
		chain.addCommand(new SyncBusinessStateCmd());// 4、同步业务数据的状态
		chain.addCommand(new FinishProcessInstanceCmd());// 5、确认是否结束流程实例
		chain.addCommand(new CreateInstanceHistoryCmd());
		
		commandService.execute(chain);
	}

	public void handover(WorkflowContext ctx) {
	  ctx.setAction(WFConst.ACTION_TYPE_HANDOVER_TASK);
    CommandChain chain = new CommandChain();
    //chain.addCommand(new CheckEnvironmentCmd());//1、运行期环境的检查及初始化
    chain.addCommand(new InitHandoverEnvCmd());//2、移交的上下文书局的初始化
    chain.addCommand(new CreateTaskCmd());//3、创建被移交人的代办事项
    chain.addCommand(new CreateInstancePassCmd());
    chain.addCommand(new RemoveTaskByNodeCmd());//4、删除当前任务
    chain.addCommand(new CreateActionHistoryCmd());//5、记录操作日志
    
    commandService.execute(chain);
	}

	/**
	 * 重做已经结束的实例
	 */
	public void rework(WorkflowContext ctx) {
	  ctx.setAction(WFConst.ACTION_TYPE_REWORK_INSTANCE);
    CommandChain chain = new CommandChain();
    chain.addCommand(new ProcessReworkInstanceCmd());//处理重做流程，初始化相关信息
    chain.addCommand(new CreateActionHistoryCmd());// 添加历史
    commandService.execute(chain);
	}

	//节点回退
	public void untread(WorkflowContext ctx) {
		ctx.setAction(WFConst.ACTION_TYPE_UNTREAD_FLOW);
		CommandChain chain = new CommandChain();
		chain.addCommand(new UntreadStrategyCmd()); //回退前合法策略检查
		chain.addCommand(new UntreadProcessChainCmd());   //1.回退处理
		chain.addCommand(new CreatTaskFromNodeListCmd()); // 2.为回退目的节点创建代办任务
		chain.addCommand(new SyncBusinessStateCmd()); // 3.同步业务数据
		///chain.addCommand(new CreateActionHistoryCmd());//4.添加操作历史
		chain.addCommand(new RemoveActionByNodeCmd());
		commandService.execute(chain);
	}

	//为节点添加执行者
	public void addRuntimeExecutor(WorkflowContext ctx){
	  ctx.setAction(WFConst.ACTION_TYPE_ADD_EXECUTOR_FLOW);
    CommandChain chain = new CommandChain();
    chain.addCommand(new AddRuntimeExecutorCmd());
    chain.addCommand(new CreateActionHistoryCmd());
    commandService.execute(chain);
	}
}
