package com.seekon.mars.sqlscript.parser;

import org.antlr.runtime.ANTLRStringStream;

public class DemoMain {
  public static void main(String[] args) {

  //String sql = " select * from as_user where user_id = '2' and (user_name like '%66' or user_name like '777%')";
  String sql = " select user_id, user_name from as_user u join as_emp e on u.user_id = e.emp_code where (u.user_id = '@@@')" +
  		" and u.user_id not in (select id from as_ss)";
  SQLLexer lexer = new SQLLexer(new ANTLRStringStream(sql));
  SQLTokenStream tokenStream = new SQLTokenStream(lexer);
  SQLParser parser = new SQLParser(tokenStream);
  parser.setTreeAdaptor(new SQLTreeAdaptor());
  try {
    SQLTree tree =  (SQLTree)parser.select_statement().getTree();
    //tokenStream.insertAfter(2, "sss");
    SQLTree cloneTree = (SQLTree)tree.clone();
    System.out.println(sql);
    System.out.println(cloneTree.toStringTree());
    System.out.println(cloneTree.getSQLString());
  } catch (Exception e) {
    e.printStackTrace();
  }
  }
}
