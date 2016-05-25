package com.szittom.picturtool.presenter.activitypresenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager;

import com.jude.beam.Utils;
import com.jude.beam.bijection.Presenter;
import com.szittom.picturtool.interfaces.CameraClick;
import com.szittom.picturtool.model.bean.SingleImageModel;
import com.szittom.picturtool.view.activity.CameraWayActivity;
import com.szittom.picturtool.view.activity.PickOrTakeImageActivity;
import com.szittom.picturtool.view.adapter.CameraWayAdapter;
import com.szittom.picturtool.widget.DividerGridDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SZITTom on 2016/5/12.
 */
public class CameraWayPresenter extends Presenter<CameraWayActivity> {

    public static final int REQUESTCODE = 123;
    private CameraWayAdapter mAdapter;

    @Override
    protected void onCreateView(@NonNull CameraWayActivity view) {
        super.onCreateView(view);

        LayoutManager mLayoutManager = new LinearLayoutManager(getView(),LinearLayoutManager.HORIZONTAL,false);
        getView().getRecyclerView().setLayoutManager(mLayoutManager);

        final ArrayList<SingleImageModel> lists = new ArrayList<>();
        mAdapter = new CameraWayAdapter(lists);
        getView().getRecyclerView().setAdapter(mAdapter);
        getView().getRecyclerView().addItemDecoration(new DividerGridDecoration(getView()));

        mAdapter.setCameraClick(new CameraClick() {
            @Override
            public void onClick(SingleImageModel singleImageModel, int position) {
                Intent intent = new Intent();
                if (position == (mAdapter.getItemCount() - 1)){
                    intent.setClass(getView(), PickOrTakeImageActivity.class);
                    getView().startActivityForResult(intent, REQUESTCODE);
                }else{

                }
            }

            @Override
            public void onChoosePick(ArrayList<SingleImageModel> singleImageModelList) {

            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getView().RESULT_OK) return;
        switch (requestCode){
            case REQUESTCODE:
                ArrayList<SingleImageModel> mCheckedImageList = data.getParcelableArrayListExtra("pathlist");
                if (mAdapter != null){
                    mAdapter.setDatas(mCheckedImageList);
                }
                break;
        }
    }
}
