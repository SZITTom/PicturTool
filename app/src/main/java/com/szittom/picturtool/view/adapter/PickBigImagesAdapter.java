package com.szittom.picturtool.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.squareup.picasso.Picasso;
import com.szittom.picturtool.R;
import com.szittom.picturtool.interfaces.RatioTransformation;
import com.szittom.picturtool.model.bean.SingleImageModel;
import com.szittom.picturtool.utils.PicassoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SZITTom on 2016/5/18.
 */
public class PickBigImagesAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private HashMap<Integer, View> viewDatas;
    private ArrayList<SingleImageModel> lists;

    public PickBigImagesAdapter(Context context, ArrayList<SingleImageModel> lists) {
        this.lists = lists;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        viewDatas = new HashMap<>();
    }

    @Override
    public int getCount() {
        return lists == null ? 0 : lists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (null == viewDatas.get(position)) {
            View view = mInflater.inflate(R.layout.item_pick_big, null);
            PhotoView mImageView = (PhotoView) view.findViewById(R.id.photoView);
            mImageView.enable();// 启用图片缩放功能
            final SingleImageModel items = lists.get(position);
            if (null != items.path) {
                PicassoUtils.displayImageUrl(mContext, new File(items.path), new RatioTransformation(1), mImageView);
            }else {
                mImageView.setImageResource(R.drawable.icn_close);
            }
            viewDatas.put(position, view);
        }
        container.addView(viewDatas.get(position));
        return viewDatas.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
