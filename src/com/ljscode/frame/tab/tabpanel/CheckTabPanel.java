package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseUSBReader;
import com.ljscode.bean.ZeroConfig;
import com.ljscode.component.Btn;
import com.ljscode.component.CheckChart;
import com.ljscode.component.DataLabel;
import com.ljscode.util.ConfigUtil;

import javax.swing.*;

/**
 * 校准传感器面板
 */
public class CheckTabPanel extends TabPanel {

    private boolean isRead;
    private double degV;
    private double cylinderV;
    private double endFaceV;

    public CheckTabPanel() {
        super();
        int rootX = (this.width - 1000) / 2;
        int rootY = (this.height - 800) / 2;
        isRead = false;
        CheckChart checkChart = createCheckBar(rootX, rootY);
        this.add(checkChart);
    }

    private CheckChart createCheckBar(int left, int top) {
        CheckChart checkChart = new CheckChart(left, top, 1000, 800, 0, 0, 0);
        DataLabel cylinderLabel = new DataLabel(30, 30, 32, "柱面传感器", 0, 3, "μm");
        this.add(cylinderLabel);
        DataLabel endFaceLabel = new DataLabel(30, 100, 32, "端面传感器", 0, 3, "μm");
        this.add(endFaceLabel);
        DataLabel degLabel = new DataLabel(30, 170, 32, "角度编码器", 0, 3, "°");
        this.add(degLabel);
        Btn zeroBtn = new Btn(30, 240, 230, 60, "确认调零", Btn.BLUE, e -> {
            ZeroConfig zeroConfig = ConfigUtil.GetZeroConfig();
            zeroConfig.setCylinder(cylinderV);
            zeroConfig.setEndFace(endFaceV);
            ConfigUtil.SetZeroConfig(zeroConfig);
            JOptionPane.showMessageDialog(null, "调零成功，请开始测量！", "", JOptionPane.INFORMATION_MESSAGE);
        });
        this.add(zeroBtn);
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5);
                    if (isRead) {
                        BaseUSBReader.ReadUSBData((deg, cylinder, endFace) -> {
                            degV = deg;
                            cylinderV = cylinder;
                            endFaceV = endFace;
                            checkChart.reload(cylinder, endFace, deg);
                            degLabel.setData(deg);
                            cylinderLabel.setData(cylinder);
                            endFaceLabel.setData(endFace);
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return checkChart;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
