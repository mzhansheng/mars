package com.seekon.mars.wf.client.tree;

public class TreeType {
  public static final int ALL_LEVEL=0;//所有层次的单位树
  public static final int COMPENY_UP_LEVEL=1;//显示当前单位上级单位
  public static final int ORG_UP_LEVEL=2;//显示当前组织上级（包括单位）
  public static final int COMPANY_DOWN_LEVEL=3;//显示当前单位所有直接下级
  public static final int ORG_DOWN_LEVEL=4;//显示当前组织所有下级组织（要显示当前组织的所属单位）
}
