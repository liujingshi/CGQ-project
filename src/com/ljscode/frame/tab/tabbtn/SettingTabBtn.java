package com.ljscode.frame.tab.tabbtn;

import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseMouseListener;
import com.ljscode.component.IconTextBtn;

/**
 * 综合数据Tab按钮
 */
public class SettingTabBtn extends TabBtn {

    /**
     * 构造方法
     *
     * @param event 点击事件
     */
    public SettingTabBtn(BaseMouseListener<IconTextBtn> event) {
        super(300, 0, "设置",
                "res/img/icon/tab/setting/setting.png", "res/img/icon/tab/setting/setting-hover.png", event);
    }

}
