package com.ljscode.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljscode.bean.*;

import java.util.List;

public abstract class ConfigUtil {

    private static final String PortConfigPath = "config/port.config";
    private static final String RadiusConfigPath = "config/radius.config";
    private static final String ComConfigPath = "config/com.config";
    private static final String UsbConfigPath = "config/usb.config";
    private static final String RangeConfigPath = "config/range.config";

    public static List<PortConfig> GetPortConfig() {
        String json = FileUtil.ReadFile(PortConfigPath);
        return JSON.parseArray(json, PortConfig.class);
    }

    public static void SetPortConfig(List<PortConfig> obj) {
        String json = JSONObject.toJSONString(obj);
        FileUtil.WriteFile(PortConfigPath, json);
    }

    public static RadiusConfig GetRadiusConfig() {
        String json = FileUtil.ReadFile(RadiusConfigPath);
        return JSON.parseObject(json, RadiusConfig.class);
    }

    public static void SetRadiusConfig(RadiusConfig obj) {
        String json = JSONObject.toJSONString(obj);
        FileUtil.WriteFile(RadiusConfigPath, json);
    }

    public static List<ComConfig> GetComConfig() {
        String json = FileUtil.ReadFile(ComConfigPath);
        return JSON.parseArray(json, ComConfig.class);
    }

    public static void SetComConfig(List<ComConfig> obj) {
        String json = JSONObject.toJSONString(obj);
        FileUtil.WriteFile(ComConfigPath, json);
    }

    public static UsbConfig GetUsbConfig() {
        String json = FileUtil.ReadFile(UsbConfigPath);
        return JSON.parseObject(json, UsbConfig.class);
    }

    public static void SetUsbConfig(UsbConfig obj) {
        String json = JSONObject.toJSONString(obj);
        FileUtil.WriteFile(UsbConfigPath, json);
    }

    public static RangeConfig GetRangeConfig() {
        String json = FileUtil.ReadFile(RangeConfigPath);
        return JSON.parseObject(json, RangeConfig.class);
    }

    public static void SetRangeConfig(RangeConfig obj) {
        String json = JSONObject.toJSONString(obj);
        FileUtil.WriteFile(RangeConfigPath, json);
    }
}
