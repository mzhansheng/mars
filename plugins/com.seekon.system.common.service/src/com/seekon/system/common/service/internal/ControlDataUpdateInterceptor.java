package com.seekon.system.common.service.internal;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.seekon.mars.context.http.SessionContext;
import com.seekon.mars.db.util.MybatisInterceptorUtil;
import com.seekon.system.common.ControlDataUpdater;
import com.seekon.system.common.mapper.ControlDataMapper;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = {
  MappedStatement.class, Object.class }) })
public class ControlDataUpdateInterceptor implements Interceptor {

  ControlDataMapper controlDataMapper;

  public Object intercept(Invocation invocation) throws Throwable {
    Object result = invocation.proceed();
    checkAndUpdateTableControlData(invocation);
    return result;
  }

  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  public void setProperties(Properties properties) {
  }

  protected void checkAndUpdateTableControlData(Invocation invocation) {
    ControlDataUpdater controlDataUpdater = (ControlDataUpdater) MybatisInterceptorUtil
      .getMapperClassMethodAnnotation((MappedStatement) invocation.getArgs()[0],
        ControlDataUpdater.class);
    if (controlDataUpdater != null) {
      updateTableControlData(controlDataUpdater, invocation);
    }
  }

  private void updateTableControlData(ControlDataUpdater controlDataUpdater,
    Invocation invocation) {
    MappedStatement mst = (MappedStatement) invocation.getArgs()[0];
    String propertyName = controlDataUpdater.keyField();
    String id = (String) mst.getConfiguration()
      .newMetaObject(invocation.getArgs()[1]).getValue(propertyName);

    String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      .format(new Date(System.currentTimeMillis()));
    String userId = null;
    Map<String, Object> session = SessionContext.getCurrentHttpSession();
    if (session != null) {
      userId = (String) session.get("svUserCode");
    }

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("table_name", controlDataUpdater.tableName());
    params.put("last_modifier", userId);
    params.put("last_modify_time", currentDate);
    params.put("id", id);
    if (mst.getSqlCommandType() == SqlCommandType.INSERT) {
      params.put("creator", userId);
      params.put("create_time", currentDate);
    }

    if (controlDataMapper == null) {
      controlDataMapper = Activator.injector.getInstance(ControlDataMapper.class);
    }
    controlDataMapper.updateTableControlData(params);
  }
}
