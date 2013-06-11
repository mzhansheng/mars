package com.seekon.system.common.spi;

import java.util.List;

import com.seekon.system.common.model.Category;

public interface CategoryService {

  public Category addCategory(Category category);

  public void updateCategory(Category category);

  public void deleteCategory(Category category);

  public void delelteCategoryList(List<Category> categoryList);

  public Category getCategory();

}
