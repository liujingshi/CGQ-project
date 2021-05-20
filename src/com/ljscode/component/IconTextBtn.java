package com.ljscode.component;

import com.ljscode.base.BaseMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 图片文字按钮
 */
public class IconTextBtn extends Div {

    private final Icon normalIcon; // 正常icon
    private final Icon hoverIcon; // hover icon
    private final Img icon; // 按钮中的图片
    private final TextLabel text; // 按钮中的文本
    private final Color background; // 正常背景颜色
    private final Color hoverBackground; // hover背景颜色
    private final Color color; // 正常文字颜色
    private final Color hoverColor; // hover文字颜色
    private boolean active = false; // 是否被按下

    /**
     * 构造方法
     *
     * @param left            距左
     * @param top             距顶
     * @param width           宽度
     * @param height          高度
     * @param iconWidth       图片宽度
     * @param iconHeight      图片高度
     * @param paddingTop      顶端内边距
     * @param paddingRight    右侧内边距
     * @param paddingBottom   底部内边距
     * @param paddingLeft     左侧内边距
     * @param text            文本
     * @param fontSize        文本字体大小
     * @param color           正常文本颜色
     * @param hoverColor      hover文本颜色
     * @param src             正常图片路径
     * @param hoverSrc        hover图片路径
     * @param background      正常背景颜色
     * @param hoverBackground hover背景颜色
     * @param event           点击事件
     */
    public IconTextBtn(int left, int top, int width, int height, int iconWidth, int iconHeight,
                       int paddingTop, int paddingRight, int paddingBottom, int paddingLeft,
                       String text, float fontSize, Color color, Color hoverColor,
                       String src, String hoverSrc,
                       Color background, Color hoverBackground,
                       BaseMouseListener<IconTextBtn> event) {
        super();
        this.setBounds(left, top, width, height); // 设置位置和宽高
        this.setBackground(background); // 设置背景颜色
        Image normalImage = Toolkit.getDefaultToolkit().getImage(src).getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT); // 读取图片
        Image hoverImage = Toolkit.getDefaultToolkit().getImage(hoverSrc).getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT); // 读取图片
        this.background = background;
        this.hoverBackground = hoverBackground;
        this.color = color;
        this.hoverColor = hoverColor;
        this.normalIcon = new ImageIcon(normalImage); // 图片转icon
        this.hoverIcon = new ImageIcon(hoverImage); // 图片转icon
        this.icon = new Img(paddingLeft, paddingTop, iconWidth, iconHeight, src); // 创建icon
        this.text = new TextLabel(0, paddingTop + iconHeight, width,
                height - paddingTop - paddingBottom - iconHeight, text, fontSize, color); // 创建文本
        this.text.setHorizontalAlignment(JLabel.CENTER); // 设置文字居中
        this.add(this.icon);
        this.add(this.text);
        IconTextBtn that = this;
        this.addMouseListener(new MouseListener() { // 鼠标事件监听
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
                that.setActive();
                that.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!that.active)
                    that.removeActive();
                that.setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    /**
     * 设置按钮active
     */
    public void setActive() {
        this.setBackground(hoverBackground);
        this.icon.setIcon(hoverIcon);
        this.text.setForeground(hoverColor);
    }

    /**
     * 移除按钮active
     */
    public void removeActive() {
        this.setBackground(background);
        this.icon.setIcon(normalIcon);
        this.text.setForeground(color);
        this.active = false;
    }

    /**
     * 设置active为true
     */
    public void setActiveTrue() {
        this.active = true;
    }

}
