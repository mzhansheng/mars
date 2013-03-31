package com.seekon.mars.dictionary.service.internal;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.seekon.mars.dictionary.FieldMeta;
import com.seekon.mars.dictionary.MetaService;
import com.seekon.mars.dictionary.TableMeta;
import com.seekon.mars.dictionary.service.MetaMapper;

public class MetaServiceImpl implements MetaService{

  @Inject
  MetaMapper metaMapper;
  
  @Override
  public TableMeta getTableMeta(String tabCode) {
    return metaMapper.getTableMeta(tabCode);
  }

  @Override
  public FieldMeta getFieldMeta(String colCode, String tabCode) {
    Map<String, String> fieldMap = new HashMap<String, String>();
    fieldMap.put("colCode", colCode);
    fieldMap.put("tabCode", tabCode);
    return metaMapper.getFieldMeta(fieldMap);
  }

}
