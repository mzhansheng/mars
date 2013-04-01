package com.seekon.system.common.service.internal;

import com.google.inject.Inject;
import com.seekon.system.common.mapper.ElementMapper;
import com.seekon.system.common.model.Element;
import com.seekon.system.common.spi.ElementService;

public class ElementServiceImpl implements ElementService{
  
  @Inject
  private ElementMapper elementMapper;
  
  @Override
  public Element getElementByCode(String eleCode) {
    return elementMapper.getElementByCode(eleCode);
  }

}
