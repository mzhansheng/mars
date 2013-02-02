package com.seekon.system.rule.client.view.ruleset;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTree;

import com.seekon.smartclient.framework.util.AuthUtil;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.system.common.client.category.CreateCategoryAction;
import com.seekon.system.common.client.category.DeleteCategoryAction;
import com.seekon.system.rule.client.internal.ServiceFacade;
import com.seekon.system.rule.client.view.RuleMainPage;
import com.seekon.system.rule.client.view.rule.CreateRuleAction;
import com.seekon.system.rule.client.view.rule.DeleteRuleAction;
import com.seekon.system.rule.spi.RuleService;

public class RuleTreePopupMenu extends JPopupMenu {

  private static final long serialVersionUID = 3694434974347256544L;

  protected JTree tree;

  protected RuleMainPage ruleMainPage;

  public RuleTreePopupMenu(JTree tree, RuleMainPage ruleMainPage) {
    super();
    this.tree = tree;
    this.ruleMainPage = ruleMainPage;

    initPopupMenu();
  }

  protected void initPopupMenu() {
    RuleService service = ServiceFacade.getRuleService();

    boolean add = AuthUtil.isActionAuthorizedToUser(ruleMainPage.getResource(),
      ActionHelper.getAddAction());
    boolean delete = AuthUtil.isActionAuthorizedToUser(ruleMainPage.getResource(),
      ActionHelper.getDeleteAction());
    int count = 0;
    
    if (add) {
      CreateCategoryAction createCategoryAction = new CreateCategoryAction(tree,
        ruleMainPage.getRuleSettingContainer(), service);
      JMenuItem item = new JMenuItem(createCategoryAction);
      add(item);
      count++;
    }
    
    if (delete) {
      DeleteCategoryAction deleteCategoryAction = new DeleteCategoryAction(tree,
        service);
      JMenuItem item = new JMenuItem(deleteCategoryAction);
      add(item);
      count++;
    }
    
    if (count > 0) {
      add(new JSeparator());
    }
    
    if (add) {
      CreateRuleAction createUserAction = new CreateRuleAction(tree);
      JMenuItem item = new JMenuItem(createUserAction);
      add(item);
    }
    
    if (delete) {
      DeleteRuleAction deleteUserAction = new DeleteRuleAction(tree);
      JMenuItem item = new JMenuItem(deleteUserAction);
      add(item);
    }
  }

}
