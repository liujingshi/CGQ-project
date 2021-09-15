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
import com.ljscode.data.ResultModel;
import com.ljscode.data.TestData;
import com.ljscode.util.DatasetUtil;
import com.ljscode.util.DbUtil;

import java.util.List;

/**
 * 打开面板
 */
public class OpenTabPanel extends TabPanel {

    private Btn openBtn;
    private ResultModel selectTestData;
    private BaseMouseListener<ResultModel> event;

    public OpenTabPanel() {
        super();
        int rootX = 30;
        int rootY = 30;
        TextLabel textLabel = new TextLabel(rootX, rootY, "检测时间区间", 32, BaseColor.Black);
        this.add(textLabel);
        InputGroup startTime = new InputGroup(rootX, rootY + BaseConfig.InputGroupSpaceLg, BaseConfig.InputGroupWidth,
                BaseConfig.InputHeight, "开始时间：", "2021-05-09");
        this.add(startTime);
        DatePicker.datePicker(startTime.getInput());
        InputGroup endTime = new InputGroup(rootX, rootY + BaseConfig.InputGroupSpaceLg * 2, BaseConfig.InputGroupWidth,
                BaseConfig.InputHeight, "结束时间：", "2021-05-16");
        this.add(endTime);
        DatePicker.datePicker(endTime.getInput());
        InputGroup dataName = new InputGroup(rootX, rootY + BaseConfig.InputGroupSpaceLg * 3,
                BaseConfig.InputGroupWidth, BaseConfig.InputHeight, "数据名称：", "请输入数据名称...");
        this.add(dataName);
        ListView<ResultModel> list = new ListView<>(this.width - 16 - 400, 30, 400, 460, select -> {
            this.selectTestData = select;
            openBtn.unDisabled();
        });
        this.add(list);
        Btn searchBtn = new Btn(rootX, rootY + BaseConfig.InputGroupSpaceLg * 4,
                0, 0, "查询", Btn.BLUE, e -> {
            openBtn.disabled();
            String startTimeStr = startTime.getValue();
            String endTimeStr = endTime.getValue();
            String dataNameStr = dataName.getValue();
            SearchConfig searchConfig = new SearchConfig(dataNameStr, startTimeStr, endTimeStr);
            List<ResultModel> result = DbUtil.FindBySearchConfig(searchConfig);
            list.setListViewData(result);
            openBtn.disabled();
        });
        this.add(searchBtn);
        this.openBtn = new Btn(this.width - 16 - 400, 500, 0, 0, "打开", Btn.BLUE, e -> {
            event.mouseClicked(selectTestData);
        });
        this.add(openBtn);
        openBtn.disabled();
    }

    public void setEvent(BaseMouseListener<ResultModel> event) {
        this.event = event;
    }

}
