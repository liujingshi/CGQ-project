package com.ljscode.util;

import com.ljscode.data.DataModel;
import com.ljscode.data.ItemModel;

import java.util.ArrayList;

public abstract class BeanUtil {

    public static ItemModel GetCurrentItemModel(DataModel dataModel) {
        ArrayList<ItemModel> dataItems = dataModel.getDataItems();
        if (dataItems.size() > 0) {
            return dataItems.get(dataItems.size() - 1);
        } else {
            return null;
        }
    }

}
