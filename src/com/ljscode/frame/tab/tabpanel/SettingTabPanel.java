package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseConfig;
import com.ljscode.bean.PortConfig;
import com.ljscode.component.Btn;
import com.ljscode.component.Select;
import com.ljscode.component.TextLabel;
import com.ljscode.util.ConfigUtil;

import java.util.List;

/**
 * 综合数据面板
 */
public class SettingTabPanel extends TabPanel {

    public SettingTabPanel() {
        super();
        List<PortConfig> portConfigs = ConfigUtil.GetPortConfig();
        PortConfig aPortConfig = null;
        PortConfig bPortConfig = null;
        PortConfig cPortConfig = null;
        PortConfig dPortConfig = null;
        for (PortConfig portConfig : portConfigs) {
            switch (portConfig.getName()) {
                case "A":
                    aPortConfig = portConfig;
                    break;
                case "B":
                    bPortConfig = portConfig;
                    break;
                case "C":
                    cPortConfig = portConfig;
                    break;
                case "D":
                    dPortConfig = portConfig;
                    break;
            }
        }
        int rootX = 50;
        int rootY = 50;
        String[] options = {"无", "角度传感器", "柱面传感器", "端面传感器"};
        TextLabel aLabel = new TextLabel(rootX, rootY, "A", 16, BaseColor.Black);
        this.add(aLabel);
        PortConfig finalAPortConfig = aPortConfig;
        Select aSelect = new Select(rootX + 20, rootY, 200, 20, options, index -> {
            switch (index) {
                case 0:
                    finalAPortConfig.setDevice("");
                    break;
                case 1:
                    finalAPortConfig.setDevice(BaseConfig.Deg);
                    break;
                case 2:
                    finalAPortConfig.setDevice(BaseConfig.Cylinder);
                    break;
                case 3:
                    finalAPortConfig.setDevice(BaseConfig.EndFace);
                    break;
            }
        });
        this.add(aSelect);
        TextLabel bLabel = new TextLabel(rootX, rootY + 40, "B", 16, BaseColor.Black);
        this.add(bLabel);
        PortConfig finalBPortConfig = bPortConfig;
        Select bSelect = new Select(rootX + 20, rootY + 40, 200, 20, options, index -> {
            switch (index) {
                case 0:
                    finalBPortConfig.setDevice("");
                    break;
                case 1:
                    finalBPortConfig.setDevice(BaseConfig.Deg);
                    break;
                case 2:
                    finalBPortConfig.setDevice(BaseConfig.Cylinder);
                    break;
                case 3:
                    finalBPortConfig.setDevice(BaseConfig.EndFace);
                    break;
            }
        });
        this.add(bSelect);
        TextLabel cLabel = new TextLabel(rootX, rootY + 80, "C", 16, BaseColor.Black);
        this.add(cLabel);
        PortConfig finalCPortConfig = cPortConfig;
        Select cSelect = new Select(rootX + 20, rootY + 80, 200, 20, options, index -> {
            switch (index) {
                case 0:
                    finalCPortConfig.setDevice("");
                    break;
                case 1:
                    finalCPortConfig.setDevice(BaseConfig.Deg);
                    break;
                case 2:
                    finalCPortConfig.setDevice(BaseConfig.Cylinder);
                    break;
                case 3:
                    finalCPortConfig.setDevice(BaseConfig.EndFace);
                    break;
            }
        });
        this.add(cSelect);
        TextLabel dLabel = new TextLabel(rootX, rootY + 120, "D", 16, BaseColor.Black);
        this.add(dLabel);
        PortConfig finalDPortConfig = dPortConfig;
        Select dSelect = new Select(rootX + 20, rootY + 120, 200, 20, options, index -> {
            switch (index) {
                case 0:
                    finalDPortConfig.setDevice("");
                    break;
                case 1:
                    finalDPortConfig.setDevice(BaseConfig.Deg);
                    break;
                case 2:
                    finalDPortConfig.setDevice(BaseConfig.Cylinder);
                    break;
                case 3:
                    finalDPortConfig.setDevice(BaseConfig.EndFace);
                    break;
            }
        });
        this.add(dSelect);
        switch (aPortConfig.getDevice()) {
            case "":
                aSelect.setSelectedIndex(0);
                break;
            case BaseConfig.Deg:
                aSelect.setSelectedIndex(1);
                break;
            case BaseConfig.Cylinder:
                aSelect.setSelectedIndex(2);
                break;
            case BaseConfig.EndFace:
                aSelect.setSelectedIndex(3);
                break;
        }
        switch (bPortConfig.getDevice()) {
            case "":
                bSelect.setSelectedIndex(0);
                break;
            case BaseConfig.Deg:
                bSelect.setSelectedIndex(1);
                break;
            case BaseConfig.Cylinder:
                bSelect.setSelectedIndex(2);
                break;
            case BaseConfig.EndFace:
                bSelect.setSelectedIndex(3);
                break;
        }
        switch (cPortConfig.getDevice()) {
            case "":
                cSelect.setSelectedIndex(0);
                break;
            case BaseConfig.Deg:
                cSelect.setSelectedIndex(1);
                break;
            case BaseConfig.Cylinder:
                cSelect.setSelectedIndex(2);
                break;
            case BaseConfig.EndFace:
                cSelect.setSelectedIndex(3);
                break;
        }
        switch (dPortConfig.getDevice()) {
            case "":
                dSelect.setSelectedIndex(0);
                break;
            case BaseConfig.Deg:
                dSelect.setSelectedIndex(1);
                break;
            case BaseConfig.Cylinder:
                dSelect.setSelectedIndex(2);
                break;
            case BaseConfig.EndFace:
                dSelect.setSelectedIndex(3);
                break;
        }
        TextLabel tip = new TextLabel(rootX, rootY + 220, "保存成功！", 16, BaseColor.Green);
        tip.setVisible(false);
        this.add(tip);
        Btn saveBtn = new Btn(rootX, rootY + 160, -1, -1, "保存", Btn.BLUE, e -> {
            tip.setVisible(true);
            ConfigUtil.SetPortConfig(portConfigs);
        });
        this.add(saveBtn);
    }

}
