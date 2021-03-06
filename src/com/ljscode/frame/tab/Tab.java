package com.ljscode.frame.tab;

import com.ljscode.base.BaseColor;
import com.ljscode.base.BaseConfig;
import com.ljscode.component.Div;
import com.ljscode.component.IconTextBtn;
import com.ljscode.data.ResultModel;
import com.ljscode.data.TestData;
import com.ljscode.data.UnitData;
import com.ljscode.frame.tab.tabbtn.*;
import com.ljscode.frame.tab.tabpanel.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tab
 */
public class Tab extends Div {

    private TabBtn[] tabButtons; // tab按钮数组
    private NewTabBtn newTabBtn; // 新建Tab
    private CylinderTabBtn cylinderTabBtn; // 柱面测量Tab
    private EndFaceTabBtn endFaceTabBtn; // 柱面测量Tab
    private SynthesizeTabBtn synthesizeTabBtn;

    private TabPanel[] tabPanels; // tab面板数组
    private NewTabPanel newTabPanel; // 新建Tab面板
    private OpenTabPanel openTabPanel; // 打开Tab面板
    private OutputTabPanel outputTabPanel; // 导出Tab面板
    private SettingTabPanel settingTabPanel; // 设置Tab面板
    private CheckTabPanel checkTabPanel; // 传感器校准Tab面板
    private CylinderTabPanel cylinderTabPanel; // 柱面测量Tab面板
    private EndFaceTabPanel endFaceTabPanel; // 端面测量Tab面板
    private SynthesizeTabPanel synthesizeTabPanel; // 综合数据Tab面板

    private TestData currentData; // 当前数据
    private List<UnitData> rawData;

    private ResultModel resultModel;

    /**
     * 构造方法
     */
    public Tab() {
        super(1, 80,
                BaseConfig.FrameWidth - 2, BaseConfig.FrameHeight - 81,
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
     *
     * @param body Tab面板
     */
    private void initTabBtnEvent(Div body) {
        this.newTabBtn = new NewTabBtn(this::onClickNewTab); // 新建Tab
        OpenTabBtn openTabBtn = new OpenTabBtn(this::onClickOpenTab); // 打开Tab
        OutputTabBtn outputTabBtn = new OutputTabBtn(this::onClickOutputTab); // 导出Tab
        SettingTabBtn settingTabBtn = new SettingTabBtn(this::onClickSettingTab); // 设置Tab
        CheckTabBtn checkTabBtn = new CheckTabBtn(this::onClickCheckTab); // 传感器校准Tab
        this.cylinderTabBtn = new CylinderTabBtn(this::onClickCylinderTab); // 柱面测量Tab
        this.endFaceTabBtn = new EndFaceTabBtn(this::onClickEndFaceTab); // 端面测量Tab
        this.synthesizeTabBtn = new SynthesizeTabBtn(this::onClickSynthesizeTab); // 综合数据Tab
        this.tabButtons = new TabBtn[]{newTabBtn, openTabBtn, outputTabBtn, settingTabBtn,
                endFaceTabBtn, checkTabBtn, synthesizeTabBtn}; // 装载Tab到数组
        for (TabBtn tabBtn : this.tabButtons) { // 将Tab添加到body中
            body.add(tabBtn);
        }

        rawData = new ArrayList<>();
        this.newTabPanel = new NewTabPanel(); // 新建Tab面板
        this.openTabPanel = new OpenTabPanel(); // 打开Tab面板
        this.outputTabPanel = new OutputTabPanel(); // 导出Tab面板
        this.settingTabPanel = new SettingTabPanel(); // 设置Tab面板
        this.checkTabPanel = new CheckTabPanel(); // 传感器校准Tab面板
        this.cylinderTabPanel = new CylinderTabPanel(rawData); // 柱面测量Tab面板
        this.endFaceTabPanel = new EndFaceTabPanel(); // 端面测量Tab面板
        this.synthesizeTabPanel = new SynthesizeTabPanel(); // 综合数据Tab面板
        this.tabPanels = new TabPanel[]{newTabPanel, openTabPanel, outputTabPanel, settingTabPanel,
                endFaceTabPanel,  checkTabPanel, synthesizeTabPanel}; // 装载Tab面板到数组
        for (TabPanel tabPanel : this.tabPanels) { // 将Tab添加到body中
            body.add(tabPanel);
        }

        onClickSettingTab(settingTabBtn); // 默认点击传感器校准
    }

    /**
     * 切换tab
     *
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
     *
     * @param tab 点击的tab
     */
    private void onClickNewTab(IconTextBtn tab) {
        changeTab(tab, newTabPanel);
        newTabPanel.setEvent(data -> {
            this.resultModel = data;
            onClickEndFaceTab(endFaceTabBtn);
        });
    }

    /**
     * 点击打开Tab事件
     *
     * @param tab 点击的tab
     */
    private void onClickOpenTab(IconTextBtn tab) {
        changeTab(tab, openTabPanel);
        openTabPanel.setEvent(data -> {
            this.resultModel = data;
            onClickSynthesizeTab(synthesizeTabBtn);
        });
    }

    /**
     * 点击导出Tab事件
     *
     * @param tab 点击的tab
     */
    private void onClickOutputTab(IconTextBtn tab) {
        changeTab(tab, outputTabPanel);
        outputTabPanel.setData(resultModel);
        outputTabPanel.setEvent(data -> {
            this.resultModel = data;
            onClickSynthesizeTab(synthesizeTabBtn);
        });
//        if (currentData == null) { // 需要新建
//            onClickNewTab(newTabBtn);
//        } else {
//            outputTabPanel.setData(currentData);
//        }
    }

    /**
     * 点击设置Tab事件
     *
     * @param tab 点击的tab
     */
    private void onClickSettingTab(IconTextBtn tab) {
        changeTab(tab, settingTabPanel);
    }

    /**
     * 点击传感器校准Tab事件
     *
     * @param tab 点击的tab
     */
    private void onClickCheckTab(IconTextBtn tab) {
        changeTab(tab, checkTabPanel);
        endFaceTabPanel.setRead(false);
        checkTabPanel.setRead(true);
    }

    /**
     * 点击柱面测量Tab事件
     *
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
     *
     * @param tab 点击的tab
     */
    private void onClickEndFaceTab(IconTextBtn tab) {
        changeTab(tab, endFaceTabPanel);
        if (resultModel == null) { // 需要新建
            onClickNewTab(newTabBtn);
            tab.removeActive();
            newTabBtn.setActiveTrue();
        } else {
            endFaceTabPanel.setData(resultModel);
            checkTabPanel.setRead(false);
            endFaceTabPanel.setRead(true);
        }
    }

    /**
     * 点击综合数据Tab事件
     *
     * @param tab 点击的tab
     */
    private void onClickSynthesizeTab(IconTextBtn tab) {
        changeTab(tab, synthesizeTabPanel);
        if (resultModel == null) { // 需要新建
            onClickNewTab(newTabBtn);
            tab.removeActive();
            newTabBtn.setActiveTrue();
        } else {
            synthesizeTabPanel.setData(resultModel);
        }
    }

}
