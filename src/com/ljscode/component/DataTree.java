package com.ljscode.component;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseMouseListener;
import com.ljscode.data.DataModel;
import com.ljscode.data.ItemData;
import com.ljscode.data.ItemModel;
import com.ljscode.data.ResultModel;
import com.ljscode.util.FontUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

/**
 * 数据树
 */
public class DataTree extends JTree {

    private BaseMouseListener<ResultModel> event;

    /**
     * 构造方法
     *
     * @param data 整个数据
     */
    public DataTree(ResultModel data, BaseMouseListener<ItemModel> event, BaseMouseListener<DataModel> event1) {
        super(createTreeNode(data));
        this.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.getLastSelectedPathComponent();

            if (node == null || node.isRoot()) {
                event.mouseClicked(null);
            } else {
                Object object = node.getUserObject();
                if (node.isLeaf()) {
                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
                    if (parent.isRoot()) {
                        event1.mouseClicked((DataModel) object);
                    } else {
                        event.mouseClicked((ItemModel) object);
                    }
                } else {
                    event1.mouseClicked((DataModel) object);
                }
            }
        });
        this.setFont(FontUtil.LoadFont(18));
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
    public DataTree(int left, int top, int width, int height, ResultModel data, BaseMouseListener<ItemModel> event, BaseMouseListener<DataModel> event1) {
        this(data, event, event1);
        this.setBounds(left, top, width, height);
    }

    /**
     * 创建树
     *
     * @param data 整个数据
     * @return DefaultMutableTreeNode TreeNode
     */
    public static DefaultMutableTreeNode createTreeNode(ResultModel data) {
        if (data == null)
            return new DefaultMutableTreeNode("");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(data);
        for (DataModel dataModel : data.getData()) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(dataModel);
            for (ItemModel itemModel : dataModel.getDataItems()) {
                DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(itemModel);
                node.add(subNode);
            }
            root.add(node);
        }
        return root;
    }

    /**
     * 设置数据
     *
     * @param data 整个数据
     */
    public void setResultModel(ResultModel data) {
        DefaultMutableTreeNode root = createTreeNode(data);
        this.setModel(new DefaultTreeModel(root));
        if (event != null)
            event.mouseClicked(null);
    }

    public void blur(BaseMouseListener<ResultModel> event) {
        this.event = event;
    }

}
