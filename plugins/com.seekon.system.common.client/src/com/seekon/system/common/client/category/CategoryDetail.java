package com.seekon.system.common.client.category;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.common.model.Category;

public class CategoryDetail extends AbstractDetail<Category>{

  private static final long serialVersionUID = -8515811723222992148L;

  private Category category;
  
  private void initComponents(Category category){
    this.category = category;
    this.removeAll();
    
    this.setLayout(new MigLayout("align center"));
    this.add(new JLabel("分类名称"));
    this.add(new JLabel(category.getDisplayName()), "wrap");
    
    this.revalidate();
  }
  
  @Override
  public Category edit() {
    return category;
  }

  @Override
  public void update(Event event) {
    EventType type = event.getEventType();
    if(type == CategoryEventType.CATEGORY_SELECTED
      || type == CategoryEventType.CATEGORY_UPDATED){
      initComponents((Category) event.getSource());
    }
  }
}
