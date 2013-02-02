package com.seekon.mars.db.internal;

import java.util.Properties;

import javax.inject.Provider;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.JndiDataSourceProvider;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.google.inject.util.Providers;
import com.seekon.mars.context.ISharedModuleProvider;

public class DatabaseModuleProvider implements ISharedModuleProvider {

  @Override
  public Module get() {
    return new MyBatisModule() {
      @Override
      protected void initialize() {
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        initDatabase();
      }

      private void initDatabase() {
        String dbMode = System.getProperties().getProperty("db.mode");
        if (dbMode == null || dbMode.trim().length() == 0) {
          dbMode = "jndi";//"mysql";
        }

        if ("jndi".equalsIgnoreCase(dbMode)) {
          bind(String.class).annotatedWith(Names.named("jndi.initialContext"))
            .toInstance("java:comp/env");
          bind(String.class).annotatedWith(Names.named("jndi.dataSource"))
            .toInstance("m_ds");
          bindDataSourceProviderType(JndiDataSourceProvider.class);
          Names.bindProperties(binder(), createProperties("jndi"));
          return;
        }

        bindDataSourceProviderType(PooledDataSourceProvider.class);
        Names.bindProperties(binder(), createProperties(dbMode));
        
        if ("local".equalsIgnoreCase(dbMode)) {
          install(new Module() {
            @Override
            public void configure(Binder binder) {
              binder.bindConstant().annotatedWith(Names.named("JDBC.driver"))
                .to("org.sqlite.JDBC");
              binder.bind(Key.get(String.class, Names.named("JDBC.url")))
                .toProvider(Providers.guicify(new Provider<String>() {
                  @Override
                  public String get() {
                    String installArea = (String) System.getProperties().get(
                      "osgi.install.area");
                    if (installArea != null && installArea.startsWith("file:/")) {
                      installArea = installArea.substring("file:/".length());
                    }
                    return "jdbc:sqlite:" + installArea + "db/gmap.db";
                  }
                }));
            }
          });
        }

      }

      private Properties createProperties(String type) {
        Properties myBatisProperties = new Properties();
        myBatisProperties.setProperty("mybatis.environment.id", "education");
        if ("mysql".equalsIgnoreCase(type)) {
          install(JdbcHelper.MySQL);
          myBatisProperties.setProperty("JDBC.schema", "mars");
          myBatisProperties.setProperty("JDBC.username", "mars");
          myBatisProperties.setProperty("JDBC.password", "1");
        } else if ("sqlite".equalsIgnoreCase(type)) {
          //nothing
        } else if ("oracle".equalsIgnoreCase(type)) {
          install(JdbcHelper.Oracle_Thin);
          myBatisProperties.setProperty("JDBC.host", "10.11.115.186");
          myBatisProperties.setProperty("JDBC.port", "1521");
          myBatisProperties.setProperty("oracle.sid", "orcl");
          myBatisProperties.setProperty("JDBC.username", "edu");
          myBatisProperties.setProperty("JDBC.password", "1");
        }

        myBatisProperties.setProperty("JDBC.autoCommit", "false");
        myBatisProperties.setProperty(
          "mybatis.configuration.mapUnderscoreToCamelCase", "true");

        return myBatisProperties;
      }
    };
  }

}
