package com.ljscode.frame.tab.tabpanel;

import com.ljscode.component.CheckChart;

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
                    double cylinder = Math.random() * 1 + 1;
                    double endFace = Math.random() * 1 - 2;
                    double deg = Math.random() * 2 - 1;
                    checkChart.reload(cylinder, endFace, deg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return checkChart;
    }
}
