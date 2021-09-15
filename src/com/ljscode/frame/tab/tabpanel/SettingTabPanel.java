package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.*;
import com.ljscode.bean.ComConfig;
import com.ljscode.bean.PortConfig;
import com.ljscode.bean.RangeConfig;
import com.ljscode.bean.UsbConfig;
import com.ljscode.component.Btn;
import com.ljscode.component.InputGroup;
import com.ljscode.component.Select;
import com.ljscode.component.TextLabel;
import com.ljscode.util.ConfigUtil;

import java.util.List;

/**
 * 综合数据面板
 */
public class SettingTabPanel extends TabPanel {

    private UsbConfig usbConfig;
    private RangeConfig rangeConfig;
    private BaseMouseListener<String> event;
    private final int rootX;
    private final int rootY;

    public SettingTabPanel() {
        super();
        rootX = 50;
        rootY = 50;
        setPortConfig();
        setRangeConfig();
        TextLabel tip = new TextLabel(rootX, rootY + 220, 340, 17, "保存成功！", 16, BaseColor.Green);
        tip.setVisible(false);
        this.add(tip);
        Btn saveBtn = new Btn(rootX, this.getHeight() - 60, -1, -1, "保存", Btn.BLUE, e -> {
            tip.setText("保存成功！");
            tip.setForeground(BaseColor.Green);
            tip.setVisible(true);
            ConfigUtil.SetUsbConfig(usbConfig);
            event.mouseClicked("");
            ConfigUtil.SetRangeConfig(rangeConfig);
        });
        this.add(saveBtn);
        Btn linkBtn = new Btn(rootX + 200, this.getHeight() - 60, -1, -1, "连接", Btn.BLUE, e -> {
            tip.setText("连接中......");
            tip.setForeground(BaseColor.Blue);
            tip.setVisible(true);
            new Thread(() -> {
                if (BaseUSBReader.Link(false)) {
                    tip.setText("连接成功！");
                    tip.setForeground(BaseColor.Green);
                    tip.setVisible(true);
                } else {
                    tip.setText("连接失败，请检查设置以及设备连接情况！");
                    tip.setForeground(BaseColor.Red);
                    tip.setVisible(true);
                }
            }).start();
        });
        this.add(linkBtn);
    }

