package com.seekon.mars.db.internal;

import java.util.UUID;

public class SqliteTest {

  /**
   * @param args
   * @throws Exception 
   */
  public static void main(String[] args) throws Exception {
//    Class.forName("org.sqlite.JDBC");
//    String connString = "jdbc:sqlite:E:/workspace/repository/GitHub/mars/web/mars/WebContent/client/db/mars.db";
//    Connection conn = DriverManager.getConnection(connString);
//    PreparedStatement  pst = conn.prepareStatement("create table sys_table(id, code, name, master_tab_code, is_table)");
//    pst.execute();
//    pst = conn.prepareStatement(" drop table sys_table");
//    pst.execute();
//    pst.close();
//    conn.close();
    System.out.println(UUID.randomUUID().toString().length());
  }

}
