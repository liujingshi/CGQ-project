package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseMouseListener;
import com.ljscode.base.BaseOnlyInputNumber;
import com.ljscode.bean.RadiusConfig;
import com.ljscode.component.Btn;
import com.ljscode.component.InputGroup;
import com.ljscode.data.ResultModel;
import com.ljscode.data.TestData;
import com.ljscode.util.ConfigUtil;

/**
 * 新建面板
 */
public class NewTabPanel extends TabPanel {

    private ResultModel data;
    private BaseMouseListener<ResultModel> event;

    public NewTabPanel() {
        super();
        InputGroup inputGroup = new InputGroup((this.width - BaseConfig.InputGroupWidth) / 2,
                (this.height - BaseConfig.InputHeight - 240) / 2, BaseConfig.InputGroupWidth, BaseConfig.InputHeight,
                "数据名称：", "请输入数据名称...");
        this.add(inputGroup);
        InputGroup tfGroup = new InputGroup((this.width - BaseConfig.InputGroupWidth) / 2,
                (this.height - BaseConfig.InputHeight - 240) / 2 + BaseConfig.InputGroupSpaceMd,
                BaseConfig.InputGroupWidth, BaseConfig.InputHeight,
                "测量台份：", "请输入测量台份...");
        this.add(tfGroup);
        InputGroup userGroup = new InputGroup((this.width - BaseConfig.InputGroupWidth) / 2,
                (this.height - BaseConfig.InputHeight - 240) / 2 + BaseConfig.InputGroupSpaceMd * 2,
                BaseConfig.InputGroupWidth, BaseConfig.InputHeight,
                "操作人员：", "请输入操作人员...");
        this.add(userGroup);
        InputGroup times = new InputGroup((this.width - BaseConfig.InputGroupWidth) / 2,
                (this.height - BaseConfig.InputHeight - 240) / 2 + BaseConfig.InputGroupSpaceMd * 3,
                BaseConfig.InputGroupWidth, BaseConfig.InputHeight,
                "装配次数：", "请输入第几次装配...");
        this.add(times);
        InputGroup inr = new InputGroup((this.width - BaseConfig.InputGroupWidth) / 2,
                (this.height - BaseConfig.InputHeight - 240) / 2 + BaseConfig.InputGroupSpaceMd * 4,
                BaseConfig.InputGroupWidth, BaseConfig.InputHeight,
                "理论半径：", "请输入理论半径...");
        this.add(inr);
        inr.limit(new BaseOnlyInputNumber());
//        RadiusConfig radiusConfig = ConfigUtil.GetRadiusConfig();
//        inr.setValue(String.valueOf(radiusConfig.getInsideR()));
        Btn btn = new Btn((this.width - BaseConfig.InputGroupWidth) / 2, (this.height - BaseConfig.InputHeight) / 2 + BaseConfig.InputGroupSpaceMd * 3,
                0, 0, "新建", Btn.BLUE, e -> {
            String value = inputGroup.getValue();
            String rfStr = tfGroup.getValue();
            String user = userGroup.getValue();
            String csStr = times.getValue();
            String rtr = inr.getValue();
            this.data = new ResultModel();
            this.data.setDataName(value);
            this.data.setMeasuringStand(rfStr);
            this.data.setOperator(user);
            this.data.setSurveyTimes(Integer.parseInt(csStr));
            this.data.setTheoryRadius(Double.parseDouble(rtr));
            this.event.mouseClicked(this.data); // 回调方法
//            ConfigUtil.SetRadiusConfig(radiusConfig);
        });
        this.add(btn);
    }

    public ResultModel getData() {
        return data;
    }

    public void setData(ResultModel data) {
        this.data = data;
    }

    public BaseMouseListener<ResultModel> getEvent() {
        return event;
    }

    public void setEvent(BaseMouseListener<ResultModel> event) {
        this.event = event;
    }
}
