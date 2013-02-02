package com.seekon.smartclient.component.viewedit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.seekon.smartclient.component.viewedit.callback.CallbackAble;
import com.seekon.smartclient.component.viewedit.callback.CallbackBehavior;
import com.seekon.smartclient.component.viewedit.callback.CallbackPage;
import com.seekon.smartclient.component.viewedit.callback.DefaultCallBackBehavior;

/**
 * View-Edit-Save Mode Page
 * 
 */
public abstract class VESPage<T> extends AbstractPage implements CallbackAble {

  private static final String EDIT_PAGE = "EDIT_PAGE";

  private static final String VIEW_PAGE = "VIEW_PAGE";

  private PageContainer target;

  private CallbackBehavior callBackBehavior;

  private AbstractPage viewPage;

  private AbstractPage editPage;

  protected AbstractForm<T> form;

  protected AbstractDetail<T> detail;

  public VESPage() {

    initComponents();

    // Set layout
    setLayout(new BorderLayout());

    // Add Target
    target = new PageContainer();
    add(target, BorderLayout.CENTER);

    // call back behavior
    callBackBehavior = new DefaultCallBackBehavior(target);

    // view page
    target.addPage(viewPage, viewPage.getPageId());

    // edit page
    target.addPage(editPage, editPage.getPageId());

    // show view page
    target.showPage(VIEW_PAGE);
  }

  private void initComponents() {

    viewPage = new AbstractPage() {
      {
        setLayout(new BorderLayout());

        if (isEditableByAuth()) {
          JComponent viewToolBar = new JPanel() {
            {
              setLayout(new FlowLayout(FlowLayout.RIGHT));

              Component extToolbar = viewToolbar();
              if (extToolbar != null) {
                this.add(extToolbar);
              }
              JButton button = new JButton("编辑");
              button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  edit();
                  showEditPage();
                }
              });
              this.add(button);
            }
          };
          add(viewToolBar, BorderLayout.NORTH);
        }

        detail = getDetail();
        add(detail, BorderLayout.CENTER);
      }

      @Override
      public String getPageId() {
        return VIEW_PAGE;
      }

      @Override
      public String getPageName() {
        return getDetail().getPageName();
      }
    };

    editPage = new AbstractPage() {

      class SaveFormAction extends AbstractAction implements PropertyChangeListener {
        public SaveFormAction(AbstractForm form) {
          super("保存");
          setEnabled(form.isComplete());
          form.addCompleteListener(this);
        }

        public void actionPerformed(ActionEvent e) {
          save();
          showViewPage();
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          boolean complete = (Boolean) evt.getNewValue();
          setEnabled(complete);
        }
      }

      class CancelFormAction extends AbstractAction {
        public CancelFormAction() {
          super("取消");
        }

        public void actionPerformed(ActionEvent e) {
          cancel();
          showViewPage();
        }
      }

      {
        setLayout(new BorderLayout());

        // prepare content
        form = getForm();
        add(form, BorderLayout.CENTER);

        if (isEditableByAuth()) {
          // prepare tool bar
          JComponent editToolBar = new JPanel() {
            {
              setLayout(new FlowLayout(FlowLayout.RIGHT));

              Component extToolbar = editToolbar();
              if (extToolbar != null) {
                this.add(extToolbar);
              }

              SaveFormAction saveFormAction = new SaveFormAction(form);
              JButton saveButton = new JButton(saveFormAction);
              this.add(saveButton);

              CancelFormAction cancelFormAction = new CancelFormAction();
              JButton cancelButton = new JButton(cancelFormAction);
              this.add(cancelButton);
            }
          };
          add(editToolBar, BorderLayout.NORTH);
        }
      }

      @Override
      public String getPageId() {
        return EDIT_PAGE;
      }

      @Override
      public String getPageName() {
        return getForm().getPageName();
      }
    };
  }

  protected void showViewPage() {
    target.showPage(VIEW_PAGE);
  }

  protected void showEditPage() {
    target.showPage(EDIT_PAGE);
  }

  @Override
  public void callback(CallbackPage page) {
    callBackBehavior.callback(page);
  }

  protected void cancel() {

  }

  protected void edit() {
    form.edit(detail.edit());
  }

  protected void save() {
    form.save();
  }

  protected Component editToolbar() {
    return null;
  }

  protected Component viewToolbar() {
    return null;
  }

  protected abstract boolean isEditableByAuth();

  protected abstract AbstractForm<T> getForm();

  protected abstract AbstractDetail<T> getDetail();

}
