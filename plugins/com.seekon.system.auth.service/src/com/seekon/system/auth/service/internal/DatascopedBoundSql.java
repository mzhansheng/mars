package com.seekon.system.auth.service.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;

import com.seekon.mars.rule.engine.specification.Visitor;
import com.seekon.mars.rule.engine.specification.visitor.SqlConditionVisitor;
import com.seekon.mars.sqlscript.parser.SQLLexer;
import com.seekon.mars.sqlscript.parser.SQLParser;
import com.seekon.mars.sqlscript.parser.SQLTokenStream;
import com.seekon.mars.sqlscript.parser.SQLTree;
import com.seekon.mars.sqlscript.parser.SQLTreeAdaptor;
import com.seekon.system.rule.model.Rule;
import com.seekon.system.rule.model.RuleElement;

/**
 * 
 * 具有数据权限范围的sql
 * 通过对sql语法分析和用户所具有的数据权限对查询的sql添加数据范围的过滤条件
 * 
 */
public class DatascopedBoundSql extends BoundSql {

  private static Map<String, SQLTree> sqlTreeMap = new HashMap<String, SQLTree>();

  private Visitor sqlConditionVisitor = new SqlConditionVisitor();

  private Set<Rule> datascopeSet;
  
  private String datascopedSql = null;
  
  public DatascopedBoundSql(Configuration configuration, BoundSql delegate, Set<Rule> datascopeSet) {
    super(configuration, delegate.getSql(), delegate.getParameterMappings(),
      delegate.getParameterObject());
    this.datascopeSet = datascopeSet;
  }
  
  @Override
  public String getSql() {
    if(datascopedSql == null){
      datascopedSql = getUserDatascopedSql(super.getSql());
    }
    return datascopedSql;
  }
  
  private String getUserDatascopedSql(String originalSql) {
    String sql = originalSql.replaceAll("[?]", "'@@'");
    SQLTree sqlTree = sqlTreeMap.get(sql);
    if (sqlTree == null) {
      SQLLexer lexer = new SQLLexer(new ANTLRStringStream(sql));
      SQLTokenStream tokenStream = new SQLTokenStream(lexer);
      SQLParser parser = new SQLParser(tokenStream);
      parser.setTreeAdaptor(new SQLTreeAdaptor());
      try {
        sqlTree = (SQLTree) parser.select_statement().getTree();
      } catch (RecognitionException e) {
        e.printStackTrace();
      }
      sqlTreeMap.put(sql, sqlTree);
    }

    if (sqlTree == null) {
      return originalSql;
    }

    SQLTree cloneSqlTree = null;
    try {
      cloneSqlTree = (SQLTree) sqlTree.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

    for (Rule rule : datascopeSet) {
      List<RuleElement> ruleElementList = rule.getRuleElementList();
      if (ruleElementList != null && !ruleElementList.isEmpty()) {
        for (RuleElement ruleElement : ruleElementList) {
          addDatascopeToSQLTree(cloneSqlTree, ruleElement);
        }
      }
    }
    return cloneSqlTree.getSQLString().replaceAll("'@@'", "?");
  }

  private void addDatascopeToSQLTree(SQLTree sqlTree, RuleElement ruleElement) {
    try {
      String condition = (String) ruleElement.getRuleSpecification().accept(
        sqlConditionVisitor);
      SQLTree conditionTree = sqlTreeMap.get(condition);
      if (conditionTree == null) {
        SQLParser sqlParser = new SQLParser(new SQLTokenStream(new SQLLexer(
          new ANTLRStringStream(condition))));
        sqlParser.setTreeAdaptor(new SQLTreeAdaptor());
        conditionTree = (SQLTree) sqlParser.expression().getTree();
        sqlTreeMap.put(condition, conditionTree);
      }
      addDatascopeTreeToSQLTree(sqlTree, 0, ruleElement.getElement().getTable()
        .getTableCode(), conditionTree);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void addDatascopeTreeToSQLTree(SQLTree sqlTree, int nodeIndex,
    String tableCode, SQLTree conditionTree) {
    List<SQLTree> children = (List<SQLTree>) sqlTree.getChildren();
    if (children == null || children.isEmpty()) {
      return;
    }

    for (int i = 0; i < children.size(); i++) {
      SQLTree child = children.get(i);
      Token token = child.getToken();
      if (token.getType() == SQLLexer.ID) {
        if (tableCode.equalsIgnoreCase(token.getText())) {//表名一样
          SQLTree fromParent = (SQLTree) sqlTree.getParent();////from节点的上级
          if (fromParent != null && fromParent.getChildCount() > nodeIndex) {
            SQLTree whereTree = (SQLTree) fromParent.getChild(nodeIndex + 1);
            if (whereTree != null) {
              SQLTree tmpTree = new SQLTree(new CommonToken(SQLLexer.AND_SYM, "and"));
              tmpTree.addChild(whereTree.getChild(0));
              tmpTree.addChild(conditionTree);
              conditionTree = tmpTree;
              whereTree.deleteChild(0);
            } else {
              whereTree = new SQLTree(new CommonToken(SQLLexer.WHERE, "where"));
            }
            whereTree.addChild(conditionTree);
          }

        }
      }
      addDatascopeTreeToSQLTree(child, i, tableCode, conditionTree);
    }
  }
}
