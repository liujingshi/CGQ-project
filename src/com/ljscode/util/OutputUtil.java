package com.ljscode.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljscode.bean.MainData;
import com.ljscode.bean.OutputDataset;
import com.ljscode.data.ResultModel;

public abstract class OutputUtil {

    public static OutputDataset ReadOutputDataset(String path) {
        String json = FileUtil.ReadFile(path);
        return JSON.parseObject(json, OutputDataset.class);
    }

    public static void SaveOutputDataset(ResultModel testData, String path) {
        MainData mainData = DatasetUtil.GetMainById(testData.getDataId());
        OutputDataset outputDataset = new OutputDataset(mainData, testData);
        String json = JSONObject.toJSONString(outputDataset);
        FileUtil.WriteFile(path, json);
    }

}
