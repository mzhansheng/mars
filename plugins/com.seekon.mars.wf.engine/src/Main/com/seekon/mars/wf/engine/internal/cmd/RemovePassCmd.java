package com.seekon.mars.wf.engine.internal.cmd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.runtime.PassModel;
import com.seekon.mars.wf.engine.util.WFConst;

public class RemovePassCmd extends AbstractCommand {

  private static final long serialVersionUID = -1428113327674595100L;

  public void doExecute(Environment env) throws CommandException {
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    String type = env.getContext().getAction();
    if (type.equals(WFConst.ACTION_TYPE_TERMINATED_INSTANCE)
      || type.equals(WFConst.ACTION_TYPE_RESTART_INSTANCE)) {
      PassModel pass=new PassModel();
      pass.setInstanceId(env.getInstance().getInstanceId());
      runtimeDao.removeInstancePass(pass);
    } else {
      List passIdList = new ArrayList();
      List passLst = env.getPassPathList();
      Iterator itr = passLst.iterator();
      while (itr.hasNext()) {
        PassModel passModel = (PassModel) itr.next();
        passIdList.add(passModel.getPassCountId());
      }
      runtimeDao.removeInstancePassByIdList(passIdList);
    }
  }

}
