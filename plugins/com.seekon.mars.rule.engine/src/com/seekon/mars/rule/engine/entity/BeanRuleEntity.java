package com.seekon.mars.rule.engine.entity;

import java.lang.reflect.Method;

public abstract class BeanRuleEntity implements RuleEntity {

	@Override
	public Object getProperty(String propertyName) {
		Object value = null;

		try {
			Class clazz = this.getClass();
			String firstCha = propertyName.substring(0, 1).toUpperCase();
			String getMethodName = "get" + firstCha + propertyName.substring(1);
			Method getMethod = clazz.getMethod(getMethodName);
			value = getMethod.invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}

}
