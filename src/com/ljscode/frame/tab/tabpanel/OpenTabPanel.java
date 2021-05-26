package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseMouseListener;
import com.ljscode.bean.SearchConfig;
import com.ljscode.component.Btn;
import com.ljscode.component.InputGroup;
import com.ljscode.component.ListView;
import com.ljscode.component.TextLabel;
import com.datepicker.DatePicker;
import com.ljscode.data.TestData;
import com.ljscode.util.DatasetUtil;

import java.util.List;

/**
 * 打开面板
 */
public class OpenTabPanel extends TabPanel {

    private TestData selectTestData;
    private BaseMouseListener<TestData> event;

    public OpenTabPanel() {
        super();
        int rootX = 30;
        int rootY = 30;
        TextLabel textLabel = new TextLabel(rootX, rootY, "检测时间区间", 16, BaseColor.Black);
        this.add(textLabel);
        InputGroup startTime = new InputGroup(rootX, rootY + BaseConfig.InputGroupSpaceSm, BaseConfig.InputGroupWidth,
                BaseConfig.InputHeight, "开始时间：", "2021-05-09");
        this.add(startTime);
        DatePicker.datePicker(startTime.getInput());
        InputGroup endTime = new InputGroup(rootX, rootY + BaseConfig.InputGroupSpaceSm * 2, BaseConfig.InputGroupWidth,
                BaseConfig.InputHeight, "结束时间：", "2021-05-16");
        this.add(endTime);
        DatePicker.datePicker(endTime.getInput());
        InputGroup dataName = new InputGroup(rootX, rootY + BaseConfig.InputGroupSpaceSm * 2 + BaseConfig.InputGroupSpaceMd,
                BaseConfig.InputGroupWidth, BaseConfig.InputHeight, "数据名称：", "请输入数据名称...");
        this.add(dataName);
        ListView<TestData> list = new ListView<>(600, 30, 400, 460, select -> {
            this.selectTestData = select;
        });
        this.add(list);
        Btn searchBtn = new Btn(rootX, rootY + BaseConfig.InputGroupSpaceSm * 3 + BaseConfig.InputGroupSpaceMd,
                0, 0, "查询", Btn.BLUE, e -> {
            String startTimeStr = startTime.getValue();
            String endTimeStr = endTime.getValue();
            String dataNameStr = dataName.getValue();
            SearchConfig searchConfig = new SearchConfig(dataNameStr, startTimeStr, endTimeStr);
            List<TestData> result = DatasetUtil.FindBySearchConfig(searchConfig);
            list.setListViewData(result);
        });
        this.add(searchBtn);
        Btn openBtn = new Btn(600, 500, 0, 0, "打开", Btn.BLUE, e -> {
            event.mouseClicked(selectTestData);
        });
        this.add(openBtn);
    }

    public void setEvent(BaseMouseListener<TestData> event) {
        this.event = event;
    }

}
