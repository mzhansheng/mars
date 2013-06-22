package com.seekon.mars.wf.engine.internal.strategy.rollback;

import com.seekon.mars.wf.engine.internal.strategy.RollbackStrategy;
import com.seekon.mars.wf.engine.model.NodeModel;

public class RollbackStrategyFactory {

  public static RollbackStrategy getRollbackStrategy(NodeModel node){
    return new AllRollbackStrategy();
  }
}
