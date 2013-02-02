package com.seekon.system.auth.client.view;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.model.DatascopeOwner;
import com.seekon.system.auth.spi.DataScopeService;
import com.seekon.system.rule.client.view.ruleset.RuleTreeModel;

public abstract class AbstractDatascopeDetail extends AbstractDetail<DatascopeOwner> {

  private static final long serialVersionUID = -2287443668138654562L;

  private DatascopeOwner owner;

  private JTree datascopeTree;

  public AbstractDatascopeDetail() {
    super();

    this.setLayout(new BorderLayout());
    datascopeTree = new JTree();
    JScrollPane jsp = new JScrollPane(datascopeTree);
    setLayout(new BorderLayout());
    add(jsp, BorderLayout.CENTER);
  }

  @Override
  public DatascopeOwner edit() {
    return owner;
  }

  protected void setDatascopeOwner(final DatascopeOwner owner) {
    this.owner = owner;

    UIUtilities.asyncInvoke(new DefaultInvokeHandler<DefaultTreeModel>() {

      @Override
      public DefaultTreeModel execute() throws Exception {
        DataScopeService service = ServiceFacade.getDataScopeService();
        return new RuleTreeModel(service.getDateScopeCategory(), owner
          .getDatascopeRuleList());
      }

      @Override
      public void success(DefaultTreeModel result) {
        datascopeTree.setModel(result);
      }
    });
  }
}
