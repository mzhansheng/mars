package com.seekon.system.auth.client.view.element;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jdesktop.swingx.JXTreeTable;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.framework.util.AuthUtil;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.smartclient.module.Resource;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.client.util.ElementUtil;
import com.seekon.system.auth.client.view.AuthResourcePage;
import com.seekon.system.common.model.Element;
import com.seekon.system.common.model.Option;

public class ElementManager extends AuthResourcePage {

  private static final long serialVersionUID = 8588396259206855839L;

  private JXTreeTable elementTree;

  private ElementTreeTableModel treeTableModel;

  private Option authElementOption;

  private boolean editable = false;
  
  public ElementManager(Resource resource) {
    super(resource);
    init();
  }

  protected void init() {
    this.setLayout(new BorderLayout());
    this.initToolbar();
    this.initClientPane();
  }

  protected void initToolbar() {

    JButton settingButton = new JButton("设置");
    settingButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        List<Element> authElementList = new ArrayList<Element>();
        List<Element> elementList = treeTableModel.getElementList();
        for (Element element : elementList) {
          if (element.isEnable()) {
            authElementList.add(element);
          }
        }
        authElementOption.setOptValue(authElementList);
        UIUtilities.asyncInvoke(new DefaultInvokeHandler<Option>() {

          @Override
          public Option execute() throws Exception {
            if (authElementOption.getId() == null) {
              ServiceFacade.getAuthElementService().addOption(authElementOption);
            } else {
              ServiceFacade.getAuthElementService().updateOption(authElementOption);
            }
            return authElementOption;
          }

          @Override
          public void success(Option result) {
            JOptionPane.showMessageDialog(elementTree, "设置成功！");
          }
        });
      }
    });

    JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    if(AuthUtil.isActionAuthorizedToUser(this.getResource(), ActionHelper.getSettingAction())){
      toolbar.add(settingButton);
      editable = true;
    }

    this.add(toolbar, BorderLayout.NORTH);
  }

  protected void initClientPane() {
    authElementOption = ElementUtil.getAuthElementOption();
    if (authElementOption == null) {
      authElementOption = new Option();
      authElementOption.setOptCode(ElementUtil.KEY_AUTH_ELEMENT_OPTION);
    }

    List<Element> elementList = ElementUtil.getAuthElementList(authElementOption,
      false);
    treeTableModel = new ElementTreeTableModel(elementList);
    elementTree = new JXTreeTable(treeTableModel);
    elementTree.getColumn(2).setCellEditor(
      new ElementCellEditor(new JCheckBox(), treeTableModel));
    elementTree.setEditable(editable);
    
    this.add(new JScrollPane(elementTree), BorderLayout.CENTER);
  }

  class ElementCellEditor extends DefaultCellEditor {

    private static final long serialVersionUID = -4642340815270901009L;

    private JCheckBox m_checkBox;

    public ElementCellEditor(JCheckBox checkBox, ElementTreeTableModel treeTableModel) {
      super(checkBox);
      this.m_checkBox = checkBox;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
      Component component = getComponent();
      if (component instanceof JCheckBox) {
        m_checkBox = (JCheckBox) component;
        Object node = elementTree.getPathForRow(row).getLastPathComponent();
        treeTableModel.setValueAt(m_checkBox.isSelected(), node, column);
        return m_checkBox;
      } else
        return component;
    }
  }
}
