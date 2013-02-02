package com.seekon.smartclient.module;

public class ActionHelper {

  private static final Action helpAction = new Action("func.help", "帮助");

  private static final Action watchAction = new Action("func.watch", "查看");

  private static final Action addAction = new Action("func.add", "新增");

  private static final Action editAction = new Action("func.edit", "编辑");

  private static final Action delAction = new Action("func.delete", "删除");

  private static final Action settingAction = new Action("func.setting", "设置");

  private ActionHelper() {
  }

  public static Action getHelpAction() {
    try {
      return (Action) helpAction.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return helpAction;
  }

  public static Action getWatchAction() {
    try {
      return (Action) watchAction.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return watchAction;
  }

  public static Action getAddAction() {
    try {
      return (Action) addAction.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return addAction;
  }

  public static Action getEditAction() {
    try {
      return (Action) editAction.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return editAction;
  }

  public static Action getDeleteAction() {
    try {
      return (Action) delAction.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return delAction;
  }

  public static Action getSettingAction() {
    try {
      return (Action) settingAction.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return settingAction;
  }
}
