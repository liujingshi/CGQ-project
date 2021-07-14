package com.ljscode.frame.tab.tabbtn;

import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseMouseListener;
import com.ljscode.component.IconTextBtn;

/**
 * 端面测量Tab按钮
 */
public class EndFaceTabBtn extends TabBtn {

    /**
     * 构造方法
     *
     * @param event 点击事件
     */
    public EndFaceTabBtn(BaseMouseListener<IconTextBtn> event) {
        super(BaseConfig.FrameWidth - 200 - 5, 0, "数据测量",
                "res/img/icon/tab/end-face/end-face.png", "res/img/icon/tab/end-face/end-face-hover.png", event);
    }

}
