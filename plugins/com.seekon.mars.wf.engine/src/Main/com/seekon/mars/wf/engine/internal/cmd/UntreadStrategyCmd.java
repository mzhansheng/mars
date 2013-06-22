package com.seekon.mars.wf.engine.internal.cmd;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.strategy.untread.AnyUntreadStrategy;

/**
 * 检查回退策略方式，根据link信息中的回退类型使用不同的判断策略
 * 
 */
public class UntreadStrategyCmd extends AbstractCommand{

  private static final long serialVersionUID = -1663951542414983741L;

  public void doExecute(Environment env) throws CommandException {
    AnyUntreadStrategy strategy=new AnyUntreadStrategy();
    strategy.apply(env);
  }

}
