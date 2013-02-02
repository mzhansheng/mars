package com.seekon.system.common.client.category;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.VESPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.system.common.model.Category;
import com.seekon.system.common.spi.CategoryService;

public class CategoryPage extends VESPage<Category> {

  private static final long serialVersionUID = -8875495537367585672L;
  
  private CategoryForm categoryForm;
  
  public CategoryPage(CategoryService categoryService) {
    super();
    
    categoryForm.setCategoryService(categoryService);
  }
  
  @Override
  protected AbstractForm<Category> getForm() {
    categoryForm = new CategoryForm();
    return categoryForm;
  }

  @Override
  protected AbstractDetail<Category> getDetail() {
    return new CategoryDetail();
  }

  @Override
  public void update(Event event) {
    if(event.getEventType() == CategoryEventType.CATEGORY_SELECTED){
      this.showViewPage();
    }
  }

  @Override
  protected boolean isEditableByAuth() {
    return true;
  }
}
