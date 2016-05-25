package com.szittom.picturtool.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.beam.Utils;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.szittom.picturtool.R;
import com.szittom.picturtool.model.bean.SingleImageModel;
import com.szittom.picturtool.presenter.activitypresenter.PickOrTakeImagePresenter;
import com.szittom.picturtool.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresPresenter(PickOrTakeImagePresenter.class)
public class PickOrTakeImageActivity extends BeamBaseActivity<PickOrTakeImagePresenter> {

    @Bind(R.id.gv_content)
    GridView mGv_content;

    @Bind(R.id.rl_date)
    RelativeLayout mRl_date;

    @Bind(R.id.tv_date)
    TextView mTv_date;

    @Bind(R.id.tv_choose_image_directory)
    TextView mTv_choose_image_directory;

    @Bind(R.id.tv_preview)
    TextView mTv_preview;

    @Bind(R.id.tv_title)
    TextView mTv_title;

    @Bind(R.id.btn_choose_finish)
    Button mBtn_choose_finish;

    @Bind(R.id.lv_directories)
    ListView mLv_directories;

    @Bind(R.id.rl_choose_directory)
    RelativeLayout mRl_choose_directory;

    private boolean isJumpActivition;

    public static final int NUMTOTAL = 9;
    /**
     * 选中的图片
     */
    private ArrayList<SingleImageModel> mCheckedImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_or_take_image);
        ButterKnife.bind(this);
        getToolbar().setVisibility(View.GONE);
        onSetToolbar(getToolbar());
        initData();
    }

    private void initData() {
        mTv_title.setText("选择图片");
        mTv_choose_image_directory.setText("全部图片");
        mTv_preview.setText("预览");
        mBtn_choose_finish.setText("完成");
    }

    public void showTimeLine(String date) {
        mRl_date.setVisibility(View.VISIBLE);
        mTv_date.setText(date);
    }
    public void showChooseImageDir(String str) {
        mTv_choose_image_directory.setText(str);
    }

    @OnClick(R.id.iv_back)
    public void onfinish(View view) {
        finish();
    }

    @OnClick(R.id.btn_choose_finish)
    public void onChoose_finish(View view) {
        //完成
        if (Utils.isArrayisEmpty(mCheckedImageList) || !isJumpActivition) {
            T.showToast("请选择图片");
            return;
        }
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("pathlist", mCheckedImageList);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.tv_preview)
    public void onPreview(View view) {
        //预览
        if (!isJumpActivition) {
            return;
        }
        getPresenter().jumpPickBigImages(0, getPresenter().getAdapter().getCheckedDatas());

    }

    @OnClick({R.id.tv_choose_image_directory,R.id.rl_choose_directory})
    public void onChoose_image() {
        //全部图片
        if (Build.VERSION.SDK_INT < 11) {
            if (mRl_choose_directory.getVisibility() == View.VISIBLE) {
                mRl_choose_directory.setVisibility(View.GONE);
            } else {
                getPresenter().UpdateListData();
                mRl_choose_directory.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLv_directories.getLayoutParams();
                params.bottomMargin = 0;
                mLv_directories.setLayoutParams(params);
                ((ViewGroup) (mLv_directories.getParent())).invalidate();
            }
        } else {
            getPresenter().onChoose_Image();
        }
    }

    /**
     *
     * @param singleImageModelList
     *              选中的列表isPicked
     */
    public void setTextViewData(ArrayList<SingleImageModel> singleImageModelList) {
        this.mCheckedImageList = singleImageModelList;
        int num = singleImageModelList.size();
        if (num == 0) {
            mTv_preview.setText("预览");
            mBtn_choose_finish.setText("完成");
            isJumpActivition = false;
        } else {
            isJumpActivition = true;
            mTv_preview.setText(new StringBuilder().append("预览").append("(").append(num).append(")").toString());
            mBtn_choose_finish.setText(new StringBuilder().append("完成").append("(").append(num).append("/").append(NUMTOTAL).append(")").toString());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getPresenter().onActivittResult(requestCode, resultCode, data);
    }

    public GridView getGridView() {
        return mGv_content;
    }

    public RelativeLayout getmRl_date() {
        return mRl_date;
    }

    public RelativeLayout getRl_choose_directory() {
        return mRl_choose_directory;
    }

    public ListView getLv_directories() {
        return mLv_directories;
    }
}
