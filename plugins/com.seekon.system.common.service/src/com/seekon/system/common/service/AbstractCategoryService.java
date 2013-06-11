package com.seekon.system.common.service;

import java.util.List;
import java.util.UUID;

import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;
import com.seekon.system.common.mapper.CategoryMapper;
import com.seekon.system.common.model.Category;
import com.seekon.system.common.spi.CategoryService;

public abstract class AbstractCategoryService implements CategoryService {

  @Inject
  CategoryMapper categoryMapper;

  protected Category getCategoryById(String id) {
    return categoryMapper.getCategoryById(id);
  }

  @Transactional
  @Override
  public Category addCategory(Category category) {
    category.setId(UUID.randomUUID().toString());
    categoryMapper.addCategory(category);
    return category;
  }

  @Transactional
  @Override
  public void updateCategory(Category category) {
    categoryMapper.updateCategory(category);
  }

  @Override
  @Transactional
  public void deleteCategory(Category category) {
    for (Category child : category.getChildren()) {
      deleteCategory(child);
    }
    categoryMapper.deleteCategory(category);
  }

  @Override
  @Transactional
  public void delelteCategoryList(List<Category> categoryList) {
    for (Category category : categoryList) {
      this.deleteCategory(category);
    }
  }
}
