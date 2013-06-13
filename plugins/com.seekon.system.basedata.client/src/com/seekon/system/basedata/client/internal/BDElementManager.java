package com.seekon.system.basedata.client.internal;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.gui.GuiEngine;
import com.seekon.smartclient.gui.tags.MDefaultTableModel;

public class BDElementManager extends GuiEngine{
 
  private Container bdElementContainer = null;

  private JButton settingButton;
  
  private JTable bdElementTable;
  
  public Container getbdElementContainer() {
    initComponents();
    bdElementContainer.setVisible(true);
    return bdElementContainer;
  }
  
  private void initComponents(){
    try {
      bdElementContainer = render(BDGeneralManager.class
        .getResource("BDElementPage.xml"));
      initListeners();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void initListeners(){
    settingButton.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        UIUtilities.asyncInvoke(new DefaultInvokeHandler<Object>() {

          @Override
          public Object execute() throws Exception {
            MDefaultTableModel model = (MDefaultTableModel) bdElementTable.getModel(); 
            Vector<Map> dataList = model.getDataList();
            List<Map> deleteList = new ArrayList<Map>();
            List<Map> updateList =  new ArrayList<Map>();
            for(Map data : dataList){
              if(data.get("code") == null || data.get("code").toString().trim().length() == 0){
                deleteList.add(data);
              }else{
                updateList.add(data);
              }
            }
            if(!deleteList.isEmpty()){
              model.setSqlid("deleteBDElement");
              model.addParam("dataList", deleteList);
              model.delete();
            }
            
            for(Map data: updateList){
              if(data.get("id") != null){
                model.setSqlid("updateBDElement");
                model.addParam("data", data);
                model.update();
              }else{
                model.setSqlid("insertBDElement");
                data.put("id", UUID.randomUUID().toString());
                data.put("type", 1);
                model.addParam("data", data);
                model.insert();
              }
            }
            return null;
          }

          @Override
          public void success(Object result) {
            JOptionPane.showMessageDialog(settingButton, "设置成功！");
          }
        });
        

      }
    });
  }
  
  @Override
  protected void registerTags() {
    super.registerTags();
    this.registerTag(BDElementTableModel.class);
  }
}
