package com.seekon.system.auth.service.internal;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.seekon.mars.context.http.SessionContext;
import com.seekon.mars.db.util.MybatisInterceptorUtil;
import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.Resource;
import com.seekon.system.auth.Action;
import com.seekon.system.auth.Datascope;
import com.seekon.system.auth.model.ResourceWrapper;
import com.seekon.system.auth.model.Role;
import com.seekon.system.auth.model.User;
import com.seekon.system.rule.model.Rule;

@Intercepts({
  @Signature(type = Executor.class, method = "query", args = {
    MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
  @Signature(type = Executor.class, method = "query", args = {
    MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,
    CacheKey.class, BoundSql.class }) })
public class DatascopeMethodInterceptor implements Interceptor {

  //private static final String KEY_DADA_SCOPE_SQL = "datascopeSql";

  public Object intercept(Invocation invocation) throws Throwable {
    MappedStatement mst = (MappedStatement) invocation.getArgs()[0];
    Datascope datascope = (Datascope) MybatisInterceptorUtil
      .getMapperClassMethodAnnotation(mst, Datascope.class);
    SqlSource sqlSource = mst.getSqlSource();
    try {
      if (datascope != null) {
        Set<Rule> datascopeSet = getUserAssignedDatascopeList(datascope);
        if (datascopeSet != null && !datascopeSet.isEmpty()) {
          setSqlSource(mst, new DatascopedSqlSource(sqlSource, datascopeSet, mst.getConfiguration()));
        }
      }
      Object result = invocation.proceed();
      return result;
    } catch (Throwable e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      setSqlSource(mst, sqlSource);
    }
  }

  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  public void setProperties(Properties properties) {
  }

  //  protected void checkAndSetDatascopeSqlParameter(Invocation invocation) {
  //    Datascope datascope = (Datascope) MybatisInterceptorUtil
  //      .getMapperClassMethodAnnotation((MappedStatement) invocation.getArgs()[0],
  //        Datascope.class);
  //    if (datascope != null) {
  //      Set<Rule> datascopeSet = getUserAssignedDatascopeList(datascope);
  //      //      String datascopeSql = getDatascopeSql(datascopeSet);
  //      //      Object parameter = invocation.getArgs()[1];
  //      //      if (parameter instanceof Map) {
  //      //        ((Map) parameter).put(KEY_DADA_SCOPE_SQL, datascopeSql);
  //      //      }
  //    }
  //  }

  /**
   * 针对当前操作用户所拥有的数值权限的集合，含：角色对应的数值权限和用户的全局数值权限
   * @param datascope
   * @return
   */
  protected Set<Rule> getUserAssignedDatascopeList(Datascope datascope) {
    Action[] actions = datascope.actions();
    User user = (User) SessionContext.getCurrentHttpSession().get(
      SessionContext.KEY_LOGINED_CURRENT_USER);

    Set<Rule> datascopeSet = new HashSet<Rule>();
    datascopeSet.addAll(user.getDatascopeRuleList());

    List<Role> userRoleList = user.getRoleList();
    for (Action action : actions) {
      Resource resource = new Resource(action.resourceId(), "");
      resource.addChild(new com.seekon.smartclient.module.Action(action.id(), ""));
      for (Role role : userRoleList) {
        ResourceWrapper rw = role.getResource();
        if (rw != null) {
          AbstractResource roleResource = rw.getResource();
          if (roleResource != null && roleResource.contains(resource)) {
            datascopeSet.addAll(role.getDatascopeRuleList());
          }
        }
      }
    }

    return datascopeSet;
  }

  private void setSqlSource(MappedStatement mst, SqlSource sqlSource){
    try{
      Field field = MappedStatement.class.getDeclaredField("sqlSource");
      field.setAccessible(true);
      field.set(mst, sqlSource);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  //  protected String getDatascopeSql(Set<Rule> datascopeSet) {
  //    StringBuffer sb = new StringBuffer();
  //    for (Rule rule : datascopeSet) {
  //      List<RuleElement> ruleElementList = rule.getRuleElementList();
  //      if (ruleElementList != null && !ruleElementList.isEmpty()) {
  //        int elementIndex = 0;
  //        sb.append("(");
  //        for (RuleElement ruleElement : ruleElementList) {
  //          if (elementIndex > 0) {
  //            sb.append(" and ");
  //          }
  //          sb.append(ruleElement.getRuleSpecification().accept(sqlConditionVisitor));
  //          elementIndex++;
  //        }
  //        sb.append(") or ");
  //      }
  //    }
  //    if (sb.length() == 0) {
  //      return null;
  //    }
  //
  //    sb = new StringBuffer(sb.substring(0, sb.length() - " or ".length()));//TODO:使用变量绑定的方式
  //    return sb.toString();
  //  }
}
