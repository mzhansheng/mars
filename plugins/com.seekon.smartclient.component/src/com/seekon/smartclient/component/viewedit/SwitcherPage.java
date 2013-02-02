package com.seekon.smartclient.component.viewedit;

import java.awt.BorderLayout;

import com.seekon.smartclient.component.viewedit.callback.CallbackAble;
import com.seekon.smartclient.component.viewedit.callback.CallbackBehavior;
import com.seekon.smartclient.component.viewedit.callback.CallbackPage;
import com.seekon.smartclient.component.viewedit.callback.DefaultCallBackBehavior;

public class SwitcherPage extends AbstractPage implements CallbackAble {

  private static final long serialVersionUID = 6286962374343429550L;

  private CallbackBehavior callbackBehavior;

  private PageContainer target;

  public SwitcherPage() {
    setLayout(new BorderLayout());
    target = new PageContainer();
    add(target, BorderLayout.CENTER);
    callbackBehavior = new DefaultCallBackBehavior(target);
  }

  @Override
  public void callback(CallbackPage page) {
    callbackBehavior.callback(page);
  }

  public void setPage(Page page) {
    target.removeCurrentPage();
    target.addPage(page, page.getPageId());
  }

}
