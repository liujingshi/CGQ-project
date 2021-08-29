package com.ljscode.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ljscode.bean.MainDb;
import com.ljscode.bean.SearchConfig;
import com.ljscode.data.ResultModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 数据库类
 */
public abstract class DbUtil {

    private static final String mainPath = "dataset/main.db"; // 数据库主文件
    private static final String dataPath = "dataset/db/"; // 每条数据所在文件路径
    private static final String datasetSuffix = "db"; // 数据文件后缀

    /**
     * 保存或更新
     *
     * @param entity 数据
     */
    public static void SaveOrUpdate(ResultModel entity) {
        List<MainDb> mainDb = GetMainAll();
        MainDb row = null;
        for (MainDb mainData : mainDb) {
            if (mainData.getDataId().equals(entity.getDataId())) {
                row = mainData;
                break;
            }
        }
        String filePath = String.format("%s%s.%s", dataPath, entity.getDataId(), datasetSuffix);
        if (row == null) { // 新增
            MainDb newMainDb = new MainDb(entity.getDataId(), entity.getDataName(), entity.getCreateTime(), entity.getMeasuringStand(), entity.getOperator(), entity.getSurveyTimes());
            mainDb.add(newMainDb);
        } else { // 修改
            row.setDataName(entity.getDataName());
            row.setCreateTime(entity.getCreateTime());
        }
        String dataJson = JSONObject.toJSONString(entity);
        FileUtil.WriteFile(filePath, dataJson);
        String mainDbJson = JSONObject.toJSONString(mainDb);
        FileUtil.WriteFile(mainPath, mainDbJson);
    }

    /**
     * 删除数据
     *
     * @param id 数据id
     */
    public static void Delete(String id) {
        List<MainDb> mainDb = GetMainAll();
        for (MainDb mainData : mainDb) {
            if (mainData.getDataId().equals(id)) {
                mainDb.remove(mainData);
                String filePath = String.format("%s%s.%s", dataPath, id, datasetSuffix);
                FileUtil.DeleteFile(filePath);
                break;
            }
        }
    }

    /**
     * 删除数据
     *
     * @param entity 数据
     */
    public static void Delete(ResultModel entity) {
        Delete(entity.getDataId());
    }

    /**
     * 获取全部索引
     *
     * @return 全部索引
     */
    public static List<MainDb> GetMainAll() {
        String datasetJson = FileUtil.ReadFile(mainPath);
        return JSON.parseArray(datasetJson, MainDb.class);
    }

    /**
     * 通过ids获取索引
     *
     * @param ids ids
     * @return 索引列表
     */
    public static List<MainDb> GetMainByIds(List<String> ids) {
        List<MainDb> mainDb = GetMainAll();
        mainDb.removeIf(mainData -> !ids.contains(mainData.getDataId()));
        return mainDb;
    }

    /**
     * 通过id找索引
     *
     * @param id id
     * @return 索引 找不到为null
     */
    public static MainDb GetMainById(String id) {
        List<MainDb> mainDb = GetMainAll();
        MainDb result = null;
        for (MainDb mainData : mainDb) {
            if (mainData.getDataId().equals(id)) {
                result = mainData;
                break;
            }
        }
        return result;
    }

    /**
     * 通过索引列表获取数据列表
     *
     * @param mainDb 索引列表
     * @return 数据列表
     */
    public static List<ResultModel> GetByMainDbset(List<MainDb> mainDb) {
        List<ResultModel> testDataset = new ArrayList<>();
        for (MainDb mainData : mainDb) {
            String filePath = String.format("%s%s.%s", dataPath, mainData.getDataId(), datasetSuffix);
            String testDataJson = FileUtil.ReadFile(filePath);
            testDataset.add(JSON.parseObject(testDataJson, ResultModel.class));
//            JSONObject obj = JSONObject.parseObject(testDataJson);
//            JSONArray arr = obj.getJSONArray("data");
//            String js = JSON.toJSONString(arr, SerializerFeature.WriteClassName);
//            testDataset.add(JSON.parseObject(js, ResultModel.class));
        }
        return testDataset;
    }

    /**
     * 获取全部数据
     *
     * @return 全部数据
     */
    public static List<ResultModel> GetAll() {
        List<MainDb> mainDb = GetMainAll();
        return GetByMainDbset(mainDb);
    }

    /**
     * 通过ids获取数据
     *
     * @param ids ids
     * @return 数据列表
     */
    public static List<ResultModel> GetByIds(List<String> ids) {
        List<MainDb> mainDb = GetMainByIds(ids);
        return GetByMainDbset(mainDb);
    }

    /**
     * 通过id获取数据
     *
     * @param id id
     * @return 数据
     */
    public static ResultModel GetById(String id) {
        MainDb mainData = GetMainById(id);
        if (mainData == null)
            return null;
        String filePath = String.format("%s%s.%s", dataPath, mainData.getDataId(), datasetSuffix);
        String testDataJson = FileUtil.ReadFile(filePath);
        return JSON.parseObject(testDataJson, ResultModel.class);
    }

    /**
     * 已经存在一个相同的id
     *
     * @param id id
     * @return 是否存在
     */
    public static boolean IsExistId(String id) {
        MainDb mainData = GetMainById(id);
        return !(mainData == null);
    }

    /**
     * 生成一个新的不重复的Id
     *
     * @return 新的Id
     */
    public static String CreateNewId() {
        String newId = UUID.randomUUID().toString();
        while (IsExistId(newId)) {
            newId = UUID.randomUUID().toString();
        }
        return newId;
    }

    /**
     * 通过搜索配置进行查询
     *
     * @param searchConfig 搜索配置
     * @return 数据列表
     */
    public static List<ResultModel> FindBySearchConfig(SearchConfig searchConfig) {
        List<MainDb> mainDb = GetMainAll();
        mainDb.removeIf(mainData -> {
            boolean result = !mainData.getDataName().contains(searchConfig.getName());
            String startTimeStr = searchConfig.getStartTimeStr();
            if (!startTimeStr.equals("")) {
                Date startTime = DateUtil.ToDate(startTimeStr);
                if (startTime != null) {
                    if (DateUtil.LessThan(mainData.getCreateTime(), startTime)) {
                        result = true;
                    }
                }
            }
            String endTimeStr = searchConfig.getEndTimeStr();
            if (!endTimeStr.equals("")) {
                Date endTime = DateUtil.ToDate(endTimeStr);
                if (endTime != null) {
                    if (DateUtil.GreaterThan(mainData.getCreateTime(), endTime)) {
                        result = true;
                    }
                }
            }
            return result;
        });
        return GetByMainDbset(mainDb);
    }
}
