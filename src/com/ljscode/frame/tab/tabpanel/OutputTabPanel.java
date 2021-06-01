package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseMouseListener;
import com.ljscode.bean.OutputDataset;
import com.ljscode.component.Btn;
import com.ljscode.component.FileSelect;
import com.ljscode.data.TestData;
import com.ljscode.util.OutputUtil;

/**
 * 导出面板
 */
public class OutputTabPanel extends TabPanel {

    private TestData data;
    private BaseMouseListener<TestData> event;

    public OutputTabPanel() {
        super();
        Btn open = new Btn(30, 30, 0, 0, "打开", Btn.BLUE, e -> {
            FileSelect fileSelect = new FileSelect("打开", "确定", ".test", "", this);
            String path = fileSelect.read();
            if (!path.equals("")) {
                OutputDataset outputDataset = OutputUtil.ReadOutputDataset(path);
                if (outputDataset != null) {
                    data = outputDataset.getTestData();
                    event.mouseClicked(data);
                }
            }
        });
        Btn lcw = new Btn(30, 100, 0, 0, "保存", Btn.BLUE, e -> {
            FileSelect fileSelect = new FileSelect("另存为", "确定", ".test", data.getName(), this);
            String path = fileSelect.read();
            if (!path.equals("")) {
                OutputUtil.SaveOutputDataset(data, path);
            }
        });
        this.add(open);
        this.add(lcw);
    }

    public void setData(TestData data) {
        this.data = data;
    }

    public void setEvent(BaseMouseListener<TestData> event) {
        this.event = event;
    }
}
