/**
 * 
 */
package com.seekon.smartclient.component.viewedit;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JComponent;

public class PageContainer extends JComponent {

  private static final long serialVersionUID = -8810092610076557602L;

  private CardLayout cardLayout;

  public PageContainer() {
    cardLayout = new CardLayout();
    setLayout(cardLayout);
  }

  public void addPage(Page page, String constraints) {
    add(page, constraints);
    showPage(constraints);
  }

  public Page findCurrentPage() {
    Component visibleComp = null;

    synchronized (getTreeLock()) {
      int ncomponents = getComponentCount();
      for (int i = 0; i < ncomponents; i++) {
        Component comp = getComponent(i);
        if (comp.isVisible()) {
          visibleComp = comp;
          break;
        }
      }
    }

    return (Page) visibleComp;
  }

  public void first() {
    cardLayout.first(this);
  }

  public int getPageCount() {
    return getComponentCount();
  }

  public void last() {
    cardLayout.last(this);
  }

  public void next() {
    cardLayout.next(this);
  }

  public void previous() {
    cardLayout.previous(this);
  }

  public void removeCurrentPage() {
    Component comp = (Component) findCurrentPage();

    if (comp == null)
      return;

    remove(comp);
    cardLayout.removeLayoutComponent(comp);
    revalidate();

    // Page page = (Page) comp;
    // page.disposePage();
  }

  public void showPage(String name) {
    cardLayout.show(this, name);
    revalidate();
  }

}