    public void setPortConfig() {
        usbConfig = ConfigUtil.GetUsbConfig();
        String[] portOptions = {"请选择...", "A", "B", "C", "D"};
        String[] usbOptions = {"请选择...", "USB1", "USB2", "USB3", "USB4", "USB5", "USB6"};
        TextLabel cylinderLabel = new TextLabel(rootX, rootY, "柱面传感器", 16, BaseColor.Black);
        this.add(cylinderLabel);
        TextLabel endFaceLabel = new TextLabel(rootX, rootY + BaseConfig.InputGroupSpaceMd, "端面传感器", 16, BaseColor.Black);
        this.add(endFaceLabel);
        TextLabel bpxLabel = new TextLabel(rootX, rootY + BaseConfig.InputGroupSpaceMd * 2, "BPX黑盒", 16, BaseColor.Black);
        this.add(bpxLabel);
        TextLabel degLabel = new TextLabel(rootX, rootY + BaseConfig.InputGroupSpaceMd * 3, "角度编码器", 16, BaseColor.Black);
        this.add(degLabel);
        Select cylinderSelect = new Select(rootX + 120, rootY, 200, 20, portOptions, index -> {
            switch (index) {
                case 0:
                    usbConfig.setCylinder("");
                    break;
                case 1:
                    usbConfig.setCylinder("A");
                    break;
                case 2:
                    usbConfig.setCylinder("B");
                    break;
                case 3:
                    usbConfig.setCylinder("C");
                    break;
                case 4:
                    usbConfig.setCylinder("D");
                    break;
            }
        });
        this.add(cylinderSelect);
        Select endFaceSelect = new Select(rootX + 120, rootY + BaseConfig.InputGroupSpaceMd, 200, 20, portOptions, index -> {
            switch (index) {
                case 0:
                    usbConfig.setEndFace("");
                    break;
                case 1:
                    usbConfig.setEndFace("A");
                    break;
                case 2:
                    usbConfig.setEndFace("B");
                    break;
                case 3:
                    usbConfig.setEndFace("C");
                    break;
                case 4:
                    usbConfig.setEndFace("D");
                    break;
            }
        });
        this.add(endFaceSelect);
        Select bpxSelect = new Select(rootX + 120, rootY + BaseConfig.InputGroupSpaceMd * 2, 200, 20, usbOptions, index -> {
            switch (index) {
                case 0:
                    usbConfig.setBpx("");
                    break;
                case 1:
                    usbConfig.setBpx("USB1");
                    break;
                case 2:
                    usbConfig.setBpx("USB2");
                    break;
                case 3:
                    usbConfig.setBpx("USB3");
                    break;
                case 4:
                    usbConfig.setBpx("USB4");
                    break;
                case 5:
                    usbConfig.setBpx("USB5");
                    break;
                case 6:
                    usbConfig.setBpx("USB6");
                    break;
            }
        });
        this.add(bpxSelect);
        Select degSelect = new Select(rootX + 120, rootY + BaseConfig.InputGroupSpaceMd * 3, 200, 20, usbOptions, index -> {
            switch (index) {
                case 0:
                    usbConfig.setDeg("");
                    break;
                case 1:
                    usbConfig.setDeg("USB1");
                    break;
                case 2:
                    usbConfig.setDeg("USB2");
                    break;
                case 3:
                    usbConfig.setDeg("USB3");
                    break;
                case 4:
                    usbConfig.setDeg("USB4");
                    break;
                case 5:
                    usbConfig.setDeg("USB5");
                    break;
                case 6:
                    usbConfig.setDeg("USB6");
                    break;
            }
        });
        this.add(degSelect);
        switch (usbConfig.getCylinder()) {
            case "":
                cylinderSelect.setSelectedIndex(0);
                break;
            case "A":
                cylinderSelect.setSelectedIndex(1);
                break;
            case "B":
                cylinderSelect.setSelectedIndex(2);
                break;
            case "C":
                cylinderSelect.setSelectedIndex(3);
                break;
            case "D":
                cylinderSelect.setSelectedIndex(4);
                break;
        }
        switch (usbConfig.getEndFace()) {
            case "":
                endFaceSelect.setSelectedIndex(0);
                break;
            case "A":
                endFaceSelect.setSelectedIndex(1);
                break;
            case "B":
                endFaceSelect.setSelectedIndex(2);
                break;
            case "C":
                endFaceSelect.setSelectedIndex(3);
                break;
            case "D":
                endFaceSelect.setSelectedIndex(4);
                break;
        }
        switch (usbConfig.getBpx()) {
            case "":
                bpxSelect.setSelectedIndex(0);
                break;
            case "USB1":
                bpxSelect.setSelectedIndex(1);
                break;
            case "USB2":
                bpxSelect.setSelectedIndex(2);
                break;
            case "USB3":
                bpxSelect.setSelectedIndex(3);
                break;
            case "USB4":
                bpxSelect.setSelectedIndex(4);
                break;
            case "USB5":
                bpxSelect.setSelectedIndex(5);
                break;
            case "USB6":
                bpxSelect.setSelectedIndex(6);
                break;
        }
        switch (usbConfig.getDeg()) {
            case "":
                degSelect.setSelectedIndex(0);
                break;
            case "USB1":
                degSelect.setSelectedIndex(1);
                break;
            case "USB2":
                degSelect.setSelectedIndex(2);
                break;
            case "USB3":
                degSelect.setSelectedIndex(3);
                break;
            case "USB4":
                degSelect.setSelectedIndex(4);
                break;
            case "USB5":
                degSelect.setSelectedIndex(5);
                break;
            case "USB6":
                degSelect.setSelectedIndex(6);
                break;
        }
    }

