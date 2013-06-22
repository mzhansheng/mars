package com.seekon.mars.wf.engine.internal.cmd;

import java.util.ArrayList;
import java.util.List;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.internal.strategy.AssignmentStrategy;
import com.seekon.mars.wf.engine.internal.strategy.assignment.AssignmentStrategyFactory;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;

/**
 * 
 * 创建代办任务
 * 1、根据当前节点获取任务分配策略，得到代办任务列表
 * 2、持久化代办任务列表
 * 3、发送代办任务创建的事件
 * 
 */
public class CreateTaskCmd extends AbstractCommand{

  private static final long serialVersionUID = 3445584025147228028L;
    
  public void doExecute(Environment env) throws CommandException {
    AssignmentStrategy[] assignmentStrategyList = AssignmentStrategyFactory.getAssignmentStrategy(env);
    List taskList = new ArrayList();
    for(int i = 0; i < assignmentStrategyList.length; i++){
      taskList.addAll(assignmentStrategyList[i].apply(env));
    }
    
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    for(int i = 0; i < taskList.size(); i++){
      runtimeDao.createCurrentTask((CurrentTaskModel)taskList.get(i));
      //TODO:发送创建代办任务事件
    }
  }

}
