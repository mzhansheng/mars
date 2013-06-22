package com.seekon.mars.wf.engine.util;

public interface WFConst {

	public static final String ACTION_TYPE_AUTHORIZE_TASK    =   "authorize_task";// 授权

	public static final String ACTION_TYPE_COMMIT_TASK       =   "commit_task";// 提交

	public static final String ACTION_TYPE_HANDOVER_TASK     =   "forward_task";// 移交
  
	public static final String ACTION_TYPE_CALLBACK_FLOW     =   "callback_flow";// 回收

	public static final String ACTION_TYPE_GIVEBACK_FLOW     =   "giveback_flow";// 回退一步

	public static final String ACTION_TYPE_TRANSFER_FLOW     =   "transfer_flow";// 流程跳转

	public static final String ACTION_TYPE_UNTREAD_FLOW      =   "untread_flow";// 回退多步
	
	public static final String ACTION_TYPE_ADD_EXECUTOR_FLOW =   "addExecutor_flow"; //添加执行者

	public static final String ACTION_TYPE_START_INSTANCE    =   "start_instance";// 启动流程
	
	public static final String ACTION_TYPE_ACTIVATE_INSTANCE =   "activate_instance";// 激活

	public static final String ACTION_TYPE_DEACTIVATE_INSTANCE = "deactivate_instance";// 冻结

	public static final String ACTION_TYPE_INTERRUPT_INSTANCE =  "interrupt_instance";// 中止

	public static final String ACTION_TYPE_RESTART_INSTANCE =    "restart_instance";// 重启

	public static final String ACTION_TYPE_REWORK_INSTANCE =     "redo_instance";// 重做
	
	public static final String ACTION_TYPE_SUSPEND_INSTANCE =    "suspend_instance";// 挂起
	
	public static final String ACTION_TYPE_TERMINATED_INSTANCE = "terminated_instance";// 中止
	

	public static final int ACTION_NODE_UNKNOWN = -9;

	public static final int TASK_IDENTITY_NORMAL = 1;

	public static final int TASK_IDENTITY_DELEGATED = -1;

	public static final int TASK_IDENTITY_DELEGATING = -9;

	public static final int TASK_TYPE_NORMAL = 0;// 普同类型待办

	public static final int TASK_TYPE_TOCOLLECT = -2;// 等待汇总类型待办任务

	public static final int TASK_TYPE_COLLECTED = -1;// 已经汇总类型待办任务

	public static final int TASK_TYPE_TOCOLLECT_DETAIL = -999;// 汇总类型待办任务

	public static final String TASK_NODE = "2";

	public static final String SPLIT_NODE = "3";

	public static final String MERGE_NODE = "4";

	public static final int INSTANCE_STATUS_ACTIVE = 1;// 激活

	public static final int INSTANCE_STATUS_DEACTIVE = -1;// 冻结

	public static final int INSTANCE_STATUS_TERMINATED = 8;// 人工终止

	public static final int INSTANCE_STATUS_FINISH = 9;// 正常完成
	
	public static final int INSTANCE_STATUS_SUSPEND = -9;// 挂起

	public static final int INSTANCE_STATUS_RESTART = 0;// 重启

	/* 执行方式 此处的定义指的是执行人的方式，而不是and/or */
	public static final String EXECUTORS_METHOD_SOLO = "0";// 独签

	public static final String EXECUTORS_METHOD_PARALLEL = "1";// 并签

	public static final String EXECUTORS_METHOD_SERIAL = "2";// 顺序签

	public static final String EXECUTORS_POLICY_SOLO = "0";// 独签

	public static final String EXECUTORS_POLICY_PARALLEL = "1";// 并签

	public static final String NUMBERORPERCENT_NUMBER = "0";// 单位是数量

	public static final String NUMBERORPERCENT_PERCENT = "1";// 单位是百分比

	public static final String PROCESS_INST_ID_FIELD = "PROCESS_INST_ID";

	public static final String INITIAL_END_TIME = "00000000000000";

	public static final String WF_COMPANY_CODE = "WF_COMPANY_CODE";

	public static final String WF_ORG_CODE = "WF_ORG_CODE";

	public static final String WF_POSITION_ID = "WF_POSITION_ID";
	public static final String WF_POSITION_CODE = "WF_POSITION_CODE";

	public static final String ND = "ND";

	/* 提交者与执行者关系 */
	public static final String EXECUTOR_RELATION_NONE = "0";// 无关系

	public static final String EXECUTOR_RELATION_MANAGER = "1";// 组织上级

	public static final String EXECUTOR_RELATION_SELF = "2";// 自己

	public static final String EXECUTOR_RELATION_BUSINESS_SUPPERIOR = "3";// 业务上级

	/*节点类型*/
	public static final String NODE_TYPE_START="0"; //开始
	public static final String NODE_TYPE_END="1"; //结束
	public static final String NODE_TYPE_TASK="2"; //任务
	public static final String NODE_TYPE_NAVIGATION="3"; //路径
	public static final String NODE_TYPE_EVENT="4";//
	public static final String NODE_TYPE_AND="5";//
	public static final String NODE_TYPE_OR="6";//
}