    public void setRangeConfig() {
        rangeConfig = ConfigUtil.GetRangeConfig();
        InputGroup cylinderInputStart = new InputGroup(rootX + 400, rootY,
                BaseConfig.InputGroupWidth, BaseConfig.InputHeight,
                "柱面理论期间（微米）：", "请输入...");
        cylinderInputStart.limit(new BaseOnlyInputNumber());
        cylinderInputStart.setValue(Double.toString(rangeConfig.getCylinderStart()));
        this.add(cylinderInputStart);
        InputGroup cylinderInputEnd = new InputGroup(rootX + 820, rootY,
                BaseConfig.InputGroupWidthSm, BaseConfig.InputHeight,
                "~", "请输入...");
        cylinderInputEnd.limit(new BaseOnlyInputNumber());
        cylinderInputEnd.setValue(Double.toString(rangeConfig.getCylinderEnd()));
        this.add(cylinderInputEnd);
        InputGroup endFaceInputStart = new InputGroup(rootX + 400, rootY + BaseConfig.InputGroupSpaceMd,
                BaseConfig.InputGroupWidth, BaseConfig.InputHeight,
                "端面理论期间（微米）：", "请输入...");
        endFaceInputStart.limit(new BaseOnlyInputNumber());
        endFaceInputStart.setValue(Double.toString(rangeConfig.getEndFaceStart()));
        this.add(endFaceInputStart);
        InputGroup endFaceInputEnd = new InputGroup(rootX + 820, rootY + BaseConfig.InputGroupSpaceMd,
                BaseConfig.InputGroupWidthSm, BaseConfig.InputHeight,
                "~", "请输入...");
        endFaceInputEnd.limit(new BaseOnlyInputNumber());
        endFaceInputEnd.setValue(Double.toString(rangeConfig.getEndFaceEnd()));
        this.add(endFaceInputEnd);
        InputGroup roundnessInputStart = new InputGroup(rootX + 400, rootY + BaseConfig.InputGroupSpaceMd * 2,
                BaseConfig.InputGroupWidth, BaseConfig.InputHeight,
                "圆度理论期间（微米）：", "请输入...");
        roundnessInputStart.limit(new BaseOnlyInputNumber());
        roundnessInputStart.setValue(Double.toString(rangeConfig.getRoundnessStart()));
        this.add(roundnessInputStart);
        InputGroup roundnessInputEnd = new InputGroup(rootX + 820, rootY + BaseConfig.InputGroupSpaceMd * 2,
                BaseConfig.InputGroupWidthSm, BaseConfig.InputHeight,
                "~", "请输入...");
        roundnessInputEnd.limit(new BaseOnlyInputNumber());
        roundnessInputEnd.setValue(Double.toString(rangeConfig.getRoundnessEnd()));
        this.add(roundnessInputEnd);
        InputGroup flatnessInputStart = new InputGroup(rootX + 400, rootY + BaseConfig.InputGroupSpaceMd * 3,
                BaseConfig.InputGroupWidth, BaseConfig.InputHeight,
                "平面度理论期间（微米）：", "请输入...");
        flatnessInputStart.limit(new BaseOnlyInputNumber());
        flatnessInputStart.setValue(Double.toString(rangeConfig.getFlatnessStart()));
        this.add(flatnessInputStart);
        InputGroup flatnessInputEnd = new InputGroup(rootX + 820, rootY + BaseConfig.InputGroupSpaceMd * 3,
                BaseConfig.InputGroupWidthSm, BaseConfig.InputHeight,
                "~", "请输入...");
        flatnessInputEnd.limit(new BaseOnlyInputNumber());
        flatnessInputEnd.setValue(Double.toString(rangeConfig.getFlatnessEnd()));
        this.add(flatnessInputEnd);
        InputGroup axisFromInputStart = new InputGroup(rootX + 400, rootY + BaseConfig.InputGroupSpaceMd * 4,
                BaseConfig.InputGroupWidth, BaseConfig.InputHeight,
                "同轴度理论期间（微米）：", "请输入...");
        axisFromInputStart.limit(new BaseOnlyInputNumber());
        axisFromInputStart.setValue(Double.toString(rangeConfig.getAxisFromStart()));
        this.add(axisFromInputStart);
        InputGroup axisFromInputEnd = new InputGroup(rootX + 820, rootY + BaseConfig.InputGroupSpaceMd * 4,
                BaseConfig.InputGroupWidthSm, BaseConfig.InputHeight,
                "~", "请输入...");
        axisFromInputEnd.limit(new BaseOnlyInputNumber());
        axisFromInputEnd.setValue(Double.toString(rangeConfig.getAxisFromEnd()));
        this.add(axisFromInputEnd);
        InputGroup parallelismInputStart = new InputGroup(rootX + 400, rootY + BaseConfig.InputGroupSpaceMd * 5,
                BaseConfig.InputGroupWidth, BaseConfig.InputHeight,
                "平行度理论期间（微米）：", "请输入...");
        parallelismInputStart.limit(new BaseOnlyInputNumber());
        parallelismInputStart.setValue(Double.toString(rangeConfig.getParallelismStart()));
        this.add(parallelismInputStart);
        InputGroup parallelismInputEnd = new InputGroup(rootX + 820, rootY + BaseConfig.InputGroupSpaceMd * 5,
                BaseConfig.InputGroupWidthSm, BaseConfig.InputHeight,
                "~", "请输入...");
        parallelismInputEnd.limit(new BaseOnlyInputNumber());
        parallelismInputEnd.setValue(Double.toString(rangeConfig.getParallelismEnd()));
        this.add(parallelismInputEnd);
        event = that -> {
            rangeConfig.setCylinderStart(Double.parseDouble(cylinderInputStart.getValue()));
            rangeConfig.setCylinderEnd(Double.parseDouble(cylinderInputEnd.getValue()));
            rangeConfig.setEndFaceStart(Double.parseDouble(endFaceInputStart.getValue()));
            rangeConfig.setEndFaceEnd(Double.parseDouble(endFaceInputEnd.getValue()));
            rangeConfig.setRoundnessStart(Double.parseDouble(roundnessInputStart.getValue()));
            rangeConfig.setRoundnessEnd(Double.parseDouble(roundnessInputEnd.getValue()));
            rangeConfig.setFlatnessStart(Double.parseDouble(flatnessInputStart.getValue()));
            rangeConfig.setFlatnessEnd(Double.parseDouble(flatnessInputEnd.getValue()));
            rangeConfig.setAxisFromStart(Double.parseDouble(axisFromInputStart.getValue()));
            rangeConfig.setAxisFromEnd(Double.parseDouble(axisFromInputEnd.getValue()));
            rangeConfig.setParallelismStart(Double.parseDouble(parallelismInputStart.getValue()));
            rangeConfig.setParallelismEnd(Double.parseDouble(parallelismInputEnd.getValue()));
        };
    }

}
