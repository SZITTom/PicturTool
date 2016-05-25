package com.szittom.picturtool.interfaces;

import com.szittom.picturtool.model.bean.SingleImageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SZITTom on 2016/5/13.
 */
public interface CameraClick {
    void onClick(SingleImageModel singleImageModel, int position);
    void onChoosePick(ArrayList<SingleImageModel> singleImageModelList);
}
