package com.seekon.mars.wf.engine;
public interface IWorkflowContextAware {
    public void setRuntimeContext(WorkflowContext ctx);
    public WorkflowContext getRuntimeContext();
}