package com.seekon.mars.context;

import static com.google.inject.util.Providers.guicify;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

import static com.google.inject.internal.util.$Preconditions.checkArgument;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.guice.binder.AliasBinder;
import org.mybatis.guice.mappers.MapperProvider;

import com.google.inject.Injector;
import com.google.inject.Scopes;

public abstract class AbstractMyBatisExportModule extends AbstractExportModule {

  Configuration configuration = null;

  @Override
  protected void configure() {
    Injector injector = GuiceContext.getGlobalSharedInjector();
    if (injector != null) {
      try {
        configuration = injector.getInstance(SqlSessionFactory.class)
          .getConfiguration();
      } catch (Exception e) {

      }
    }
    initialize();
  };

  protected final void addTypeHandlerClass(
    Class<? extends TypeHandler<?>> handlerClass) {
    checkArgument(handlerClass != null, "Parameter 'handlerClass' must not be null");
    configuration.getTypeHandlerRegistry().register(handlerClass);
  }

  protected final void addSimpleAliases(final String packageName) {
    Set<Class<?>> clazzes = (getClasses(packageName));
    for (Class<?> clazz : clazzes) {
      addSimpleAlias(clazz);
    }
  }
  
  protected final void addSimpleAlias(final Class<?> type) {
    checkArgument(type != null, "Parameter 'type' must be not null");
    addAlias(type.getSimpleName()).to(type);
  }

  protected final AliasBinder addAlias(final String alias) {
    checkArgument(alias != null && alias.length() > 0,
      "Empty or null 'alias' is not valid");

    return new AliasBinder() {

      public void to(final Class<?> clazz) {
        checkArgument(clazz != null, "Null type not valid for alias '%s'", alias);
        if (configuration != null) {
          try {
            configuration.getTypeAliasRegistry().registerAlias(alias, clazz);
          } catch (Throwable e) {

          }
        }
      }

    };
  }

  protected final void addMapperClass(Class<?> mapperClass) {
    checkArgument(mapperClass != null, "Parameter 'mapperClass' must not be null");
    if (configuration != null) {
      try {
        configuration.addMapper(mapperClass);
      } catch (Throwable e) {

      }
    }
    bindMapper(mapperClass);
  }

  final <T> void bindMapper(Class<T> mapperType) {
    bind(mapperType).toProvider(guicify(new MapperProvider<T>(mapperType))).in(
      Scopes.SINGLETON);
  }

  final void addMapperClasses(Collection<Class<?>> mapperClasses) {
    checkArgument(mapperClasses != null,
      "Parameter 'mapperClasses' must not be null");

    for (Class<?> mapperClass : mapperClasses) {
      addMapperClass(mapperClass);
    }
  }

  protected final void addMapperClasses(final String packageName) {
    addMapperClasses(getClasses(packageName));
  }

  private static Set<Class<?>> getClasses(String packageName) {
    return getClasses(new ResolverUtil.IsA(Object.class), packageName);
  }

  private static Set<Class<?>> getClasses(ResolverUtil.Test test, String packageName) {
    checkArgument(test != null, "Parameter 'test' must not be null");
    checkArgument(packageName != null, "Parameter 'packageName' must not be null");
    return new ResolverUtil<Object>().find(test, packageName).getClasses();
  }

  protected final void addXmlMapperResource(String xmlResource,
    ClassLoader classLoader) {
    if (!configuration.isResourceLoaded(xmlResource)) {
      InputStream inputStream = null;
      try {
        inputStream = Resources.getResourceAsStream(classLoader, xmlResource);
      } catch (IOException e) {
        // ignore, resource is not required
      }
      if (inputStream != null) {
        XMLMapperBuilder xmlParser = new XMLMapperBuilder(inputStream,
          configuration, xmlResource, configuration.getSqlFragments());
        xmlParser.parse();
      }
    }
  }

  protected void addInterceptorClass(Class<? extends Interceptor> interceptorClass) {
    checkArgument(interceptorClass != null,
      "Parameter 'interceptorClass' must not be null");
    try {
      configuration.addInterceptor(interceptorClass.newInstance());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
