package com.szittom.picturtool.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.beam.Utils;
import com.squareup.picasso.Picasso;
import com.szittom.picturtool.R;
import com.szittom.picturtool.app.App;
import com.szittom.picturtool.interfaces.CameraClick;
import com.szittom.picturtool.interfaces.PickListClick;
import com.szittom.picturtool.model.bean.ImageDirectoryModel;
import com.szittom.picturtool.model.bean.SingleImageDirectories;
import com.szittom.picturtool.model.bean.SingleImageModel;
import com.szittom.picturtool.utils.PicassoUtils;
import com.szittom.picturtool.utils.T;
import com.szittom.picturtool.view.activity.PickOrTakeImageActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SZITTom on 2016/5/12.
 */
public class PickOrTakeListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private ArrayList<SingleImageDirectories> lists;
    private PickListClick mClick;

    private int currentPosition;//当前选中的位置

    public PickOrTakeListAdapter(Context context, ArrayList<SingleImageDirectories> lists) {
        this.mContext = context;
        this.lists = lists;
        mInflater = LayoutInflater.from(context);
//        perWidth = (Utils.getScreenWidth(App.getInstance().getApplicationContext()) - Utils.dip2px(App.getInstance().getApplicationContext(), 4)) / 3;
    }

    public void setData(ArrayList<SingleImageDirectories> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public void setmClick(PickListClick mClick) {
        this.mClick = mClick;
    }

    @Override
    public int getCount() {
        return lists == null ? 0 : lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PickOrTakeListViewHodler viewHodler;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_pickortake_listview, null);
            viewHodler = new PickOrTakeListViewHodler();
            viewHodler.mIv_directory_pic = (ImageView) convertView.findViewById(R.id.iv_directory_pic);
            viewHodler.mIv_directory_check = (ImageView) convertView.findViewById(R.id.iv_directory_check);
            viewHodler.mTv_directory_name = (TextView) convertView.findViewById(R.id.tv_directory_name);
            viewHodler.mTv_directory_nums = (TextView) convertView.findViewById(R.id.tv_directory_nums);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (PickOrTakeListViewHodler) convertView.getTag();
        }
        final SingleImageDirectories singleImageDirectories = (SingleImageDirectories) getItem(position);
        ImageDirectoryModel imageDirectoryModel = singleImageDirectories.images;
        final ArrayList<SingleImageModel> singleImageModels = imageDirectoryModel.getImages();

        if (!Utils.isArrayisEmpty(singleImageModels)) {
            if (position == 0) {
                PicassoUtils.displayImageUrl(mContext, new File(imageDirectoryModel.getImagePath(1)), viewHodler.mIv_directory_pic);
            } else {
                PicassoUtils.displayImageUrl(mContext, new File(imageDirectoryModel.getImagePath(0)), viewHodler.mIv_directory_pic);
            }
        }
        if (position == 0) {
            viewHodler.mTv_directory_name.setText("全部图片");
        } else {
            viewHodler.mTv_directory_name.setText(new File(singleImageDirectories.directoryPath).getName());
        }
        viewHodler.mTv_directory_nums.setText(imageDirectoryModel.getImageCounts() + "张");
        if (currentPosition != position) {
            viewHodler.mIv_directory_check.setVisibility(View.INVISIBLE);
        } else {
            viewHodler.mIv_directory_check.setVisibility(View.VISIBLE);

        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = position;
                notifyDataSetChanged();
                if (null != mClick) {
                    mClick.onItemClick(singleImageModels, position);
                }
            }
        });

        return convertView;
    }

    class PickOrTakeListViewHodler {
        public ImageView mIv_directory_pic;
        public ImageView mIv_directory_check;
        public TextView mTv_directory_name;
        public TextView mTv_directory_nums;
    }


}
