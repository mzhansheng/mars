package com.seekon.mars.dictionary;

import java.util.HashMap;
import java.util.Map;

import com.seekon.mars.dictionary.internal.ServiceFacade;

public class MetaStore {
  private static Map<String, TableMeta> tableMetaStore = new HashMap<String, TableMeta>();

  private static Map<FieldMeta, FieldMeta> fieldMetaStore = new HashMap<FieldMeta, FieldMeta>();
  
  public static TableMeta getTableMetaByCode(String tabCode){
    TableMeta tableMeta = tableMetaStore.get(tabCode);
    if(tableMeta == null){
      tableMeta = ServiceFacade.getMetaService().getTableMeta(tabCode);
      tableMetaStore.put(tableMeta.getTableCode(), tableMeta);
    }
    
    return tableMeta;
  }
  
  public static FieldMeta getFieldMeta(String tabCode, String colCode){
    FieldMeta fieldKey = new FieldMeta();
    fieldKey.setColCode(colCode);
    TableMeta tableMeta = getTableMetaByCode(tabCode);
    fieldKey.setBelongedTable(tableMeta);
    
    FieldMeta fieldValue = fieldMetaStore.get(fieldKey);
    if(fieldValue == null){
      fieldValue = ServiceFacade.getMetaService().getFieldMeta(colCode, tabCode);
      fieldValue.setBelongedTable(tableMeta);
      fieldMetaStore.put(fieldKey, fieldValue);
    }
    return fieldValue;
  }
}
