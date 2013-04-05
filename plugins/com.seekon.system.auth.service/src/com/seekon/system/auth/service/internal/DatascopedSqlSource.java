package com.seekon.system.auth.service.internal;

import java.util.Set;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import com.seekon.system.rule.model.Rule;

public class DatascopedSqlSource implements SqlSource {

  private SqlSource delegate;

  private Set<Rule> datascopeSet;

  private Configuration configuration;

  public DatascopedSqlSource(SqlSource delegate, Set<Rule> datascopeSet,
    Configuration configuration) {
    super();
    this.delegate = delegate;
    this.datascopeSet = datascopeSet;
    this.configuration = configuration;
  }

  public BoundSql getBoundSql(Object parameterObject) {
    BoundSql boundSql = delegate.getBoundSql(parameterObject);
    return new DatascopedBoundSql(configuration, boundSql, datascopeSet);
  }

}
