package com.ljscode.frame.tab.tabpanel;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseConfig;
import com.ljscode.base.BaseUSBListener;
import com.ljscode.component.*;
import com.ljscode.data.ItemData;
import com.ljscode.data.TestData;
import com.ljscode.data.UnitData;
import com.ljscode.util.DatasetUtil;

import java.util.List;

/**
 * 柱面检测面板
 */
public class CylinderTabPanel extends TabPanel {

    private final TextLabel currentDataNameLabel;
    private final List<UnitData> rawData;
//    private final DataTree tree;
    private final DataLabel degLabel;
    private Btn cNewBtn;
    private TestData data;
    private ItemData selectData;

    public CylinderTabPanel(List<UnitData> rawData) {
        super();
        this.rawData = rawData;
        int rootX = 30;
        int rootY = 30;
        currentDataNameLabel = new TextLabel(rootX, rootY, "2021-05-09日测量数据", 16, BaseColor.Black);
        this.add(currentDataNameLabel);
//        this.tree = new DataTree(rootX, rootY + 50, 180, 500, data, itemData -> {
//            this.selectData = itemData;
//            this.cNewBtn.unDisabled();
//        });
//        this.add(tree);
//        tree.blur(e -> {
//            cNewBtn.disabled();
//        });
//        this.cNewBtn = new Btn(rootX + 200, rootY + 160, 230, 60, "保存数据", Btn.BLUE, e -> {
//            if (selectData != null) {
//                selectData.setData(rawData);
//                selectData.setCheckCylinder(true);
//                tree.setTestData(data);
//                selectData = null;
//                DatasetUtil.SaveOrUpdate(data);
//            }
//        });
//        this.add(cNewBtn);
//        cNewBtn.disabled();
        Btn left1 = new Btn(rootX + 200, rootY + 230, 80, 80, "旋钮1逆时针旋转", Btn.GREEN, e -> {
            BaseUSBListener.RotateCylinder(1, false);
        });
        Btn left2 = new Btn(rootX + 290, rootY + 230, 80, 80, "旋钮2逆时针旋转", Btn.GREEN, e -> {
            BaseUSBListener.RotateCylinder(2, false);
        });
        Btn right1 = new Btn(rootX + 200, rootY + 320, 80, 80, "旋钮1顺时针旋转", Btn.GREEN, e -> {
            BaseUSBListener.RotateCylinder(1, true);
        });
        Btn right2 = new Btn(rootX + 290, rootY + 320, 80, 80, "旋钮2顺时针旋转", Btn.GREEN, e -> {
            BaseUSBListener.RotateCylinder(2, true);
        });
        this.add(left1);
        this.add(left2);
        this.add(right1);
        this.add(right2);
        this.degLabel = new DataLabel(rootX + 200, rootY + 450, 24, "角度", 36, 0, "°");
        this.add(degLabel);
    }




    public void changeData() {
        currentDataNameLabel.setText(data.getName());
//        tree.setTestData(data);
    }

    public TestData getData() {
        return data;
    }

    public void setData(TestData data) {
        this.data = data;
        changeData();
    }

    @Override
    public void showMe() {
        super.showMe();
        cNewBtn.disabled();
    }
}
