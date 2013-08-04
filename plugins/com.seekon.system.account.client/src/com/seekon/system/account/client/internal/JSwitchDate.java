package com.seekon.system.account.client.internal;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import com.seekon.mars.context.http.SessionContext;
import com.seekon.smartclient.framework.component.JBaseDialog;
import com.seekon.smartclient.framework.util.ToolBarButton;
import com.seekon.system.auth.model.User;
import com.seekon.system.auth.spi.UserService;

public class JSwitchDate extends ToolBarButton {
  private static final long serialVersionUID = 3564676627067599775L;

  public JSwitchDate() {
    super(new AbstractAction() {
      private static final long serialVersionUID = 4929275473078617409L;

      @Override
      public void actionPerformed(ActionEvent e) {
        Map<String, Object> session = SessionContext.getCurrentHttpSession();
        User user = null;
        user = (User) session.get(SessionContext.KEY_LOGINED_CURRENT_USER);
        if (user != null) {
          new SwitchDate();
        }
      }
    });

    this.setText("切换日期");
    this.setToolTipText("切换日期");
    this.setIcon(new ImageIcon(JSwitchDate.class
      .getResource("/resources/calendar.png")));

  }

  static class SwitchDate extends JBaseDialog {
    private static final long serialVersionUID = -5992070991448857195L;

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

    public static final SimpleDateFormat sesssionSdf = new SimpleDateFormat(
      "yyyy-MM-dd");

    private JXDatePicker datePicker;

    public SwitchDate() {

      // 1、数据！
      final Map<String, Object> session = SessionContext.getCurrentHttpSession();
      Date oldDate = null;
      try {
        Object d = session.get(SessionContext.KEY_TRANS_DATE);
        if (d instanceof String) {
          oldDate = sesssionSdf.parse((String) d);
        } else {
          oldDate = (Date) d;
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }

      // 2、界面
      setTitle("切换日期");
      setModalityType(ModalityType.APPLICATION_MODAL);
      setLayout(new FlowLayout(FlowLayout.CENTER));

      JPanel p = new JPanel(new MigLayout("wrap", "[right][180]", "15[]15[]15[]15"));
      p.add(new JLabel("当前日期:"));
      final JLabel current = new JLabel(oldDate == null ? "未设定"
        : sdf.format(oldDate));
      p.add(current, "growx");
      p.add(new JLabel("设定日期:"));
      datePicker = new JXDatePicker(oldDate == null ? new Date() : oldDate);
      datePicker.setFormats(sdf);
      p.add(datePicker, "growx");
      JButton confirm = new JButton(new AbstractAction("确定") {
        @Override
        public void actionPerformed(ActionEvent e) {
          Date newDate = datePicker.getDate();
          String newDateStr = sesssionSdf.format(newDate);
          // 修改 客户端session内容。
          session.put(SessionContext.KEY_TRANS_DATE, newDateStr);
          // 修改 服务端session内容。
          UserService service = AccountPlugin.injector
            .getInstance(UserService.class);
          service.changeSession(session);
          // 关闭窗口
          SwitchDate.this.setVisible(false);
        }
      });
      p.add(confirm, "span 2, gapleft 30");
      add(p, BorderLayout.CENTER);

      setSize(300, 160);
      setLocationRelativeTo(null);
      setResizable(false);
      setVisible(true);
    }
  }

  static class ViewValueComboBox<T> extends JComboBox {

    private static final long serialVersionUID = -3295341362987856716L;

    private List<String> labels;

    private List<T> values;

    public ViewValueComboBox(T[] values, String[] labels) {
      if (labels == null || values == null || values.length != labels.length)
        throw new IllegalArgumentException();
      this.labels = new ArrayList<String>(values.length);
      this.values = new ArrayList<T>(values.length);
      for (int i = 0, len = values.length; i < len; ++i) {
        this.values.add(values[i]);
        this.labels.add(labels[i]);
        this.addItem(values[i]);
      }
      this.setRenderer(new Render());
    }

    public ViewValueComboBox(List<T> values, List<String> labels) {
      if (labels == null || values == null || values.size() != labels.size())
        throw new IllegalArgumentException();
      this.labels = new ArrayList<String>(labels);
      this.values = new ArrayList<T>(values);
      for (T v : values) {
        this.addItem(v);
      }
      this.setRenderer(new Render());
    }

    @Override
    public void setSelectedItem(Object anObject) {
      super.setSelectedItem(anObject);
    }

    @Override
    public Object getSelectedItem() {
      return super.getSelectedItem();
    }

    private class Render extends BasicComboBoxRenderer {

      private static final long serialVersionUID = -518229085247509806L;

      Render() {
      }

      @Override
      public Component getListCellRendererComponent(JList list, Object value,
        int index, boolean isSelected, boolean cellHasFocus) {
        ViewValueComboBox<T> cb = ViewValueComboBox.this;
        if (cb.values.contains(value)) {
          value = cb.labels.get(cb.values.indexOf(value));
        }
        return super.getListCellRendererComponent(list, value, index, isSelected,
          cellHasFocus);
      }
    }

  }

}
