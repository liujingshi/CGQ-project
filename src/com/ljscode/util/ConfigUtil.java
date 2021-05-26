package com.ljscode.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljscode.bean.PortConfig;
import java.util.List;

public abstract class ConfigUtil {

    private static final String PortConfigPath = "config/port.config";

    public static List<PortConfig> GetPortConfig() {
        String json = FileUtil.ReadFile(PortConfigPath);
        return JSON.parseArray(json, PortConfig.class);
    }

    public static void SetPortConfig(List<PortConfig> obj) {
        String json = JSONObject.toJSONString(obj);
        FileUtil.WriteFile(PortConfigPath, json);
    }

}
