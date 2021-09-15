package com.ljscode.component;

import com.ljscode.base.BaseMouseListener;
import com.ljscode.util.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Select extends JComboBox {

    public Select(String[] options, BaseMouseListener<Integer> event) {
        super(options);
        this.addItemListener(e -> event.mouseClicked(this.getSelectedIndex()));
        Font font = FontUtil.LoadFont(28);
        this.setFont(font);
    }

    public Select(int left, int top, int width, int height, String[] options, BaseMouseListener<Integer> event) {
        this(options, event);
        this.setBounds(left, top, width, height);
    }

}
