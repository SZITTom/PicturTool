package com.szittom.picturtool.view;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.szittom.picturtool.R;
import com.szittom.picturtool.config.API;
import com.szittom.picturtool.interfaces.RatioTransformation;
import com.szittom.picturtool.model.bean.Gallery;
import com.szittom.picturtool.utils.PicassoUtils;

/**
 * Created by SZITTom on 2016/4/20.
 */
public class NetImageViewHolder extends BaseViewHolder<Gallery> {

    private ImageView image;

    public NetImageViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_netimage);
        image = $(R.id.net_img);
    }

    @Override
    public void setData(final Gallery data) {
        super.setData(data);
        if (!TextUtils.isEmpty(data.getImg())) {
            PicassoUtils.displayImageUrl(getContext(),API.HOST_IMAGE + data.getImg(), new RatioTransformation(2), image);
        }

    }

}
