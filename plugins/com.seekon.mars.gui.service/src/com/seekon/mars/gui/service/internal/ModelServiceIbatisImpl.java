package com.seekon.mars.gui.service.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.guice.transactional.Transactional;

import com.seekon.mars.gui.service.ModelService;

public class ModelServiceIbatisImpl implements ModelService {

  @Inject
  private SqlSession sqlSession;

  @Override
  public List<Map<String, Object>> getModelData(String sqlid,
    Map<String, Object> params) {
    MappedStatement mst = sqlSession.getConfiguration().getMappedStatement(sqlid);
    try {
      return sqlSession.selectList(mst.getId(), params);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<Map<String, Object>>();
  }

  @Override
  @Transactional
  public int insert(String sqlid, Map<String, Object> params) {
    MappedStatement mst = sqlSession.getConfiguration().getMappedStatement(sqlid);
    try {
      return sqlSession.insert(mst.getId(), params);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  @Transactional
  public int update(String sqlid, Map<String, Object> params) {
    MappedStatement mst = sqlSession.getConfiguration().getMappedStatement(sqlid);
    try {
      return sqlSession.update(mst.getId(), params);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  @Transactional
  public int delete(String sqlid, Map<String, Object> params) {
    MappedStatement mst = sqlSession.getConfiguration().getMappedStatement(sqlid);
    try {
      return sqlSession.delete(mst.getId(), params);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
