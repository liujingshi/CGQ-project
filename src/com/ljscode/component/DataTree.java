package com.ljscode.component;

import com.ljscode.base.BaseMouseListener;
import com.ljscode.data.ItemData;
import com.ljscode.data.TestData;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * 数据树
 */
public class DataTree extends JTree {

    private BaseMouseListener<TestData> event;

    /**
     * 构造方法
     *
     * @param data 整个数据
     */
    public DataTree(TestData data, BaseMouseListener<ItemData> event) {
        super(createTreeNode(data));
        this.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.getLastSelectedPathComponent();

            if (node == null || node.isRoot()) {
                event.mouseClicked(null);
            } else {
                Object object = node.getUserObject();
                if (node.isLeaf()) {
                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
                    ItemData parentObj = (ItemData) parent.getUserObject();
                    event.mouseClicked(parentObj);
                } else {
                    event.mouseClicked((ItemData) object);
                }
            }

        });
    }

    /**
     * 构造方法
     *
     * @param left   距左
     * @param top    距顶
     * @param width  宽度
     * @param height 高度
     * @param data   整个数据
     */
    public DataTree(int left, int top, int width, int height, TestData data, BaseMouseListener<ItemData> event) {
        this(data, event);
        this.setBounds(left, top, width, height);
    }

    /**
     * 创建树
     *
     * @param data 整个数据
     * @return DefaultMutableTreeNode TreeNode
     */
    public static DefaultMutableTreeNode createTreeNode(TestData data) {
        if (data == null)
            return new DefaultMutableTreeNode("");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(data.getName());
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(data.getData1());
        node1.add(new DefaultMutableTreeNode(String.format("柱面数据(%s)", data.getData1().isCheckCylinder() ? "已测量" : "未测量")));
        node1.add(new DefaultMutableTreeNode(String.format("端面数据(%s)", data.getData1().isCheckEndFace() ? "已测量" : "未测量")));
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode(data.getData2());
        node2.add(new DefaultMutableTreeNode(String.format("柱面数据(%s)", data.getData2().isCheckCylinder() ? "已测量" : "未测量")));
        node2.add(new DefaultMutableTreeNode(String.format("端面数据(%s)", data.getData2().isCheckEndFace() ? "已测量" : "未测量")));
        DefaultMutableTreeNode node3 = new DefaultMutableTreeNode(data.getData3());
        node3.add(new DefaultMutableTreeNode(String.format("柱面数据(%s)", data.getData3().isCheckCylinder() ? "已测量" : "未测量")));
        node3.add(new DefaultMutableTreeNode(String.format("端面数据(%s)", data.getData3().isCheckEndFace() ? "已测量" : "未测量")));
        DefaultMutableTreeNode node4 = new DefaultMutableTreeNode(data.getData4());
        node4.add(new DefaultMutableTreeNode(String.format("柱面数据(%s)", data.getData4().isCheckCylinder() ? "已测量" : "未测量")));
        node4.add(new DefaultMutableTreeNode(String.format("端面数据(%s)", data.getData4().isCheckEndFace() ? "已测量" : "未测量")));
        DefaultMutableTreeNode node0 = new DefaultMutableTreeNode(data.getInsideData());
        node0.add(new DefaultMutableTreeNode(String.format("柱面数据(%s)", data.getInsideData().isCheckCylinder() ? "已测量" : "未测量")));
        node0.add(new DefaultMutableTreeNode(String.format("端面数据(%s)", data.getInsideData().isCheckEndFace() ? "已测量" : "未测量")));
        root.add(node1);
        root.add(node2);
        root.add(node3);
        root.add(node4);
        root.add(node0);
        return root;
    }

    /**
     * 设置数据
     *
     * @param data 整个数据
     */
    public void setTestData(TestData data) {
        DefaultMutableTreeNode root = createTreeNode(data);
        this.setModel(new DefaultTreeModel(root));
        if (event != null)
            event.mouseClicked(null);
    }

    public void blur(BaseMouseListener<TestData> event) {
        this.event = event;
    }

}
