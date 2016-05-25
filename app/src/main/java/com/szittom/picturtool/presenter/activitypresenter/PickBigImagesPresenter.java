package com.szittom.picturtool.presenter.activitypresenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.widget.CompoundButton;

import com.jude.beam.expansion.BeamBasePresenter;
import com.szittom.picturtool.model.bean.SingleImageModel;
import com.szittom.picturtool.view.activity.PickBigImagesActivity;
import com.szittom.picturtool.view.adapter.PickBigImagesAdapter;

import java.util.ArrayList;

/**
 * Created by SZITTom on 2016/5/18.
 */
public class PickBigImagesPresenter extends BeamBasePresenter<PickBigImagesActivity> implements ViewPager.OnPageChangeListener, CompoundButton.OnCheckedChangeListener {

    /** 传进来照片*/
    private ArrayList<SingleImageModel> datas;

    /** 当前选中的位置*/
    private int currentPosition;
    /** 当前滑动的位置*/
    private int currentScrolled;

    @Override
    protected void onCreateView(@NonNull PickBigImagesActivity view) {
        super.onCreateView(view);

        initParams();

        PickBigImagesAdapter mAdapter = new PickBigImagesAdapter(getView(), datas);
        getView().getViewPager().setAdapter(mAdapter);
        getView().getViewPager().addOnPageChangeListener(this);
        getView().getViewPager().setCurrentItem(currentPosition);
        getView().setTitleText((currentPosition + 1), datas.size());
        getView().setChooseData(getChooseData());

        getView().getCheckBox().setChecked(datas.get(currentPosition).isPicked);
        getView().getCheckBox().setOnCheckedChangeListener(this);
    }

    private void initParams() {
        Intent i = getView().getIntent();
        datas = i.getParcelableArrayListExtra(PickBigImagesActivity.EXTRA_DATA);
        currentPosition = i.getIntExtra(PickBigImagesActivity.EXTRA_CURRENT_PIC,0);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.currentScrolled = position;
        SingleImageModel items = datas.get(position);
        int current = position + 1;
        getView().setTitleText(current, datas.size());
        getView().getCheckBox().setChecked(items.isPicked);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SingleImageModel items = datas.get(currentScrolled);
        items.isPicked = isChecked;

        getView().setChooseData(getChooseData());
    }

    /**
     * 获得选中的列表
     * @return
     */
    public ArrayList<SingleImageModel> getChooseData(){
        ArrayList<SingleImageModel> dataChoose = new ArrayList<>();
        for (SingleImageModel singleImageModel : datas){
            if (singleImageModel.isPicked){
                dataChoose.add(singleImageModel);
            }
        }
        return dataChoose;
    }

    //完成
    public Intent getChooseFinish() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(PickBigImagesActivity.EXTRA_ALL_PICK_DATA,datas);
        return intent;
    }
}
