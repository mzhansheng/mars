package com.seekon.system.basedata.client.internal;

import java.util.List;
import java.util.Map;

import com.seekon.smartclient.gui.tags.MDefaultTableModel;

public class BDElementTableModel extends MDefaultTableModel{

  private static final long serialVersionUID = -383391946193207987L;

  @Override
  public void setDataList(List<Map> dataList) {
    if(dataList == null){
      return;
    }
    for(Map data : dataList){
      Object code = data.get("code");
      if(code != null && code.toString().trim().length() > 0){
        data.put("is_used", true);
      }
    }
    super.setDataList(dataList);
  }
  
  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return columnIndex != 2;
  }
}
