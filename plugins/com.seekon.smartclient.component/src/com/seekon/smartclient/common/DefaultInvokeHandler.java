package com.seekon.smartclient.common;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Container;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Window;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public abstract class DefaultInvokeHandler<T> implements InvokeHandler<T> {

  /**
   * 消息默认的显示组件
   */
  private static JComponent defaultMessageComponent = null;

  /**
   * 设置默认的显示组件
   * @param defaultMessageComponent
   */
  public static void setDefaultMessageComponent(JComponent defaultMessageComponent) {
    DefaultInvokeHandler.defaultMessageComponent = defaultMessageComponent;
  }

  /**
   * 返回默认的显示组件
   */
  public static JComponent getDefaultMessageComponent() {
    return defaultMessageComponent;
  }

  private static Icon loadingIcon = new ImageIcon(
    InvokeHandler.class.getResource("/icon/large-loading.gif"));

  private JComponent messageComponent;

  /**
   * 设置下在加载的图标
   * @param image
   */
  public static void setLoadingIcon(Icon image) {
    if (image == null) {
      throw new IllegalArgumentException("image icon can't be null");
    }
    loadingIcon = image;
  }

  /**
   * 显示加载信息的组件
   */
  private JComponent loadingComponent = null;

  /**
   * 加载线程启动时间
   */
  private long startTime = System.currentTimeMillis();

  /**
   * 是否显示执行时间
   */
  private boolean showTime = true;

  /**
   * 显示时间的Label
   */
  private JLabel timeLabel = null;

  private void changeLabelLocation() {
    Container parent = loadingComponent.getParent();
    if (parent == null)
      return;
    final Dimension pSize = parent.getSize();
    final Dimension lSize = loadingComponent.getPreferredSize();
    final int x = (pSize.width - lSize.width) / 2;
    final int y = (pSize.height - lSize.height) / 2;

    Point location = loadingComponent.getLocation();
    if (location.x != x || location.y != y) {
      loadingComponent.setOpaque(false);
      loadingComponent.setBounds(x, y, lSize.width, lSize.height);
    }
  }

  public DefaultInvokeHandler() {
    this(false);
  }

  /**
   * @param showTime 是否显示加载时间
   */
  public DefaultInvokeHandler(boolean showTime) {
    this(showTime, null);
  }

  /**
   * @param showTime 是否显示加载时间
   */
  public DefaultInvokeHandler(boolean showTime, JComponent messageComponent) {
    super();
    if (messageComponent == null) {
      messageComponent = defaultMessageComponent;
    }
    this.messageComponent = messageComponent;
    this.showTime = showTime;
    if (showTime) {
      timeLabel = new JLabel(" ");
      timeLabel.setHorizontalAlignment(JLabel.CENTER);
    }
    initLoadingComponent();
  }

  private void initLoadingComponent() {
    loadingComponent = new JComponent() {
      /**
       * 显示动画的label
       */
      private JLabel label = new JLabel(loadingIcon) {

        @Override
        public void paint(java.awt.Graphics g) {
          changeLabelLocation();
          super.paint(g);
          if (!showTime) {
            return;
          }
          updateTime();
        };
      };

      {
        Dimension size = new Dimension(label.getPreferredSize());
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        c.insets = new Insets(2, 0, 2, 0);

        add(label, c);

        if (messageComponent != null) {
          size.width = Math.max(size.width,
            messageComponent.getPreferredSize().width);
          size.height += messageComponent.getPreferredSize().height;
          c.gridy++;
          add(messageComponent, c);
        }
        if (timeLabel != null) {
          size.width = Math.max(size.width, timeLabel.getPreferredSize().width);
          size.height += timeLabel.getPreferredSize().height;
          c.gridy++;
          add(timeLabel, c);
        }
        setSize(size);
      }
    };
  }

  /**
   * 执行前调用，该方法在AWT EventDispatcThread中执行
   */
  public void before() {
    startTime = System.currentTimeMillis();
    defaultShowLoading();
  }

  /**
   * 显示默认加载动画
   */
  private void defaultShowLoading() {
    Window activeWindow = DefaultKeyboardFocusManager
      .getCurrentKeyboardFocusManager().getActiveWindow();
    JLayeredPane layeredPane = null;
    if (activeWindow instanceof JFrame) {
      layeredPane = ((JFrame) activeWindow).getLayeredPane();
    } else if (activeWindow instanceof JDialog) {
      layeredPane = ((JDialog) activeWindow).getLayeredPane();
    } else {
      Applet applet = findApplet();
      if (applet instanceof JApplet) {
        layeredPane = ((JApplet) applet).getLayeredPane();
      }
    }
    if (layeredPane != null) {
      installLoadingComponent(layeredPane);
    }
  }

  /**
   * 执行后调用，该方法在AWT EventDispatcThread中执行
   */
  public void after() {
    hideLoading();
  }

  /**
   * 隐藏显示加载动画
   */
  private void hideLoading() {
    unInstallLoadingComponent(loadingComponent.getParent());
  }

  private Applet findApplet() {
    Window window = KeyboardFocusManager.getCurrentKeyboardFocusManager()
      .getActiveWindow();
    if (window instanceof Frame) {
      int componentCount = window.getComponentCount();
      for (int i = 0; i < componentCount; i++) {
        Component c = window.getComponent(i);
        if (c instanceof Applet) {
          return (Applet) c;
        }
      }
    }
    return null;
  }

  /**
   * 调用失败后执行方法，该方法在AWT EventDispatcThread中执行
   * @param e 失败的异常对象
   */
  public void failure(Exception e) {
    UIUtilities.showStaickTraceDialog(e, DefaultKeyboardFocusManager
      .getCurrentKeyboardFocusManager().getActiveWindow(), "错误 ", e.getMessage());
  }

  /**
   * 安装加载动画组件
   * @param parent 预安装显示动画组件的容器
   */
  protected void installLoadingComponent(final Container parent) {
    assert parent != null;
    parent.add(loadingComponent);
    changeLabelLocation();
    if (parent instanceof JComponent) {
      ((JComponent) parent).revalidate();
    }
    parent.repaint();
  }

  /**
   * 卸载动画显示组件
   * @param parent 要卸载显示动画组件的容器
   */
  protected void unInstallLoadingComponent(Container parent) {
    if (parent == null)
      return;
    parent.remove(loadingComponent);
    parent.invalidate();
    parent.repaint();
    Frame frame = JOptionPane.getFrameForComponent(parent);
    if (frame != null) {
      //frame.invalidate();
      frame.validate();
      frame.repaint();
    }
  }

  /**
   * 绘制加载时间
   */
  protected void updateTime() {
    NumberFormat format = NumberFormat.getInstance();
    format.setMinimumFractionDigits(3);
    BigDecimal d = new BigDecimal(System.currentTimeMillis() - startTime).divide(
      new BigDecimal(1000.0), 3, BigDecimal.ROUND_UP);
    String str = format.format(d);
    if (timeLabel != null) {
      timeLabel.setText(str);
    }
  }

  /**
   * 该方法会，返回加载线程启动时间，startTime以 {@link System#currentTimeMillis()}初始化，
   * 可以用<pre>System.currentTimeMillis() - getStartTime()</pre>计算已用时间
   * @return 加载线程启动时间
   */
  public long getStartTime() {
    return startTime;
  }
}
