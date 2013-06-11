package com.seekon.system.auth.client;

import com.seekon.smartclient.component.viewedit.event.EventType;

public enum AuthorityEventType implements EventType {

  NEW_USER_ADDED, /* 添加新用户 */
  NEW_USER_SELECTED, /* 新的用户被选中 */
  USERS_SELECTED, /* 多个用户被选中 */
  USER_CATEGORY_SELECTED, /* 新的类别被选中 */
  USER_ROOT_SELECTED, /* 根节点被选中 */
  NONE_USER_SELECTIED, /* 没有被选中的 */
  USERS_DELETED, /* 用户被删除 */
  USER_BASIC_INFO_UPDATED, /* 用户更新 */
  USER_ROLE_UPDATED, /*用户角色更新*/
  USER_DATASCOPE_RULE_UPDATED, /*用户数据范围更新*/

  NONE_ROLE_SELECTIED, ROLE_ROOT_SELECTED,

  ROLE_SELECTED, ROLES_SELECTED, ROLE_ADDED, ROLES_DELETED,

  ROLE_BASIC_INFO_UPDATED, ROLE_RESOURCE_UPDATED, ROLE_DATASCOPE_RULE_UPDATED//,
  
  //ROLE_CATEGORY_ADDED, ROLE_CATEGORY_DELETED,
  
  //DATA_SCOPE_CATEGORY_ADDED, DATA_SCOPE_CATEGORY_DELETED,
  
  //DATA_SCOPE_RULE_ADDED, DATA_SCOPE_RULE_DELETED,
}
