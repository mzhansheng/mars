package com.seekon.mars.db.internal;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.seekon.mars.context.GuiceContext;

public class Activator implements BundleActivator{

  static Injector injector;
  
  @Override
  public void start(BundleContext context) throws Exception {
    injector = GuiceContext.createChildInjector(new Module() {
      @Override
      public void configure(Binder arg0) {
        //nothing;
      }
    });
    
    Injector parent = injector.getParent();
    if(parent != null){
      Configuration config = parent.getInstance(SqlSessionFactory.class).getConfiguration();
      if(config != null){
        config.setJdbcTypeForNull(JdbcType.VARCHAR);//修改设置null参数的jdbc类型为varchar
      }
    }
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    injector = null;
  }

  public static Injector getInjector() {
    return injector;
  }

}
