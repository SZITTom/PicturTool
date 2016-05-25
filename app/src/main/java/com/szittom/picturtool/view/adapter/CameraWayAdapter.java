package com.szittom.picturtool.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.beam.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.szittom.picturtool.R;
import com.szittom.picturtool.app.App;
import com.szittom.picturtool.interfaces.CameraClick;
import com.szittom.picturtool.model.bean.SingleImageModel;
import com.szittom.picturtool.utils.PicassoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SZITTom on 2016/5/12.
 */
public class CameraWayAdapter extends RecyclerView.Adapter<CameraWayAdapter.CameraWayViewHodler> {

    /** 每张图片需要显示的高度和宽度 */
    private int perWidth;

    private ArrayList<SingleImageModel> lists;
    private CameraClick cameraClick;

    public CameraWayAdapter(ArrayList<SingleImageModel> lists) {
        SingleImageModel single = new SingleImageModel();
        lists.add(single);
        this.lists = lists;
        perWidth = (Utils.getScreenWidth(App.getInstance().getApplicationContext()) - Utils.dip2px(App.getInstance().getApplicationContext(), 4))/3;
    }

    public void setCameraClick(CameraClick cameraClick) {
        this.cameraClick = cameraClick;
    }

    public void setDatas(ArrayList<SingleImageModel> lists) {
        this.lists.addAll(0, lists);
        notifyDataSetChanged();
    }

    @Override
    public CameraWayViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CameraWayViewHodler(parent);
    }

    @Override
    public void onBindViewHolder(CameraWayViewHodler holder, int position) {
        holder.setData(lists.get(position),position);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public List<SingleImageModel> getData() {
        return lists;
    }

    class CameraWayViewHodler extends BaseViewHolder<SingleImageModel>{

        private ImageView mImageView;
        private LinearLayout mLl_camera;

        public CameraWayViewHodler(ViewGroup parent) {
            super(parent, R.layout.item_camera_img);
            mImageView = $(R.id.img_camera);
            mLl_camera = $(R.id.ll_camera);
        }

        @Override
        public void setData(SingleImageModel data) {
            super.setData(data);
        }

        public void setData(final SingleImageModel singleImageModel, final int position) {

            if (position == (getItemCount() -1)){
                mImageView.setImageResource(R.drawable.take_pic);
            }else {
                PicassoUtils.displayImageUrl(getContext(), new File(singleImageModel.path), mImageView);
            }
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cameraClick != null){
                        cameraClick.onClick(singleImageModel, position);
                    }
                }
            });
            mLl_camera.setLayoutParams(new RecyclerView.LayoutParams(perWidth,perWidth));
        }
    }

}
