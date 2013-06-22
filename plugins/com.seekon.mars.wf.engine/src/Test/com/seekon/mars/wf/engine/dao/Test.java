package com.seekon.mars.wf.engine.dao;

import java.util.ArrayList;
import java.util.List;

import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;

public class Test {
 public static void main(String args[]){
   WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
   List nodeIdList=new ArrayList();
   nodeIdList.add(new Long(2030));
   List list=runtimeDao.getActionFromNodeList(new Long(369), nodeIdList);
   ActionModel actionModel=((ActionModel)list.get(0));
   System.out.println(actionModel.getActionName());
   System.out.println(actionModel.getNodeId());
 }
}
