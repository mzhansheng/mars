package com.seekon.mars.dictionary.service.internal;

import java.util.Map;

import com.seekon.mars.dictionary.FieldMeta;
import com.seekon.mars.dictionary.TableMeta;

public interface MetaMapper {

  public TableMeta getTableMeta(String tabCode);

  public FieldMeta getFieldMeta(Map<String, String> fieldMap);
}
