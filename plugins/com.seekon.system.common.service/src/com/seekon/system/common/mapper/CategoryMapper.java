package com.seekon.system.common.mapper;

import com.seekon.system.common.ControlDataUpdater;
import com.seekon.system.common.model.Category;

public interface CategoryMapper {

  public Category getCategoryById(String id);

  @ControlDataUpdater(tableName = "m_sys_category", keyField = "id")
  public void addCategory(Category category);

  @ControlDataUpdater(tableName = "m_sys_category", keyField = "id")
  public void updateCategory(Category category);

  public void deleteCategory(Category category);
}
