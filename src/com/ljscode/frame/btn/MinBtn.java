package com.ljscode.frame.btn;

import com.ljscode.base.BaseConfig;
import com.ljscode.component.IconBtn;
import com.ljscode.frame.MainFrame;

/**
 * 关闭按钮
 */
public class MinBtn extends IconBtn {

    /**
     * 构造方法
     *
     * @param mainFrame 主窗体对象
     */
    public MinBtn(MainFrame mainFrame) {
        super(BaseConfig.FrameWidth - 90, 10, 30, 30,
                "res/img/icon/min/min.png", "res/img/icon/min/min-hover.png",
                that -> mainFrame.min());

    }

}
