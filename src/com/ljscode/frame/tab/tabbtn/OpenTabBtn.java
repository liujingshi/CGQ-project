package com.ljscode.frame.tab.tabbtn;

import com.ljscode.base.BaseMouseListener;
import com.ljscode.component.IconTextBtn;

/**
 * 打开Tab按钮
 */
public class OpenTabBtn extends TabBtn {

    /**
     * 构造方法
     * @param event 点击事件
     */
    public OpenTabBtn(BaseMouseListener<IconTextBtn> event) {
        super(100, 0, "打开", "res/img/icon/tab/open/open.png", "res/img/icon/tab/open/open-hover.png", event);
    }

}
