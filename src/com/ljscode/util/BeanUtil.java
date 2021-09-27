package com.ljscode.util;

import com.ljscode.data.DataModel;
import com.ljscode.data.ItemModel;
import com.ljscode.data.ResultModel;

import java.util.ArrayList;
import java.util.Optional;

public abstract class BeanUtil {

    public static ItemModel GetCurrentItemModel(DataModel dataModel) {
        ArrayList<ItemModel> dataItems = dataModel.getDataItems();
        if (dataItems.size() > 0) {
            return dataItems.get(dataItems.size() - 1);
        } else {
            return null;
        }
    }

    public static DataModel GetLevel1Data(ResultModel resultModel) {
        Optional<DataModel> dataModelStream = resultModel.getData().stream().filter(t -> t.getDataIndex() == 1).findFirst();
        return dataModelStream.orElse(null);
    }

    public static ItemModel GetLevel1ItemData(ResultModel resultModel) {
        DataModel dataModel = GetLevel1Data(resultModel);
        if (dataModel != null) {
            return GetCurrentItemModel(dataModel);
        }
        return null;
    }

}
