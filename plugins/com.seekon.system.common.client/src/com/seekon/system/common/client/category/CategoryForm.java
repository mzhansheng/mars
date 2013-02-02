package com.seekon.system.common.client.category;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.common.model.Category;
import com.seekon.system.common.spi.CategoryService;

public class CategoryForm extends AbstractForm<Category> {

  private static final long serialVersionUID = -3963421696000914823L;

  private PresentationModel<BindingCategory> model = null;

  private CategoryService categoryService;

  public CategoryForm() {
    super();

    model = new PresentationModel<BindingCategory>(new BindingCategory(
      new Category()));
    setLayout(new BorderLayout());
    add(layoutComponents(), BorderLayout.CENTER);

    setComplete(true);
  }

  private JComponent layoutComponents() {

    JTextField roleNameField = BasicComponentFactory.createTextField(
      model.getModel(BindingCategory.PROPERTYNAME_NAME), false);

    JPanel pane = new JPanel(new MigLayout("align center"));
    pane.add(new JLabel("分类名称:"));
    pane.add(roleNameField, "w 4cm:6cm:,wrap");
    return pane;
  }

  public void setCategoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Override
  public void edit(Category t) {
    this.model.setBean(new BindingCategory(t));
  }

  @Override
  public Category save() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<Category>() {

      @Override
      public Category execute() throws Exception {
        Category roleCategory = getCategory();
        categoryService.updateCategory(roleCategory);
        return roleCategory;
      }

      @Override
      public void success(Category roleCategory) {
        JOptionPane.showMessageDialog(CategoryForm.this, "更新成功！");
        ObserverUtil.notifyEvent(new Event(roleCategory,
          CategoryEventType.CATEGORY_UPDATED), CategoryForm.this);
      }
    });

    return getCategory();
  }

  public Category getCategory() {
    return model.getBean().getCategory();
  }
}
