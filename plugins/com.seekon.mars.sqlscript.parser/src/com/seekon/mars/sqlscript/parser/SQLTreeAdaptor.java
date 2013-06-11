package com.seekon.mars.sqlscript.parser;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;

public class SQLTreeAdaptor extends CommonTreeAdaptor {

  @Override
  public Object create(Token payload) {
    return new SQLTree((CommonTree)super.create(payload));
  }

  @Override
  public Token createToken(int tokenType, String text) {
    // TODO Auto-generated method stub
    return super.createToken(tokenType, text);
  }
  
  @Override
  public Token createToken(Token fromToken) {
    // TODO Auto-generated method stub
    return super.createToken(fromToken);
  }
  
  @Override
  public Token getToken(Object t) {
    Token token = super.getToken(t);
    if(token == null){
      if(t instanceof SQLTree){
        return ((SQLTree)t).getToken();
      }
    }
    return null;
  }
  
}
