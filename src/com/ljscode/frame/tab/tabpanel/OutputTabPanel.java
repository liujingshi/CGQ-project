package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseMouseListener;
import com.ljscode.bean.OutputDataset;
import com.ljscode.component.Btn;
import com.ljscode.component.FileSelect;
import com.ljscode.data.ResultModel;
import com.ljscode.data.ResultModel;
import com.ljscode.util.OutputUtil;

/**
 * 导出面板
 */
public class OutputTabPanel extends TabPanel {

    private ResultModel data;
    private BaseMouseListener<ResultModel> event;

    public OutputTabPanel() {
        super();
        Btn open = new Btn(30, 30, 0, 0, "打开", Btn.BLUE, e -> {
            FileSelect fileSelect = new FileSelect("打开", "确定", ".test", "", this);
            String path = fileSelect.read();
            if (!path.equals("")) {
                OutputDataset outputDataset = OutputUtil.ReadOutputDataset(path);
                if (outputDataset != null) {
                    data = outputDataset.getResultModel();
                    event.mouseClicked(data);
                }
            }
        });
        Btn lcw = new Btn(30, 120, 0, 0, "保存", Btn.BLUE, e -> {
            FileSelect fileSelect = new FileSelect("另存为", "确定", ".test", data.getDataName(), this);
            String path = fileSelect.read();
            if (!path.equals("")) {
                OutputUtil.SaveOutputDataset(data, path);
            }
        });
        this.add(open);
        this.add(lcw);
    }

    public void setData(ResultModel data) {
        this.data = data;
    }

    public void setEvent(BaseMouseListener<ResultModel> event) {
        this.event = event;
    }
}
