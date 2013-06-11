package com.seekon.system.common.client.category;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.viewedit.Page;
import com.seekon.smartclient.component.viewedit.callback.CallbackPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.common.model.Category;
import com.seekon.system.common.spi.CategoryService;

public class CreateCategoryPage extends CallbackPage {

  private static final long serialVersionUID = -5546858100249262794L;

  private CategoryForm form;

  public CreateCategoryPage(final Category parent,
    final CategoryService categoryService) {
    super();

    form = new CategoryForm();

    this.setLayout(new BorderLayout());
    this.add(form, BorderLayout.CENTER);

    JPanel tools = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton savebButton = new JButton("保存");
    tools.add(savebButton);
    this.add(tools, BorderLayout.NORTH);

    savebButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        UIUtilities.asyncInvoke(new DefaultInvokeHandler<Category>() {

          @Override
          public Category execute() throws Exception {
            Category category = form.getCategory();
            category.setParent(parent);
            category = categoryService.addCategory(category);
            return category;
          }

          @Override
          public void success(Category result) {
            JOptionPane.showMessageDialog(CreateCategoryPage.this, "保存成功！");
            ObserverUtil.notifyEvent(new Event(result,
              CategoryEventType.CATEGORY_ADDED), form);
          }
        });

      }
    });

  }

  @Override
  public void setGoBackAction(Action action) {

  }

  @Override
  public void setPreviousPage(Page page) {

  }

}
