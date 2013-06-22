package com.seekon.mars.wf.engine.internal.expression;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import bsh.EvalError;
import bsh.Interpreter;

public class ExpressionEvaluator {

	public static Object calculate(String expression, Map context) throws EvalError {
		String buffer = expression;
		Boolean result = Boolean.FALSE;
		Interpreter interpreter = new Interpreter();
		Pattern pattern = null;
		Matcher matcher = null;
		if (context != null) {
			Iterator iterator = context.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry entry = (Entry)iterator.next();
				String key = (String)entry.getKey().toString();
				pattern = Pattern.compile(key);
				matcher = pattern.matcher(buffer);
				Object value = entry.getValue();
				if (value instanceof Number) {
					buffer = matcher.replaceAll(value.toString());
				} else if (value instanceof String) {
					buffer = matcher.replaceAll("\"" + value.toString() + "\"");
				}
			}
			result = (Boolean)interpreter.eval(buffer);
		}
		return result;
	}
	
	public static boolean matchExpr(String expression, Map context){
    if (StringUtils.isEmpty(expression)) return true;
    try{
      expression = expression.replaceAll("'", "\"");
      Object result = ExpressionEvaluator.calculate(expression, context);
      if (result == null || !(result instanceof Boolean)) {
        throw new RuntimeException("计算link()表达式 " + expression + " : " + "不能返回一个boolean型结果");
      } else {
        Boolean b = (Boolean)result;
        return b.booleanValue();
      }
    }catch(Exception e){
      throw new RuntimeException("计算link()表达式发生异常：" + e.getMessage());
    }
  }
}
