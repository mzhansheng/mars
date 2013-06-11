package com.seekon.mars.rule.engine.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 规则实体
 * 
 * @author mdrtwell
 */
public class MapRuleEntity implements RuleEntity {

	private Map<String, Object>	properties;

	public MapRuleEntity() {
    super();
    properties = new HashMap<String, Object>();
  }

  public MapRuleEntity(Map<String, Object> properties) {
    super();
    this.properties = properties;
  }

  public void addProperty(String propertyName, Object value) {
		properties.put(propertyName, value);
	}

	public Object getProperty(String propertyName) {
		return properties.get(propertyName);
	}

}
