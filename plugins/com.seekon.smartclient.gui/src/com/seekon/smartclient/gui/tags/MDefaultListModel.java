package com.seekon.smartclient.gui.tags;

import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.gui.model.CodeNameMap;

public class MDefaultListModel extends DefaultListModel{

  private static final long serialVersionUID = 2133227212559263844L;

  private MDataModel mDataModel = new MDefaultDataModel();

  public void setSqlid(String sqlid) {
    mDataModel.setSqlid(sqlid);
  }

  public String getSqlid() {
    return mDataModel.getSqlid();
  }

  public void setParams(String paramString) {
    mDataModel.setParams(paramString);
  }

  public Map<String, Object> getParams() {
    return mDataModel.getParams();
  }

  public void addParam(String name, Object value) {
    mDataModel.addParam(name, value);
  }

  public void refresh() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<List<Map<String, Object>>>() {

      @Override
      public List<Map<String, Object>> execute() throws Exception {
        return (List<Map<String, Object>>) mDataModel.refresh();
      }

      @Override
      public void success(List<Map<String, Object>> result) {
        MDefaultListModel.this.removeAllElements();
        if(result != null && !result.isEmpty()){
          for(Map<String, Object> item : result){
            MDefaultListModel.this.addElement(new CodeNameMap(item));
          }
        }
      }
    });
  }

}
