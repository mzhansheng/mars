package com.seekon.smartclient.framework;

import com.seekon.smartclient.module.AbstractResource;

/**
 * 
 * 导航菜单资源的过滤器
 *
 */
public interface NavigationResourceFilter {
  
  public AbstractResource doFilter(AbstractResource root);
  
}
