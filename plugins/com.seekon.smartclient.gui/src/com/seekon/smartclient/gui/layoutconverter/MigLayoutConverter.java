package com.seekon.smartclient.gui.layoutconverter;

import java.awt.LayoutManager;

import net.miginfocom.swing.MigLayout;

import org.swixml.Attribute;
import org.swixml.LayoutConverter;
import org.w3c.dom.Element;

public class MigLayoutConverter implements LayoutConverter {

  @Override
  public String getID() {
    return "miglayout";
  }

  @Override
  public LayoutManager convertLayoutAttribute(Attribute attr) {
    return new MigLayout();//TODO:
  }

  @Override
  public LayoutManager convertLayoutElement(Element element) {
    String layoutConstraints = Attribute.getAttributeValue(element,
      "layoutConstraints");
    String colConstraints = Attribute.getAttributeValue(element, "colConstraints");
    String rowConstraints = Attribute.getAttributeValue(element, "rowConstraints");
    return new MigLayout(layoutConstraints, colConstraints, rowConstraints);
  }

  @Override
  public Object convertConstraintsAttribute(Attribute attr) {
    return attr.getValue();
  }

  @Override
  public Object convertConstraintsElement(Element element) {
    return null;
  }

}
