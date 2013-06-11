package com.seekon.system.org.client.view;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;

import com.seekon.smartclient.component.viewedit.ObserverPage;

public class OrganizationPage extends ObserverPage {

  private static final long serialVersionUID = 5554668310802351541L;

  private OrgTreePage orgTreePage;

  private OrgElementContainer orgElementContainer;

  public OrganizationPage() {
    super();

    orgTreePage = new OrgTreePage();
    orgElementContainer = new OrgElementContainer();

    JSplitPane jsp = new JSplitPane();
    jsp.setLeftComponent(orgTreePage);
    jsp.setRightComponent(orgElementContainer);

    this.setLayout(new BorderLayout());
    this.add(jsp, BorderLayout.CENTER);
  }

  public OrgTreePage getOrgTreePage() {
    return orgTreePage;
  }

  public OrgElementContainer getOrgElementContainer() {
    return orgElementContainer;
  }

}
