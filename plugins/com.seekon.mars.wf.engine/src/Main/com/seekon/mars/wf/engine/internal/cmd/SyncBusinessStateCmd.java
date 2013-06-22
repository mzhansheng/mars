package com.seekon.mars.wf.engine.internal.cmd;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.LinkStateModel;
import com.seekon.mars.wf.engine.model.NodeStateModel;
import com.seekon.mars.wf.engine.model.runtime.LinkStaffModel;

/**
 * 
 * 同步业务数据的状态
 *
 */
public class SyncBusinessStateCmd extends AbstractCommand{

  private static final long serialVersionUID = -4707912781895273901L;
  
  private String table;
  private String field;
  private String value;
  
  public SyncBusinessStateCmd(){
    
  }
  public SyncBusinessStateCmd(String table, String field, String value){
    this.table = table;
    this.field = field;
    this.value = value;
  }
  
  public void doExecute(Environment env) throws CommandException {
    if(table == null || field == null || value == null){     
      Set nextLinkStaffSet = env.getNextLinkStaffSet();
      Iterator iterator = nextLinkStaffSet.iterator();//TODO：现有的方式是仅仅获取第一个节点，同步此节点上的状态
      if(iterator.hasNext()){
        LinkStaffModel linkStaff = (LinkStaffModel)iterator.next();
        List nodeStateList = linkStaff.getLink().getNextNode().getNodeStateList();
        if(nodeStateList != null && nodeStateList.size() > 0){
          NodeStateModel nodeState = (NodeStateModel)nodeStateList.get(0);//TODO:仅仅获取任意的节点状态        
          table = nodeState.getBindState().getTableId();
          field = nodeState.getBindState().getFieldId();
          value = nodeState.getStateValue();       
        }else{
          List linkStateList = linkStaff.getLink().getLinkStateList();//取节点上的状态
          if(linkStateList != null && linkStateList.size() > 0){
            LinkStateModel linkState = (LinkStateModel)linkStateList.get(0);//TODO:仅仅获取任意的路径状态
            table = linkState.getBindState().getTableId();
            field = linkState.getBindState().getFieldId();
            value = linkState.getStateValue(); 
          }
        }
      }
    }
    
    if(table != null && field != null && value != null){
      WFRuntimeMapper dao = WFDaoFactory.getWFRuntimeDao();
      dao.updateBusinessState(table, field, value, env.getInstance().getInstanceId());
    }
  }

}
