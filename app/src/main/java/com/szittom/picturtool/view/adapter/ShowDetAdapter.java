package com.szittom.picturtool.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.szittom.picturtool.R;
import com.szittom.picturtool.config.API;
import com.szittom.picturtool.interfaces.RatioTransformation;
import com.szittom.picturtool.model.bean.Picture;
import com.szittom.picturtool.utils.PicassoUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by SZITTom on 2016/4/21.
 */
public class ShowDetAdapter extends PagerAdapter {

    private Context context;
    private List<Picture> datas;
    private LayoutInflater mInflater;
    private HashMap<Integer, View> viewDatas;

    public ShowDetAdapter(Context context, List<Picture> datas) {
        this.datas = datas;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        viewDatas = new HashMap<>();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (null == viewDatas.get(position)){
            View view = mInflater.inflate(R.layout.item_pick_big,null);
            viewDatas.put(position, view);

            PhotoView mImageView = (PhotoView)view.findViewById(R.id.photoView);
            mImageView.enable();
            PicassoUtils.displayImageUrl(context,API.HOST_IMAGE + datas.get(position).getSrc(), mImageView);

        }
        container.addView(viewDatas.get(position));
        return viewDatas.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
