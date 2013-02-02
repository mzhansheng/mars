package com.seekon.system.auth.service.internal;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.seekon.mars.context.http.SessionContext;
import com.seekon.mars.db.util.MybatisInterceptorUtil;
import com.seekon.mars.rule.engine.specification.Visitor;
import com.seekon.mars.rule.engine.specification.visitor.SqlConditionVisitor;
import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.Resource;
import com.seekon.system.auth.Action;
import com.seekon.system.auth.Datascope;
import com.seekon.system.auth.model.ResourceWrapper;
import com.seekon.system.auth.model.Role;
import com.seekon.system.auth.model.User;
import com.seekon.system.rule.model.Rule;
import com.seekon.system.rule.model.RuleElement;

@Intercepts({
  @Signature(type = Executor.class, method = "query", args = {
    MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
  @Signature(type = Executor.class, method = "query", args = {
    MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,
    CacheKey.class, BoundSql.class }) })
public class DatascopeMethodInterceptor implements Interceptor {

  private static final String KEY_DADA_SCOPE_SQL = "datascopeSql";

  private Visitor sqlConditionVisitor = new SqlConditionVisitor();

  public Object intercept(Invocation invocation) throws Throwable {
    checkAndSetDatascopeSqlParameter(invocation);
    Object result = invocation.proceed();
    return result;
  }

  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  public void setProperties(Properties properties) {
  }

  protected void checkAndSetDatascopeSqlParameter(Invocation invocation) {
    Datascope datascope = (Datascope) MybatisInterceptorUtil
      .getMapperClassMethodAnnotation((MappedStatement) invocation.getArgs()[0],
        Datascope.class);
    if (datascope != null) {
      Set<Rule> datascopeSet = getUserAssignedDatascopeList(datascope);
      String datascopeSql = getDatascopeSql(datascopeSet);
      Object parameter = invocation.getArgs()[1];
      if (parameter instanceof Map) {
        ((Map) parameter).put(KEY_DADA_SCOPE_SQL, datascopeSql);
      }
    }
  }

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

  protected String getDatascopeSql(Set<Rule> datascopeSet) {
    StringBuffer sb = new StringBuffer();
    for (Rule rule : datascopeSet) {
      List<RuleElement> ruleElementList = rule.getRuleElementList();
      if (ruleElementList != null && !ruleElementList.isEmpty()) {
        int elementIndex = 0;
        sb.append("(");
        for (RuleElement ruleElement : ruleElementList) {
          if (elementIndex > 0) {
            sb.append(" and ");
          }
          sb.append(ruleElement.getRuleSpecification().accept(sqlConditionVisitor));
          elementIndex++;
        }
        sb.append(") or ");
      }
    }
    if (sb.length() == 0) {
      return null;
    }

    sb = new StringBuffer(sb.substring(0, sb.length() - " or ".length()));//TODO:使用变量绑定的方式
    return sb.toString();
  }
}
