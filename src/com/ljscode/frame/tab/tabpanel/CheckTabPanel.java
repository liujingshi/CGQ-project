package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseUSBReader;
import com.ljscode.component.CheckChart;
import com.ljscode.component.DataLabel;

import java.util.Optional;

/**
 * 校准传感器面板
 */
public class CheckTabPanel extends TabPanel {

    public CheckTabPanel() {
        super();
        int rootX = (this.width - 600) / 2;
        int rootY = (this.height - 500) / 2;
        CheckChart checkChart = createCheckBar(rootX, rootY);
        this.add(checkChart);
    }

    private CheckChart createCheckBar(int left, int top) {
        CheckChart checkChart = new CheckChart(left, top, 600, 500, 1.25, -2.36, 0.05);
        DataLabel degLabel = new DataLabel(30, 30, 16, "角度编码器", 0, 3, "°");
        this.add(degLabel);
        DataLabel cylinderLabel = new DataLabel(30, 70, 16, "柱面传感器", 0, 3, "μm");
        this.add(cylinderLabel);
        DataLabel endFaceLabel = new DataLabel(30, 110, 16, "端面传感器", 0, 3, "μm");
        this.add(endFaceLabel);
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(200);
                    BaseUSBReader.ReadUSBData((deg, cylinder, endFace) -> {
                        checkChart.reload(cylinder, endFace, deg);
                        degLabel.setData(deg);
                        cylinderLabel.setData(cylinder);
                        endFaceLabel.setData(endFace);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return checkChart;
    }
}
