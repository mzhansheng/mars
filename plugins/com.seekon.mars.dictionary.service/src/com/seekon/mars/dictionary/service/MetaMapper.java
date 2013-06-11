package com.seekon.mars.dictionary.service;

import java.util.List;
import java.util.Map;

import com.seekon.mars.dictionary.FieldMeta;
import com.seekon.mars.dictionary.TableMeta;

public interface MetaMapper {

  public TableMeta getTableMeta(String tabCode);

  public FieldMeta getFieldMeta(Map<String, String> fieldMap);
  
  public List<FieldMeta> getFiledMetaListByTableCode(String tableCode);
}
