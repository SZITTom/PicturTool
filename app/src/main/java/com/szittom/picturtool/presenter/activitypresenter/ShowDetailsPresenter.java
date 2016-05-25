package com.szittom.picturtool.presenter.activitypresenter;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.jude.beam.bijection.Presenter;
import com.jude.beam.expansion.BeamBasePresenter;
import com.szittom.picturtool.model.ShowDetailsModel;
import com.szittom.picturtool.model.bean.Gallery;
import com.szittom.picturtool.model.bean.Picture;
import com.szittom.picturtool.utils.L;
import com.szittom.picturtool.view.activity.ShowDetailsActivity;
import com.szittom.picturtool.view.adapter.ShowDetAdapter;

import java.util.List;

import rx.Subscriber;

/**
 * Created by SZITTom on 2016/4/21.
 */
public class ShowDetailsPresenter extends BeamBasePresenter<ShowDetailsActivity> implements ViewPager.OnPageChangeListener {

    private int currentPosition = 0;
    private List<Picture> datas;

    @Override
    protected void onCreateView(@NonNull ShowDetailsActivity view) {
        super.onCreateView(view);
        String id = getIdFromIntent();
//        String id = getView().getIntent().getStringExtra("Beam_id");
        ShowDetailsModel.getAPIShows(id).subscribe(new Subscriber<List<Picture>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                L.i("TAG", e.getMessage()+"ShowDetail request failed");
            }

            @Override
            public void onNext(List<Picture> galleries) {
                datas = galleries;
                if (null != galleries) {
                    ShowDetAdapter mAdapter = new ShowDetAdapter(getView(), galleries);
                    getView().getLarge_viewPager().setAdapter(mAdapter);
                    //getView().getLarge_viewPager().setCurrentItem(0);
                    getView().getLarge_page().setText( (currentPosition+1) +"/"+ galleries.size());
                }
            }
        });
        getView().getLarge_viewPager().addOnPageChangeListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        getView().getLarge_page().setText( (position +1) +"/"+ datas.size());
        currentPosition=position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
