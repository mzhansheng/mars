package com.seekon.mars.wf.client.panel;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.seekon.mars.wf.client.trace.TranceGraphProcessor;

/**
 * 抽象工作流操作面板
 * @author Administrator
 *
 */
public abstract class AbstractDisplayPanel extends JComponent {
  protected TranceGraphProcessor topGraphPanel;

  protected JPanel bottomPanel;

  private JSplitPane splitVsPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);//上下分隔

  public void initGUI() {
    try {
      topGraphPanel = createTopGraph();
    } catch (Exception e) {
      e.printStackTrace();
    }
    bottomPanel = createBottomPanel();
    JScrollPane topScrollPane = new JScrollPane();
    topScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    topScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    topScrollPane.getViewport().add(topGraphPanel);
    //    JScrollPane bottomScrollPane = new JScrollPane();
    //    bottomScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    //    bottomScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    //    bottomScrollPane.getViewport().add(bottomPanel);
    splitVsPane.setResizeWeight(0.8);
    splitVsPane.setOneTouchExpandable(true);
    splitVsPane.setLeftComponent(topScrollPane);
    splitVsPane.setRightComponent(bottomPanel);
    setLayout(new BorderLayout());
    add(splitVsPane, BorderLayout.CENTER);
  }
  
  protected Map<String, String> initFiledsNameMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("actionId", "活动ID");
    map.put("instanceId", "实例ID");
    map.put("nodeId", "节点ID");
    map.put("actionName", "业务名称");
    map.put("executor", "执行者");
    map.put("executeTime", "执行时间");
    map.put("description", "描述");
    //map.put("limitExecuteTime", "期限");
    map.put("coCode", "单位代码");
    map.put("orgCode", "内部机构代码");
    map.put("posiCode", "职位代码");
    map.put("owner", "执行者");
    map.put("nd", "年度");
    return map;
  }

  public abstract TranceGraphProcessor createTopGraph() throws Exception;

  public abstract JPanel createBottomPanel();

  public abstract void addGraphListener();

}
