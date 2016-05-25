package com.szittom.picturtool.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jude.beam.Utils;
import com.squareup.picasso.Picasso;
import com.szittom.picturtool.R;
import com.szittom.picturtool.app.App;
import com.szittom.picturtool.interfaces.CameraClick;
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
public class PickOrTakeAdapter extends BaseAdapter {

    private boolean isAll;
    private Context mContext;
    private LayoutInflater mInflater;
    /**
     * 每张图片需要显示的高度和宽度
     */
    private int perWidth;

    private ArrayList<SingleImageModel> lists;
    private CameraClick mClick;

    public PickOrTakeAdapter(Context context, ArrayList<SingleImageModel> lists, boolean isAll) {
        if (isAll) {
            SingleImageModel s = new SingleImageModel();
            lists.add(0, s);
        }
        this.isAll = isAll;
        this.mContext = context;
        this.lists = lists;
        mInflater = LayoutInflater.from(context);
        perWidth = (Utils.getScreenWidth(App.getInstance().getApplicationContext()) - Utils.dip2px(App.getInstance().getApplicationContext(), 4)) / 3;
    }

    public void setmClick(CameraClick mClick) {
        this.mClick = mClick;
    }

    public void setData(ArrayList<SingleImageModel> lists, boolean isAll) {
//        lists.clear();
        this.lists = lists;
        this.isAll = isAll;
        notifyDataSetChanged();
    }

    public ArrayList<SingleImageModel> getData() {
        return lists;
    }

    /**
     * 选中的lists数据
     * @return
     */
    public ArrayList<SingleImageModel> getCheckedDatas(){
        ArrayList<SingleImageModel> datasChecked = new ArrayList<>();
        for (SingleImageModel singleImageModel : lists){
            if (singleImageModel.isPicked){
                datasChecked.add(singleImageModel);
            }
        }
        return datasChecked;
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
        PickOrTakeViewHodler viewHodler;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_pickortake, null);
            viewHodler = new PickOrTakeViewHodler();
            viewHodler.mV_gray_masking = convertView.findViewById(R.id.v_gray_masking);
            viewHodler.mIv_pick_or_not = (ImageView)convertView.findViewById(R.id.iv_pick_or_not);
            viewHodler.mIv_content = (ImageView) convertView.findViewById(R.id.iv_content);
            convertView.setTag(viewHodler);
        }
        viewHodler = (PickOrTakeViewHodler)convertView.getTag();
        final SingleImageModel singleImageModel = (SingleImageModel)getItem(position);

        if (position == 0 && isAll) {
            viewHodler.mIv_content.setImageResource(R.drawable.take_pic);
            viewHodler.mIv_pick_or_not.setVisibility(View.GONE);
        } else {
            PicassoUtils.displayImageUrl(mContext,new File(singleImageModel.path),viewHodler.mIv_content);
            viewHodler.mIv_pick_or_not.setVisibility(View.VISIBLE);
        }
        //设置选中状态变化
        if (singleImageModel.isPicked) {
            viewHodler.mV_gray_masking.setVisibility(View.VISIBLE);
            viewHodler.mIv_pick_or_not.setImageResource(R.drawable.image_choose);
        } else {
            viewHodler.mV_gray_masking.setVisibility(View.GONE);
            viewHodler.mIv_pick_or_not.setImageResource(R.drawable.image_not_chose);
        }

        viewHodler.mIv_pick_or_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (singleImageModel.isPicked) {
                    singleImageModel.isPicked = false;
                } else {
                    singleImageModel.isPicked = true;
                }
                if (getCheckedDatas().size() > PickOrTakeImageActivity.NUMTOTAL){
                    T.showToast("你最多只能选择9张图片");
                    singleImageModel.isPicked = false;
                    return;
                }

                if (mClick != null) {
                    mClick.onChoosePick(getCheckedDatas());
                }
                notifyDataSetChanged();
            }
        });

        viewHodler.mIv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClick != null) {
                    mClick.onClick(singleImageModel, position);
                }
            }
        });

        viewHodler.mIv_content.setLayoutParams(new FrameLayout.LayoutParams(perWidth, perWidth));
        viewHodler.mV_gray_masking.setLayoutParams(new FrameLayout.LayoutParams(perWidth, perWidth));


        return convertView;
    }

    class PickOrTakeViewHodler {
        public ImageView mIv_pick_or_not;
        public ImageView mIv_content;
        public View mV_gray_masking;
    }



}
