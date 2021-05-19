package com.ljscode.frame.tab.tabbtn;

import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseMouseListener;
import com.ljscode.component.IconTextBtn;

/**
 * 柱面测量Tab按钮
 */
public class CylinderTabBtn extends TabBtn {

    /**
     * 构造方法
     * @param event 点击事件
     */
    public CylinderTabBtn(BaseMouseListener<IconTextBtn> event) {
        super(BaseConfig.FrameWidth - 300 - 5, 0, "柱面测量",
                "res/img/icon/tab/cylinder/cylinder.png", "res/img/icon/tab/cylinder/cylinder-hover.png", event);
    }

}
