package com.seekon.smartclient.framework.component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class JStatusBar extends JPanel {

	private static final long serialVersionUID = -5146087549547160169L;
	/**
	 * JStatusBarPanel列表
	 * 
	 * @supplierCardinality 0..5
	 */
	private List<JStatusBarPanel> panels = new ArrayList<JStatusBarPanel>();

	/**
	 * 构造方法，默认他建一个JStatusBarPanel
	 */
	public JStatusBar() {
		addPanel(new JStatusBarPanel(" "));
		initialization();
	}

	/**
	 * 插入一个JStatusBarPanel到当前对象
	 * 
	 * @param index
	 *          int - 索引，注意，起点为0
	 * @param panel
	 *          JStatusBarPanel
	 */
	public void insertPanel(int index, JStatusBarPanel panel) {
		panels.add(index, panel);
		update();
	}

	/**
	 * 添加一个JStatusBarPanel
	 * 
	 * @param panel
	 *          JStatusBarPanel
	 */
	public void addPanel(JStatusBarPanel panel) {
		panels.add(panel);
		update();
	}

	/**
	 * 移去JStatusBarPanel
	 * 
	 * @param panel
	 *          JStatusBarPanel
	 */
	public void removePanel(JStatusBarPanel panel) {
		panels.remove(panel);
		update();
	}

	/**
	 * 根据index移去一个JStatusBarPanel
	 * 
	 * @param index
	 *          int
	 */
	public void removePanel(int index) {
		panels.remove(index);
		update();
	}

	public JStatusBarPanel getPanel(int index) {
		return panels.get(index);
	}

	/**
	 * 返回JStatusBarPanel总数
	 * 
	 * @return int
	 */
	public int getPanelCount() {
		return panels.size();
	}

	/**
	 * 更新视图，在JStatusBarPanel列表改变后需调用此方法
	 */
	public void update() {
		removeAll();
		initialization();
	}

	public void clearPanels() {
		panels.clear();
		update();
	}

	/**
	 * 根据JStatusBarPanel列表画界面
	 */
	private void initialization() {
		setLayout(new GridBagLayout());
		for (int i = 0; i < panels.size(); i++) {
			JStatusBarPanel j = panels.get(i);

			if (i == 0) {
				// 完全填充
				add(j, new GridBagConstraints(i, 0, 1, 1, 1.0, .0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,
								0, 0, 0), 0, 0));
			} else {
				// 正常填充
				add(j, new GridBagConstraints(i, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,
								0, 0, 0), 0, 0));
			}

			j.setShowResizeWindowIcon(i == panels.size() - 1);
		}
		revalidate();
	}
}
