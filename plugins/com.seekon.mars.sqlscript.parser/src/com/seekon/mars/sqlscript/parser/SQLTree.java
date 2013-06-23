package com.seekon.mars.sqlscript.parser;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

public class SQLTree extends CommonTree implements Cloneable {

  private static List<String> leftTraverseWordList = new ArrayList<String>();
  
  private static List<String> parenWordList = new ArrayList<String>();
  
  static {
    leftTraverseWordList.add(".");
    leftTraverseWordList.add(",");
    leftTraverseWordList.add("=");
    leftTraverseWordList.add("like");
    leftTraverseWordList.add("not like");
    
    parenWordList.add("and");
    parenWordList.add("or");
    parenWordList.add("in");
    parenWordList.add("not in");
    
  }
  
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
    
    boolean showParen = false;
    int index = 0;
    StringBuilder buf = new StringBuilder();
    String text = this.toString().trim().toLowerCase();
    if(parenWordList.contains(text)){
      showParen = true;
    }
    
    if(showParen || leftTraverseWordList.contains(text)){
      buf.append(" ");
      
      if(showParen){
        buf.append("(");
      }
      buf.append(((SQLTree)this.children.get(0)).getSQLString());
      if(showParen){
        buf.append(")");
      }
      
      index = 1;
    }
    
    if(!this.isNil()){
      if(text.equals(".")){
        buf.append(text);
      }else{
        buf.append(" " + text + " ");
      }
    }
    
    if(showParen){
      buf.append("(");
    }
    
    for(;index < this.children.size(); index++ ){
      buf.append(((SQLTree)this.children.get(index)).getSQLString());
    }
    
    if(showParen){
      buf.append(")");
    }
    
    return buf.toString();
  }

}
