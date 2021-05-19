package com.ljscode.frame.tab;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseConfig;
import com.ljscode.component.Div;
import com.ljscode.component.IconTextBtn;
import com.ljscode.data.TestData;
import com.ljscode.frame.tab.tabbtn.*;
import com.ljscode.frame.tab.tabpanel.*;

/**
 * Tab
 */
public class Tab extends Div {

    private TabBtn[] tabButtons; // tab按钮数组
    private NewTabBtn newTabBtn; // 新建Tab
    private CylinderTabBtn cylinderTabBtn; // 柱面测量Tab

    private TabPanel[] tabPanels; // tab面板数组
    private NewTabPanel newTabPanel; // 新建Tab面板
    private OpenTabPanel openTabPanel; // 打开Tab面板
    private OutputTabPanel outputTabPanel; // 导出Tab面板
    private CheckTabPanel checkTabPanel; // 传感器校准Tab面板
    private CylinderTabPanel cylinderTabPanel; // 柱面测量Tab面板
    private EndFaceTabPanel endFaceTabPanel; // 端面测量Tab面板
    private SynthesizeTabPanel synthesizeTabPanel; // 综合数据Tab面板

    private TestData currentData; // 当前数据
    /**
     * 构造方法
     */
    public Tab() {
        super(1, BaseConfig.ScaleHeight(0.104),
                BaseConfig.FrameWidth - 2, BaseConfig.FrameHeight - BaseConfig.ScaleHeight(0.104) - 1,
                BaseColor.Gray);
        setTab();
    }

    /**
     * 设置Tab
     */
    private void setTab() {
        Div body = new Div(4, 4, this.getWidth() - 8, this.getHeight() - 8, BaseColor.Write); // 创建Tab面板
        this.add(body);
        initTabBtnEvent(body);
    }

    /**
     * 初始化Tab按钮事件
     * @param body Tab面板
     */
    private void initTabBtnEvent(Div body) {
        this.newTabBtn = new NewTabBtn(this::onClickNewTab); // 新建Tab
        OpenTabBtn openTabBtn = new OpenTabBtn(this::onClickOpenTab); // 打开Tab
        OutputTabBtn outputTabBtn = new OutputTabBtn(this::onClickOutputTab); // 导出Tab
        CheckTabBtn checkTabBtn = new CheckTabBtn(this::onClickCheckTab); // 传感器校准Tab
        this.cylinderTabBtn = new CylinderTabBtn(this::onClickCylinderTab); // 柱面测量Tab
        EndFaceTabBtn endFaceTabBtn = new EndFaceTabBtn(this::onClickEndFaceTab); // 端面测量Tab
        SynthesizeTabBtn synthesizeTabBtn = new SynthesizeTabBtn(this::onClickSynthesizeTab); // 综合数据Tab
        this.tabButtons = new TabBtn[] {newTabBtn, openTabBtn, outputTabBtn,
                endFaceTabBtn, cylinderTabBtn, checkTabBtn, synthesizeTabBtn}; // 装载Tab到数组
        for (TabBtn tabBtn : this.tabButtons) { // 将Tab添加到body中
            body.add(tabBtn);
        }

        this.newTabPanel = new NewTabPanel(); // 新建Tab面板
        this.openTabPanel = new OpenTabPanel(); // 打开Tab面板
        this.outputTabPanel = new OutputTabPanel(); // 导出Tab面板
        this.checkTabPanel = new CheckTabPanel(); // 传感器校准Tab面板
        this.cylinderTabPanel = new CylinderTabPanel(); // 柱面测量Tab面板
        this.endFaceTabPanel = new EndFaceTabPanel(); // 端面测量Tab面板
        this.synthesizeTabPanel = new SynthesizeTabPanel(); // 综合数据Tab面板
        this.tabPanels = new TabPanel[] { newTabPanel, openTabPanel, outputTabPanel,
                endFaceTabPanel, cylinderTabPanel, checkTabPanel, synthesizeTabPanel }; // 装载Tab面板到数组
        for (TabPanel tabPanel : this.tabPanels) { // 将Tab添加到body中
            body.add(tabPanel);
        }

        onClickCheckTab(checkTabBtn); // 默认点击传感器校准
    }

    /**
     * 切换tab
     * @param tab 要切换到的tab
     */
    private void changeTab(IconTextBtn tab, TabPanel tp) {
        for (TabBtn tabBtn : this.tabButtons) {
            tabBtn.removeActive();
        }
        tab.setActive();
        tab.setActiveTrue();
        for (TabPanel tabPanel : this.tabPanels) {
            tabPanel.hideMe();
        }
        tp.showMe();
    }

    /**
     * 点击新建Tab事件
     * @param tab 点击的tab
     */
    private void onClickNewTab(IconTextBtn tab) {
        changeTab(tab, newTabPanel);
        newTabPanel.setEvent(data -> {
            this.currentData = data;
            onClickCylinderTab(cylinderTabBtn);
        });
    }

    /**
     * 点击打开Tab事件
     * @param tab 点击的tab
     */
    private void onClickOpenTab(IconTextBtn tab) {
        changeTab(tab, openTabPanel);
    }

    /**
     * 点击导出Tab事件
     * @param tab 点击的tab
     */
    private void onClickOutputTab(IconTextBtn tab) {
        changeTab(tab, outputTabPanel);
    }

    /**
     * 点击传感器校准Tab事件
     * @param tab 点击的tab
     */
    private void onClickCheckTab(IconTextBtn tab) {
        changeTab(tab, checkTabPanel);
    }

    /**
     * 点击柱面测量Tab事件
     * @param tab 点击的tab
     */
    private void onClickCylinderTab(IconTextBtn tab) {
        changeTab(tab, cylinderTabPanel);
        if (currentData == null) { // 需要新建
            onClickNewTab(newTabBtn);
        } else {
            cylinderTabPanel.setData(currentData);
        }
    }

    /**
     * 点击端面测量Tab事件
     * @param tab 点击的tab
     */
    private void onClickEndFaceTab(IconTextBtn tab) {
        changeTab(tab, endFaceTabPanel);
        if (currentData == null) { // 需要新建
            onClickNewTab(newTabBtn);
            tab.removeActive();
            newTabBtn.setActiveTrue();
        } else {
            endFaceTabPanel.setData(currentData);
        }
    }

    /**
     * 点击综合数据Tab事件
     * @param tab 点击的tab
     */
    private void onClickSynthesizeTab(IconTextBtn tab) {
        changeTab(tab, synthesizeTabPanel);
    }

}
