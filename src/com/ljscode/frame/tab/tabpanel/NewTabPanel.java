package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseMouseListener;
import com.ljscode.base.BaseOnlyInputNumber;
import com.ljscode.bean.MainDb;
import com.ljscode.bean.RadiusConfig;
import com.ljscode.bean.SearchConfig;
import com.ljscode.component.Btn;
import com.ljscode.component.InputGroup;
import com.ljscode.data.ResultModel;
import com.ljscode.data.TestData;
import com.ljscode.util.ConfigUtil;
import com.ljscode.util.DbUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 新建面板
 */
public class NewTabPanel extends TabPanel {

    private ResultModel data;
    private BaseMouseListener<ResultModel> event;

    public NewTabPanel() {
        super();
        int space = 300;
        InputGroup inputGroup = new InputGroup((this.width - BaseConfig.InputGroupWidthLg) / 2,
                (this.height - BaseConfig.InputHeight - space) / 2, BaseConfig.InputGroupWidthLg, BaseConfig.InputHeight,
                "数据名称：", "请输入数据名称...");
        this.add(inputGroup);
        InputGroup tfGroup = new InputGroup((this.width - BaseConfig.InputGroupWidthLg) / 2,
                (this.height - BaseConfig.InputHeight - space) / 2 + BaseConfig.InputGroupSpaceLg,
                BaseConfig.InputGroupWidthLg, BaseConfig.InputHeight,
                "测量台份：", "请输入测量台份...");
        this.add(tfGroup);
        InputGroup userGroup = new InputGroup((this.width - BaseConfig.InputGroupWidthLg) / 2,
                (this.height - BaseConfig.InputHeight - space) / 2 + BaseConfig.InputGroupSpaceLg * 2,
                BaseConfig.InputGroupWidthLg, BaseConfig.InputHeight,
                "操作人员：", "请输入操作人员...");
        this.add(userGroup);
        InputGroup times = new InputGroup((this.width - BaseConfig.InputGroupWidthLg) / 2,
                (this.height - BaseConfig.InputHeight - space) / 2 + BaseConfig.InputGroupSpaceLg * 3,
                BaseConfig.InputGroupWidthLg, BaseConfig.InputHeight,
                "装配次数：", "请输入第几次装配...");
        this.add(times);
        InputGroup inr = new InputGroup((this.width - BaseConfig.InputGroupWidthLg) / 2,
                (this.height - BaseConfig.InputHeight - space) / 2 + BaseConfig.InputGroupSpaceLg * 4,
                BaseConfig.InputGroupWidthLg, BaseConfig.InputHeight,
                "理论半径：", "请输入理论半径...");
        this.add(inr);
        inr.limit(new BaseOnlyInputNumber());
//        RadiusConfig radiusConfig = ConfigUtil.GetRadiusConfig();
//        inr.setValue(String.valueOf(radiusConfig.getInsideR()));
        Btn btn = new Btn((this.width - BaseConfig.InputGroupWidthLg) / 2, (this.height - BaseConfig.InputHeight) / 2 + BaseConfig.InputGroupSpaceLg * 3,
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

        ///////////
        // 设置默认值
        ///////////

        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = df.format(new Date());
        SearchConfig searchConfig = new SearchConfig();
        searchConfig.setDate(df1.format(new Date()));
        List<MainDb> mainDbs = DbUtil.FindMainBySearchConfig(searchConfig);
        int nowTimes = mainDbs.size() + 1;
        String title = String.format("%s第%d次测量数据", nowDate, nowTimes);
        inputGroup.setValue(title);
        times.setValue(Integer.toString(nowTimes));
        inr.setValue(Integer.toString(10));
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
