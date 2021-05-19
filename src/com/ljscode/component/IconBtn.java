package com.ljscode.component;

import com.ljscode.base.BaseMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 图片按钮
 */
public class IconBtn extends JLabel {

    /**
     * 构造方法
     * @param left 距左
     * @param top 距顶
     * @param width 宽度
     * @param height 高度
     * @param src 正常图片路径
     * @param hoverSrc hover图片路径
     * @param event 点击事件
     */
    public IconBtn(int left, int top, int width, int height, String src, String hoverSrc, BaseMouseListener<IconBtn> event) {
        super();
        Image normalImage = Toolkit.getDefaultToolkit().getImage(src).getScaledInstance(width, height, Image.SCALE_DEFAULT);
        Image hoverImage = Toolkit.getDefaultToolkit().getImage(hoverSrc).getScaledInstance(width, height, Image.SCALE_DEFAULT);
        Icon normalIcon = new ImageIcon(normalImage);
        Icon hoverIcon = new ImageIcon(hoverImage);
        this.setIcon(normalIcon);
        this.setBounds(left, top, width, height);
        IconBtn that = this;
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                event.mouseClicked(that);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                that.setIcon(hoverIcon);
                that.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                that.setIcon(normalIcon);
                that.setCursor(Cursor.getDefaultCursor());
            }
        });
    }

}
