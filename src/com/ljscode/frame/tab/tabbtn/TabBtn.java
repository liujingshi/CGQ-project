package com.ljscode.frame.tab.tabbtn;

import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseMouseListener;
import com.ljscode.component.IconTextBtn;

/**
 * Tab按钮
 */
public class TabBtn extends IconTextBtn {

    /**
     * 构造方法
     * @param left 距左
     * @param top 距顶
     * @param text 文本
     * @param src 正常图片路径
     * @param hoverSrc hover图片路径
     * @param event 点击事件
     */
    public TabBtn(int left, int top, String text, String src, String hoverSrc, BaseMouseListener<IconTextBtn> event) {
        super(left, top, BaseConfig.TabBtnWidth, BaseConfig.TabBtnHeight, BaseConfig.TabBtnIconWidth, BaseConfig.TabBtnIconHeight,
                BaseConfig.TabBtnPadding[0], BaseConfig.TabBtnPadding[1], BaseConfig.TabBtnPadding[2], BaseConfig.TabBtnPadding[3],
                text, BaseConfig.TabBtnFontSize, BaseConfig.TabBtnColor, BaseConfig.TabBtnHoverColor,
                src, hoverSrc,
                BaseConfig.TabBtnBackground, BaseConfig.TabBtnHoverBackground,
                event);
    }

}
