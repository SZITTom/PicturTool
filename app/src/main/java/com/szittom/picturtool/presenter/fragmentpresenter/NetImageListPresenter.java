package com.szittom.picturtool.presenter.fragmentpresenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.szittom.picturtool.interfaces.SampleScrollListener;
import com.szittom.picturtool.model.NetImageModel;
import com.szittom.picturtool.model.bean.Gallery;
import com.szittom.picturtool.utils.L;
import com.szittom.picturtool.view.activity.ShowDetailsActivity;
import com.szittom.picturtool.view.fragment.NetImageFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by SZITTom on 2016/4/19.
 */
public class NetImageListPresenter extends BeamListFragmentPresenter<NetImageFragment, Gallery> implements RecyclerArrayAdapter.OnItemClickListener {

    private int classifyId;
    private int page = 1;
    private List<Gallery> datas;

    @Override
    protected void onCreate(@NonNull NetImageFragment view, Bundle savedState) {
        super.onCreate(view, savedState);

        if (savedState != null){
            page = savedState.getInt("page");
        }

    }

    @Override
    protected void onSave(Bundle state) {
        super.onSave(state);
        state.putInt("page", page);
    }

    @Override
    protected void onCreateView(@NonNull NetImageFragment view) {
        super.onCreateView(view);
        datas = new ArrayList<>();
        classifyId = view.getArguments().getInt("classifyId");
        view.getListView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        view.getListView().setOnScrollListener(new SampleScrollListener(view.getContext()));
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        page = 1;
        Observable<List<Gallery>> observable = NetImageModel.getNetImageList(page, classifyId);
        observable.subscribe(new Subscriber<List<Gallery>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                L.e("TAG", "onError");
                getRefreshSubscriber().onError(e);
            }

            @Override
            public void onNext(List<Gallery> galleries) {
                datas.clear();
                datas.addAll(galleries);
                getRefreshSubscriber().onNext(datas);
                page++;
                getAdapter().setOnItemClickListener(NetImageListPresenter.this);
            }
        });
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        Observable<List<Gallery>> observable = NetImageModel.getNetImageList(page, classifyId);
        observable.subscribe(new Subscriber<List<Gallery>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                getMoreSubscriber().onError(e);
            }

            @Override
            public void onNext(List<Gallery> galleries) {
                datas.addAll(galleries);
                getMoreSubscriber().onNext(galleries);
                page++;
            }
        });

    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
//        if (!subscriber.isUnsubscribed()){
//            subscriber.unsubscribe();
//        }
    }

    @Override
    public void onItemClick(int position) {
        Gallery gallery = datas.get(position);
        Intent intent = new Intent(getView().getContext(), ShowDetailsActivity.class);
        intent.putExtra("Beam_id",String.valueOf(gallery.getId()));
        intent.putExtra("title",String.valueOf(gallery.getTitle()));
        startActivity(intent);
    }
}
