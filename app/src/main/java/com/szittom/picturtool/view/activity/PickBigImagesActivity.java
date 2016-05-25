package com.szittom.picturtool.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.szittom.picturtool.R;
import com.szittom.picturtool.model.bean.SingleImageModel;
import com.szittom.picturtool.presenter.activitypresenter.PickBigImagesPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.ButterKnife;

/**
 * Created by SZITTom on 2016/5/18.
 */
@RequiresPresenter(PickBigImagesPresenter.class)
public class PickBigImagesActivity extends BeamBaseActivity<PickBigImagesPresenter> {

    /** 选择的照片文件夹 */
    public final static String EXTRA_DATA = "extra_data";
    /** 所有被选中的图片 */
    public final static String EXTRA_ALL_PICK_DATA = "extra_pick_data";
    /** 当前被选中的照片 */
    public final static String EXTRA_CURRENT_PIC = "extra_current_pic";
    /** 剩余的可选择照片 */
    public final static String EXTRA_LAST_PIC = "extra_last_pic";
    /** 总的照片 */
    public final static String EXTRA_TOTAL_PIC = "extra_total_pic";

    @Bind(R.id.cb_preview)
    CheckBox mCheckBox;

    @Bind(R.id.btn_choose_finish)
    Button mBtn_choose_finish;

    @Bind(R.id.tv_title)
    TextView mTv_title;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_big_images);
        ButterKnife.bind(this);
        mBtn_choose_finish.setText("完成");
        getToolbar().setVisibility(View.GONE);
        onSetToolbar(getToolbar());

    }


    /**
     * 右上角按钮完成设置
     *                  完成（1/9）
     * @param datas
     *          当前选中的列表
     */
    public void setChooseData(ArrayList<SingleImageModel> datas){
        mBtn_choose_finish.setText("完成" + "("+datas.size() + "/" + PickOrTakeImageActivity.NUMTOTAL+")");
    }

    public void setTitleText(int current, int total) {
        mTv_title.setText(current+"/"+ total);
    }

    @OnClick(R.id.iv_back)
    public void onfinish(View view) {
        finish();
    }


    @OnClick(R.id.btn_choose_finish)
    public void onChooseFinish(View view){
        //完成
        setResult(RESULT_OK,  getPresenter().getChooseFinish());
        finish();


    }

    public CheckBox getCheckBox() {
        return mCheckBox;
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }
}
