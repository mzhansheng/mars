package com.seekon.system.common.spi;

import com.seekon.system.common.model.Element;

public interface ElementService {
  public Element getElementByCode(String eleCode);
  
  public Element getElementById(String eleId);
}
