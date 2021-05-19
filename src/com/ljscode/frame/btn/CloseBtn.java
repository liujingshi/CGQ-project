package com.ljscode.frame.btn;

import com.ljscode.base.BaseConfig;
import com.ljscode.component.IconBtn;
import com.ljscode.frame.MainFrame;

/**
 * 关闭按钮
 */
public class CloseBtn extends IconBtn {

    /**
     * 构造方法
     * @param mainFrame 主窗体对象
     */
    public CloseBtn(MainFrame mainFrame) {
        super(BaseConfig.FrameWidth-40, 10, 30, 30,
                "res/img/icon/close/close.png", "res/img/icon/close/close-hover.png",
                that -> mainFrame.close());

    }

}
