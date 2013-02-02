package com.seekon.system.common;

import java.util.List;

import com.seekon.system.common.model.Element;
import com.seekon.system.common.model.ElementType;

public interface ElementRegistry {

  public List<Element> getElementListByType(ElementType type);
}
