package com.ljscode.frame.tab.tabbtn;

import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseMouseListener;
import com.ljscode.component.IconTextBtn;

/**
 * 传感器校准Tab按钮
 */
public class CheckTabBtn extends TabBtn {

    /**
     * 构造方法
     * @param event 点击事件
     */
    public CheckTabBtn(BaseMouseListener<IconTextBtn> event) {
        super(BaseConfig.FrameWidth - 400 - 5, 0, "传感器校准",
                "res/img/icon/tab/check/check.png", "res/img/icon/tab/check/check-hover.png", event);
    }

}
