package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseConfig;
import com.ljscode.component.Btn;
import com.ljscode.component.InputGroup;
import com.ljscode.component.TextLabel;

/**
 * 打开面板
 */
public class OpenTabPanel extends TabPanel {

    public OpenTabPanel() {
        super();
        int rootX = 30;
        int rootY = 30;
        TextLabel textLabel = new TextLabel(rootX, rootY, "检测时间区间", 16, BaseColor.Black);
        this.add(textLabel);
        InputGroup startTime = new InputGroup(rootX, rootY + BaseConfig.InputGroupSpaceSm, BaseConfig.InputGroupWidth,
                BaseConfig.InputHeight, "开始时间：", "2021-05-09");
        this.add(startTime);
        InputGroup endTime = new InputGroup(rootX, rootY + BaseConfig.InputGroupSpaceSm * 2, BaseConfig.InputGroupWidth,
                BaseConfig.InputHeight, "结束时间：", "2021-05-16");
        this.add(endTime);
        InputGroup dataName = new InputGroup(rootX, rootY + BaseConfig.InputGroupSpaceSm * 2 + BaseConfig.InputGroupSpaceMd,
                BaseConfig.InputGroupWidth, BaseConfig.InputHeight, "数据名称：", "请输入数据名称...");
        this.add(dataName);
        Btn searchBtn = new Btn(rootX, rootY + BaseConfig.InputGroupSpaceSm * 3 + BaseConfig.InputGroupSpaceMd,
                0, 0, "查询", Btn.BLUE, e -> {
            String startTimeStr = startTime.getValue();
            String endTimeStr = endTime.getValue();
            String dataNameStr = dataName.getValue();
        });
        this.add(searchBtn);
    }

}
