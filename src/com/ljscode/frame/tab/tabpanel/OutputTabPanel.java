package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
import com.ljscode.component.TextLabel;

public class OutputTabPanel extends TabPanel {

    public OutputTabPanel() {
        super();
        TextLabel text = new TextLabel(0, 0, "导出", 30, BaseColor.Blue);
        this.add(text);
    }

}
