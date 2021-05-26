package com.ljscode.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljscode.bean.PortConfig;
import com.ljscode.bean.RadiusConfig;

import java.util.List;

public abstract class ConfigUtil {

    private static final String PortConfigPath = "config/port.config";
    private static final String RadiusConfigPath = "config/radius.config";

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

}
