package com.szittom.picturtool.interfaces;

import com.szittom.picturtool.model.bean.SingleImageModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/23.
 */
public interface PickListClick {
    void onItemClick(ArrayList<SingleImageModel> singleImageModelList, int position);
}
