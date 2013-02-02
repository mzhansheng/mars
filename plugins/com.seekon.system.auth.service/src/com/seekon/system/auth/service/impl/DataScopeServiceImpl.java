package com.seekon.system.auth.service.impl;

import com.google.inject.Inject;
import com.seekon.system.auth.spi.DataScopeService;
import com.seekon.system.common.mapper.CategoryMapper;
import com.seekon.system.common.model.Category;

public class DataScopeServiceImpl implements DataScopeService {

  @Inject
  CategoryMapper categoryMapper;

  @Override
  public Category getDateScopeCategory() {
    return categoryMapper.getCategoryById("sys.auth.data.scope");
  }

}
