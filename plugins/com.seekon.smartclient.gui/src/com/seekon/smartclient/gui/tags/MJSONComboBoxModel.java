package com.seekon.smartclient.gui.tags;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;

import org.json.JSONArray;
import org.json.JSONObject;

import com.seekon.smartclient.gui.model.CodeNameMap;

public class MJSONComboBoxModel extends DefaultComboBoxModel{

  private static final long serialVersionUID = -1027586881808333600L;
  
  public void setData(String data) {
    if(data == null || data.trim().length() == 0){
      return;
    }
    try{
      this.removeAllElements();
      JSONArray jsonArray = new JSONArray(data);
      for(int i = 0; i < jsonArray.length(); i++){
        Map item = new HashMap();
        JSONObject obj = (JSONObject) jsonArray.get(i);
        Iterator iterator = obj.keys();
        while(iterator.hasNext()){
          String key = (String) iterator.next();
          item.put(key, obj.get(key));
        }
        this.addElement(new CodeNameMap(item));
      }
    }catch(Exception e){
      e.printStackTrace();
    } 
  }
  
}
