package com.ljscode.component;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseMouseListener;
import com.ljscode.util.FontUtil;

import javax.swing.*;
import java.util.List;

public class ListView<T> extends JList<T> {

    public ListView(BaseMouseListener<T> event) {
        super();
        this.setBackground(BaseColor.Gray);
        listen(event);
    }

    public ListView(int left, int top, int width, int height, BaseMouseListener<T> event) {
        this(event);
        this.setBounds(left, top, width, height);
    }

    public ListView(List<T> list, BaseMouseListener<T> event) {
        super((T[]) list.toArray());
        this.setBackground(BaseColor.Gray);
        listen(event);
    }

    public ListView(int left, int top, int width, int height, List<T> list, BaseMouseListener<T> event) {
        this(list, event);
        this.setBounds(left, top, width, height);
    }

    public void setListViewData(List<T> list) {
        this.setListData((T[]) list.toArray());
    }

    public void listen(BaseMouseListener<T> event) {
        this.addListSelectionListener(e -> {
            event.mouseClicked(this.getSelectedValue());
        });
        this.setFont(FontUtil.LoadFont(14));
    }
}
