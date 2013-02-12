package com.seekon.smartclient.gui.tags;

import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.gui.model.ComboBoxMap;

public class MDefaultComboBoxModel extends DefaultComboBoxModel{

  private static final long serialVersionUID = -5371127170873544512L;

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
        MDefaultComboBoxModel.this.removeAllElements();
        if(result != null && !result.isEmpty()){
          for(Map<String, Object> item : result){
            MDefaultComboBoxModel.this.addElement(new ComboBoxMap(item));
          }
        }
      }
    }); 
  }

  public int delete() {
    return mDataModel.delete();
  }
  
}
