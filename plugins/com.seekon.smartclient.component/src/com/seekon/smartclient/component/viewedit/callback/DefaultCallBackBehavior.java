package com.seekon.smartclient.component.viewedit.callback;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.seekon.smartclient.component.viewedit.Page;
import com.seekon.smartclient.component.viewedit.PageContainer;

public class DefaultCallBackBehavior implements CallbackBehavior {

  private PageContainer target;

  public DefaultCallBackBehavior(PageContainer target) {
    this.target = target;
  }

  @Override
  public void callback(CallbackPage page) {
    final Page previousPage = target.findCurrentPage();
    page.setPreviousPage(previousPage);
    page.setGoBackAction(new AbstractAction("Go back!") {
      @Override
      public void actionPerformed(ActionEvent e) {
        target.removeCurrentPage();
        target.showPage(previousPage.getPageId());
      }
    });
    target.addPage(page, page.getPageId());
  }

}
