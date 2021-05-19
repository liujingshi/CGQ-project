package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseMouseListener;
import com.ljscode.component.Btn;
import com.ljscode.component.InputGroup;
import com.ljscode.data.TestData;

import java.util.Date;

public class NewTabPanel extends TabPanel {

    private TestData data;
    private BaseMouseListener<TestData> event;

    public NewTabPanel() {
        super();
        InputGroup inputGroup = new InputGroup((int)((this.width - BaseConfig.InputGroupWidth) / 2),
                (int)((this.height - BaseConfig.InputHeight - 100) / 2), BaseConfig.InputGroupWidth, BaseConfig.InputHeight,
                "数据名称：", "请输入数据名称...");
        this.add(inputGroup);
        Btn btn = new Btn((int)((this.width - BaseConfig.InputGroupWidth) / 2), (int)((this.height - BaseConfig.InputHeight) / 2),
                0, 0, "新建", Btn.BLUE, e -> {
            String value = inputGroup.getValue();
            this.data = new TestData(value, new Date());
            this.event.mouseClicked(this.data); // 回调方法
        });
        this.add(btn);
    }

    public TestData getData() {
        return data;
    }

    public void setData(TestData data) {
        this.data = data;
    }

    public BaseMouseListener<TestData> getEvent() {
        return event;
    }

    public void setEvent(BaseMouseListener<TestData> event) {
        this.event = event;
    }
}
