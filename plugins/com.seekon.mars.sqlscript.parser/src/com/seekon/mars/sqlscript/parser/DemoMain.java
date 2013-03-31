package com.seekon.mars.sqlscript.parser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.tree.CommonTree;

public class DemoMain {
  public static void main(String[] args) {

  //String sql = " select * from as_user where user_id = '2' and (user_name like '%66' or user_name like '777%')";
  String sql = " select user_name, sex from as_user u join as_emp e on u.user_id = e.emp_code where u.user_id = '@@@'" +
  		" or u.user_id in (select id from as_ss)";
  SQLLexer lexer = new SQLLexer(new ANTLRStringStream(sql));
  TokenRewriteStream tokenStream = new TokenRewriteStream(lexer);
  SQLParser parser = new SQLParser(tokenStream);
  
  try {
    CommonTree tree = (CommonTree) parser.select_statement().getTree();
    tokenStream.insertAfter(2, "sss");
    System.out.println(tree.toStringTree());
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  }
}
