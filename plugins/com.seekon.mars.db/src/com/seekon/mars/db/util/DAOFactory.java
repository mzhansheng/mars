package com.seekon.mars.db.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.seekon.mars.db.internal.Activator;

public class DAOFactory {

  private static final Logger log = Logger.getLogger(DAOFactory.class);

  private static DAOFactory g_theDAOFactory = new DAOFactory();

  private DAOFactory() {
  }

  /** 获取数据库连接工厂实例（常用写法 <code>DAOFactory.getInstance().getConnection()</code>） */
  public static DAOFactory getInstance() {
    return g_theDAOFactory;
  }

  /**
   * 获取数据库连接，调用名为 webglDS 的数据源
   * @return 数据库连接
   * @throws RuntimeException 如果找不到数据源或获取数据哭连接失败
   */
  public Connection getConnection() {
    DataSource ds = getDataSource();
    try {
      return ds.getConnection();
    } catch (SQLException e) {
      log.error("获取数据库连接出错", e);
      throw new RuntimeException(e);
    }
  }

  public DataSource getDataSource() {
    return Activator.getInjector().getInstance(DataSource.class);
  }
}
