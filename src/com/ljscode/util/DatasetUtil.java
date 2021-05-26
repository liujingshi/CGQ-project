package com.ljscode.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljscode.bean.MainData;
import com.ljscode.data.TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 数据库类
 */
public abstract class DatasetUtil {

    private static final String mainPath = "dataset/main.dataset"; // 数据库主文件
    private static final String dataPath = "dataset/data/"; // 每条数据所在文件路径
    private static final String datasetSuffix = "dataset"; // 数据文件后缀

    /**
     * 保存或更新
     * @param entity 数据
     */
    public static void SaveOrUpdate(TestData entity) {
        List<MainData> mainDataset = GetMainAll();
        MainData row = null;
        for (MainData mainData : mainDataset) {
            if (mainData.getId().equals(entity.getId())) {
                row = mainData;
                break;
            }
        }
        String filePath = String.format("%s%s.%s", dataPath, entity.getId(), datasetSuffix);
        if (row == null) { // 新增
            MainData newMainData = new MainData(entity.getId(), entity.getName(), entity.getTime());
            mainDataset.add(newMainData);
        } else { // 修改
            row.setName(entity.getName());
            row.setTime(entity.getTime());
        }
        String dataJson = JSONObject.toJSONString(entity);
        FileUtil.WriteFile(filePath, dataJson);
        String mainDatasetJson = JSONObject.toJSONString(mainDataset);
        FileUtil.WriteFile(mainPath, mainDatasetJson);
    }

    /**
     * 删除数据
     * @param id 数据id
     */
    public static void Delete(String id) {
        List<MainData> mainDataset = GetMainAll();
        for (MainData mainData : mainDataset) {
            if (mainData.getId().equals(id)) {
                mainDataset.remove(mainData);
                String filePath = String.format("%s%s.%s", dataPath, id, datasetSuffix);
                FileUtil.DeleteFile(filePath);
                break;
            }
        }
    }

    /**
     * 删除数据
     * @param entity 数据
     */
    public static void Delete(TestData entity) {
        List<MainData> mainDataset = GetMainAll();
        Delete(entity.getId());
    }

    /**
     * 获取全部索引
     *
     * @return 全部索引
     */
    public static List<MainData> GetMainAll() {
        String datasetJson = FileUtil.ReadFile(mainPath);
        return JSON.parseArray(datasetJson, MainData.class);
    }

    /**
     * 通过ids获取索引
     *
     * @param ids ids
     * @return 索引列表
     */
    public static List<MainData> GetMainByIds(List<String> ids) {
        List<MainData> mainDataset = GetMainAll();
        mainDataset.removeIf(mainData -> !ids.contains(mainData.getId()));
        return mainDataset;
    }

    /**
     * 通过id找索引
     *
     * @param id id
     * @return 索引 找不到为null
     */
    public static MainData GetMainById(String id) {
        List<MainData> mainDataset = GetMainAll();
        MainData result = null;
        for (MainData mainData : mainDataset) {
            if (mainData.getId().equals(id)) {
                result = mainData;
                break;
            }
        }
        return result;
    }

    /**
     * 通过索引列表获取数据列表
     *
     * @param mainDataset 索引列表
     * @return 数据列表
     */
    public static List<TestData> GetByMainDataset(List<MainData> mainDataset) {
        List<TestData> testDataset = new ArrayList<>();
        for (MainData mainData : mainDataset) {
            String filePath = String.format("%s%s.%s", dataPath, mainData.getId(), datasetSuffix);
            String testDataJson = FileUtil.ReadFile(filePath);
            testDataset.add(JSON.parseObject(testDataJson, TestData.class));
        }
        return testDataset;
    }

    /**
     * 获取全部数据
     *
     * @return 全部数据
     */
    public static List<TestData> GetAll() {
        List<MainData> mainDataset = GetMainAll();
        return GetByMainDataset(mainDataset);
    }

    /**
     * 通过ids获取数据
     *
     * @param ids ids
     * @return 数据列表
     */
    public static List<TestData> GetByIds(List<String> ids) {
        List<MainData> mainDataset = GetMainByIds(ids);
        return GetByMainDataset(mainDataset);
    }

    /**
     * 通过id获取数据
     *
     * @param id id
     * @return 数据
     */
    public static TestData GetById(String id) {
        MainData mainData = GetMainById(id);
        if (mainData == null)
            return null;
        String filePath = String.format("%s%s.%s", dataPath, mainData.getId(), datasetSuffix);
        String testDataJson = FileUtil.ReadFile(filePath);
        return JSON.parseObject(testDataJson, TestData.class);
    }

    /**
     * 已经存在一个相同的id
     *
     * @param id id
     * @return 是否存在
     */
    public static boolean IsExistId(String id) {
        MainData mainData = GetMainById(id);
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
}
