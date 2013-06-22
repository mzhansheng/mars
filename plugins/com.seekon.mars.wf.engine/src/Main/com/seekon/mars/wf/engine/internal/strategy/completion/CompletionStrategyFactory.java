package com.seekon.mars.wf.engine.internal.strategy.completion;

import java.util.ArrayList;
import java.util.List;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.expression.ExpressionEvaluator;
import com.seekon.mars.wf.engine.internal.strategy.CompletionStrategy;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.util.WFConst;

/**
 * 
 * 节点提交送审后执行策略的工厂类
 * 1、若节点的执行方式为独签则只需要一人提交此节点即可完成结束
 * 2、否则，获取此节点对应的后续连接，依次执行节点的完成策略
 * 
 */
public class CompletionStrategyFactory {

  public static CompletionStrategy[] getCompletionStrategy(Environment env){
    NodeModel currentNode = env.getCurrentNode();
    if(WFConst.EXECUTORS_METHOD_SOLO.equals(currentNode.getExecutorsMethod())){//独签 
      return new CompletionStrategy[]{new AnyiCompletionStrategy()};
    }
    if(WFConst.EXECUTORS_METHOD_SERIAL.equals(currentNode.getExecutorsMethod())){//顺签，虚拟link并得全部通过
      LinkModel link = new LinkModel();
      link.setPassValue(new Double(100));
      return new CompletionStrategy[]{new RatioCompletionStrategy(link)};
    }
    
    List linkList = new ArrayList();
    List completionStrategyList = new ArrayList();
    if(env.isManual()){
      Long linkId = env.getContext().getSelectedNextLinkId();
      linkList.add(env.getTemplate().getLink(linkId));
    }
    
    if(linkList.isEmpty()){
      linkList = currentNode.getToLinkList();
    }

    for(int i = 0; i < linkList.size(); i++){
      LinkModel tmpLink = (LinkModel)linkList.get(i);
      boolean match = ExpressionEvaluator.matchExpr(tmpLink.getExpression(), env.getAllVariableMap());
      if(match){
        if(WFConst.NUMBERORPERCENT_NUMBER.equals(tmpLink.getNumberOrPercent()) && tmpLink.getPassValue() != null){
          completionStrategyList.add(new NumericalCompletionStrategy(tmpLink));
        }else if(WFConst.NUMBERORPERCENT_PERCENT.equals(tmpLink.getNumberOrPercent()) && tmpLink.getPassValue() != null){
          completionStrategyList.add(new RatioCompletionStrategy(tmpLink));
        }else{
          completionStrategyList.add(new AnyiCompletionStrategy());
        }
      }
    }
    
    return (CompletionStrategy[])completionStrategyList.toArray(new CompletionStrategy[completionStrategyList.size()]);
  }
}
