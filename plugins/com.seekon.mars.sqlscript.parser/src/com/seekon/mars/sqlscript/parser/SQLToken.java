package com.seekon.mars.sqlscript.parser;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;

public class SQLToken implements Token{

  private CommonToken delegate;

  public SQLToken(CommonToken delegate) {
    super();
    this.delegate = delegate;
  }

  public boolean equals(Object obj) {
    return delegate.equals(obj);
  }

  public int getChannel() {
    return delegate.getChannel();
  }

  public int getCharPositionInLine() {
    return delegate.getCharPositionInLine();
  }

  public CharStream getInputStream() {
    return delegate.getInputStream();
  }

  public int getLine() {
    return delegate.getLine();
  }

  public int getStartIndex() {
    return delegate.getStartIndex();
  }

  public int getStopIndex() {
    return delegate.getStopIndex();
  }

  public String getText() {
    return delegate.getText();
  }

  public int getTokenIndex() {
    return delegate.getTokenIndex();
  }

  public int getType() {
    return delegate.getType();
  }

  public int hashCode() {
    return delegate.hashCode();
  }

  public void setChannel(int channel) {
    delegate.setChannel(channel);
  }

  public void setCharPositionInLine(int charPositionInLine) {
    delegate.setCharPositionInLine(charPositionInLine);
  }

  public void setInputStream(CharStream input) {
    delegate.setInputStream(input);
  }

  public void setLine(int line) {
    delegate.setLine(line);
  }

  public void setStartIndex(int start) {
    delegate.setStartIndex(start);
  }

  public void setStopIndex(int stop) {
    delegate.setStopIndex(stop);
  }

  public void setText(String text) {
    delegate.setText(text);
  }

  public void setTokenIndex(int index) {
    delegate.setTokenIndex(index);
  }

  public void setType(int type) {
    delegate.setType(type);
  }

  public String toString() {
    return delegate.toString();
  }

  
}
