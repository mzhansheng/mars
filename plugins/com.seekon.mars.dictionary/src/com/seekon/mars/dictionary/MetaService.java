package com.seekon.mars.dictionary;

public interface MetaService {

  public TableMeta getTableMeta(String tabCode);

  public FieldMeta getFieldMeta(String colCode, String tabCode);
}
