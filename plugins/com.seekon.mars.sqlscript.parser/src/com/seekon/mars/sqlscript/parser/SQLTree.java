package com.seekon.mars.sqlscript.parser;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

public class SQLTree extends CommonTree implements Cloneable {

  public SQLTree() {
    super();
  }

  public SQLTree(CommonTree node) {
    super(node);
  }

  public SQLTree(Token t) {
    super(t);
  }

  @Override
  public Tree dupNode() {
    return new SQLTree((CommonTree) super.dupNode());
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    Tree cloneObj = this.dupNode();
    if(this.children != null){
      for(Object child :children){
        cloneObj.addChild((Tree) ((SQLTree)child).clone());
      }
    }
    return cloneObj;
  }

  @Override
  public String toString() {
    if (isNil()) {
      return "nil";
    }
    if (getType() == 0) {
      return "<errornode>";
    }
    if (this.token == null) {
      return null;
    }
    return this.token.getText();
  }

  public String getSQLString() {
    if ((this.children == null) || (this.children.isEmpty())) {
      return toString();
    }

    StringBuilder buf = new StringBuilder();
    if (!isNil() && this.children.size() == 2) {
      buf.append(((SQLTree)this.children.get(0)).getSQLString());
      String text = toString();
      if(text.equals(".")){
        buf.append(text);
      }else{
        buf.append(" " + text + " ");
      }
      buf.append(((SQLTree)this.children.get(1)).getSQLString());
    } else {
      if (!isNil()) {
        buf.append(toString() + " ");
      }
      for (int i = 0; (this.children != null) && (i < this.children.size()); ++i) {
        SQLTree t = (SQLTree) this.children.get(i);
        if (i > 0) {
          buf.append(' ');
        }
        buf.append(t.getSQLString());
      }
    }
    return buf.toString();
  }

}
