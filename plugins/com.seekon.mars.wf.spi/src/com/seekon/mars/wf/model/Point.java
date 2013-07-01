package com.seekon.mars.wf.model;

import java.io.Serializable;

public class Point implements Serializable{
  private int x;
  private int y;
  
  public Point(int x,int y){
    this.x=x;
    this.y=y;
  }
  public int getX() {
    return x;
  }
  public void setX(int x) {
    this.x = x;
  }
  public int getY() {
    return y;
  }
  public void setY(int y) {
    this.y = y;
  }
  public String toString(){
    return "["+x+","+y+"]";
  }
}