package com.seekon.system.common.mapper;

import com.seekon.system.common.model.Element;

public interface ElementMapper {
  public Element getElementByCode(String eleCode);
  
  public Element getElementById(String id);
  
}
