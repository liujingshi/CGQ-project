package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseUSBReader;
import com.ljscode.bean.ZeroConfig;
import com.ljscode.component.Btn;
import com.ljscode.component.CheckChart;
import com.ljscode.component.DataLabel;
import com.ljscode.data.ItemModel;
import com.ljscode.util.BeanUtil;
import com.ljscode.util.ConfigUtil;
import com.ljscode.util.DbUtil;

import javax.swing.*;
import java.util.Optional;

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
        int rootX = (this.width - 600) / 2;
        int rootY = (this.height - 500) / 2;
        isRead = false;
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
        Btn zeroBtn = new Btn(30, 150, 230, 60, "确认调零", Btn.BLUE, e -> {
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
