package com.ljscode.frame.tab.tabbtn;

import com.ljscode.base.BaseMouseListener;
import com.ljscode.component.IconTextBtn;

/**
 * 新建Tab按钮
 */
public class NewTabBtn extends TabBtn {

    /**
     * 构造方法
     * @param event 点击事件
     */
    public NewTabBtn(BaseMouseListener<IconTextBtn> event) {
        super(0, 0, "新建", "res/img/icon/tab/new/new.png", "res/img/icon/tab/new/new-hover.png", event);
    }

}
