package com.ljscode.frame.tab.tabbtn;

import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseMouseListener;
import com.ljscode.component.IconTextBtn;

/**
 * 综合数据Tab按钮
 */
public class SynthesizeTabBtn extends TabBtn {

    /**
     * 构造方法
     * @param event 点击事件
     */
    public SynthesizeTabBtn(BaseMouseListener<IconTextBtn> event) {
        super(BaseConfig.FrameWidth - 100 - 5, 0, "综合数据",
                "res/img/icon/tab/synthesize/synthesize.png", "res/img/icon/tab/synthesize/synthesize-hover.png", event);
    }

}
