package com.ljscode.frame.tab.tabbtn;

import com.ljscode.base.BaseMouseListener;
import com.ljscode.component.IconTextBtn;

/**
 * 导出Tab按钮
 */
public class OutputTabBtn extends TabBtn {

    /**
     * 构造方法
     *
     * @param event 点击事件
     */
    public OutputTabBtn(BaseMouseListener<IconTextBtn> event) {
        super(200, 0, "导出", "res/img/icon/tab/output/output.png", "res/img/icon/tab/output/output-hover.png", event);
    }

}
