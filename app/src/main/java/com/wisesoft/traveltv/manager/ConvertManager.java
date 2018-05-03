package com.wisesoft.traveltv.manager;

import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.model.FilterItemModel;
import com.wisesoft.traveltv.model.ItemTypeModel;
import com.wisesoft.traveltv.model.SpannableItemModel;
import com.wisesoft.traveltv.model.temp.InitDataBean;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.ui.change.HomeTab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by picher on 2018/4/28.
 * Describe：
 */

public class ConvertManager {

    private static ConvertManager mInstance;

    private ConvertManager() {
    }

    public static ConvertManager getInstance() {
        if (mInstance == null) {
            synchronized (ConvertManager.class) {
                mInstance = new ConvertManager();
            }
        }
        return mInstance;
    }

    //轉化器
    public List<SpannableItemModel> convertItemToSpannable(List<ItemInfoBean> itemInfoBeans, String type) {
        List<SpannableItemModel> itemModels = new ArrayList<>();
        SpannableItemModel spannableItemModel;
        int rowSpan = 1, colSpan = 1;
        for (int i = 0; i < itemInfoBeans.size(); i++) {
            spannableItemModel = new SpannableItemModel(itemInfoBeans.get(i));
            //根據類型不同設置不同位置條目的寬高占比
            switch (type) {
                case Constans.HOT_PAGE_TYPE_WEEK:
                    if (i == 0) {
                        rowSpan = 2;
                        colSpan = 2;
                    } else if (i == 1 || i == 2) {
                        rowSpan = 1;
                        colSpan = 2;
                    } else {
                        rowSpan = 1;
                        colSpan = 1;
                    }
                    break;
                case Constans.HOT_PAGE_TYPE_TODAY:
                    rowSpan = i == 0 ? 1 : 1;
                    colSpan = i == 0 ? 1 : 1;
                    break;
                case Constans.HOT_PAGE_TYPE_PLAY:
                case Constans.HOT_PAGE_TYPE_EAT:
                case Constans.HOT_PAGE_TYPE_STAY:
                case Constans.HOT_PAGE_TYPE_PAY:
                case Constans.HOT_PAGE_TYPE_FUN:
                    rowSpan = i == 0 ? 1 : 1;
                    colSpan = i == 0 ? 2 : 1;
                    break;
            }
            spannableItemModel.setColSpan(colSpan);
            spannableItemModel.setRowSpan(rowSpan);
            itemModels.add(spannableItemModel);
        }
        return itemModels;
    }

    public List<ItemTypeModel> convertItemToHeader(List<ItemInfoBean> itemInfoBeans, HomeTab homeTab) {
        List<ItemTypeModel> itemTypeModels = new ArrayList<>();
        ItemTypeModel itemTypeModel;
        for(ItemInfoBean item:itemInfoBeans){
            itemTypeModel = new ItemTypeModel(item,homeTab);
            itemTypeModels.add(itemTypeModel);
        }
        return itemTypeModels;
    }

    public List<FilterItemModel> convertItemToFilterModel(List<InitDataBean> newDisginFilter,HomeTab homeTab) {
        List<FilterItemModel> itemModels = new ArrayList<>();
        FilterItemModel itemModel;
        for(InitDataBean item : newDisginFilter){
            itemModel = new FilterItemModel(item,homeTab);
            itemModels.add(itemModel);
        }
        return itemModels;
    }
}
