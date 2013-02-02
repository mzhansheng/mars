package com.seekon.smartclient.framework.internal.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import org.osgi.framework.Bundle;

import com.seekon.smartclient.framework.component.JSimpleDialog;
import com.seekon.smartclient.framework.internal.Activator;
import com.seekon.smartclient.framework.util.ResourceProvide;

final class PluginManagerDialog extends JSimpleDialog {
	/**
	 * 节点高度
	 */
	private static final int ITEM_HEIGHT = 70;

	/**
	 * 节点宽度
	 */
	private static final int ITEM_WIDTH = 412;

	/**
	 * 显示插件的TreeView
	 */
	private JTree tree = new JTree();

	/**
	 * 根节点
	 */
	private DefaultMutableTreeNode root = null;

	/**
	 * 初始化组件
	 */
	@Override
	protected void initComponent(Container c) {
		Bundle[] bundles = Activator.getBundleContext().getBundles();

		setTitle(ResourceProvide.getResourceString("PluginManagerDialog.title"));
		setSize(420, 480);
		clientPanel.setLayout(new BorderLayout());
		JScrollPane sp = new JScrollPane();
		sp.getViewport().add(tree);
		root = new DefaultMutableTreeNode("root");
		for (Bundle bundle : bundles) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(bundle);
			root.add(node);
		}
		tree.setModel(new DefaultTreeModel(root));

		tree.setRootVisible(false);
		tree.setEditable(false);
		tree.setCellRenderer(new PlugInTreeCellRenderer());
		tree.setRowHeight(0);
		// tree.setCellEditor(new PlugInTreeCellEditor());
		// 选定第1条并编辑
		if (tree.getRowCount() > 0) {
			tree.setSelectionPath(tree.getPathForRow(0));
			tree.startEditingAtPath(tree.getPathForRow(0));
		}
		clientPanel.add(sp, BorderLayout.CENTER);

		closeButton.setText(ResourceProvide.getResourceString("Close"));
		tree.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				TreePath tp = tree.getPathForLocation(e.getX(), e.getY());
				if (tp != null) {
					if (tree.getSelectionPath() != tp) {
						tree.setSelectionPath(tp);
					}
				}

			}
		});
	}

	public PluginManagerDialog(Window owner) {
		super(owner);
	}

	private class PlugInTreeCellRenderer implements TreeCellRenderer {
		private PluginDisplayPanel displayPanel = new PluginDisplayPanel();

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean selected, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			if (!(node.getUserObject() instanceof Bundle))
				return new DefaultTreeCellRenderer();
			Bundle bundle = (Bundle) node.getUserObject();
			displayPanel.selected = selected;
			displayPanel.update(bundle);
			return displayPanel;

		}
	}

	private class PluginDisplayPanel extends JPanel {
		private Color selectedBackground = new Color(0x335EA8);
		private Color unSelectedBackground = this.getBackground();

		private Color selectedForeground = Color.WHITE;
		private Color unSelectedForeground = Color.BLACK;

		private static final long serialVersionUID = 9217613373477747609L;
		private JLabel label = new JLabel();
		private JLabel labelDsr = new JLabel();
		private String noFormatLabelText = ResourceProvide
				.getResourceString("Plugin")
				+ ResourceProvide.getResourceString("Colon")
				+ "%s "
				+ ResourceProvide.getResourceString("Version")
				+ ResourceProvide.getResourceString("Colon") + "%s";

		private boolean selected = false;

		@Override
		public Dimension getPreferredSize() {
			Dimension size = super.getPreferredSize();
			size.width = ITEM_WIDTH;
			size.height = ITEM_HEIGHT;
			return size;
		}

		public void update(Bundle bundle) {
			// String iconURL = (String)bundle.getHeaders().get("Bundle-Icon");
			// if (iconURL != null){
			// ImageIcon icon = new ImageIcon(bundle.getResource(iconURL));
			// label.setIcon(icon);
			// }
			// else{
			// label.setIcon(null);
			// }
			label.setIcon(null);

			label.setText(String.format(noFormatLabelText, bundle.getSymbolicName(),
					bundle.getHeaders().get("Bundle-Version")));
			labelDsr.setText(bundle.getHeaders().get("Bundle-Name") == null ? "无描述"
					: (String) bundle.getHeaders().get("Bundle-Name"));
			if (selected) {
				this.setBackground(selectedBackground);
				label.setForeground(selectedForeground);
				labelDsr.setForeground(selectedForeground);
			} else {
				this.setBackground(unSelectedBackground);
				label.setForeground(unSelectedForeground);
				labelDsr.setForeground(unSelectedForeground);
			}
		}

		PluginDisplayPanel() {
			setBorder(BorderFactory.createEtchedBorder());
			setLayout(new GridBagLayout());
			add(label, new GridBagConstraints(0, 0, 1, 1, 1.0, 0,
					GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5,
							8), 0, 0));

			add(labelDsr, new GridBagConstraints(0, 1, 1, 1, 1.0, 0,
					GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5,
							8), 0, 0));
		}
	}
}
