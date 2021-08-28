package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseUSBReader;
import com.ljscode.component.CheckChart;

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
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(200);
                    BaseUSBReader.ReadUSBData((deg, cylinder, endFace) -> {
                        checkChart.reload(cylinder, endFace, deg);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return checkChart;
    }
}
