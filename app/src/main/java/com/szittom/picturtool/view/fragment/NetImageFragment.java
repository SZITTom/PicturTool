package com.szittom.picturtool.view.fragment;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.szittom.picturtool.R;
import com.szittom.picturtool.model.bean.Gallery;
import com.szittom.picturtool.presenter.fragmentpresenter.NetImageListPresenter;
import com.szittom.picturtool.view.NetImageViewHolder;

/**
 * Created by SZITTom on 2016/4/19.
 */
@RequiresPresenter(NetImageListPresenter.class)
public class NetImageFragment extends BeamListFragment<NetImageListPresenter,Gallery> {

    @Override
    protected ListConfig getConfig() {
        return super.getConfig()
                .setRefreshAble(true)//开启下拉刷新
                .setNoMoreAble(true)//显示没有更多
                .setLoadmoreAble(true)//开启加载更多
                .setErrorAble(true)
                .setContainerErrorAble(true)
                .setContainerErrorRes(R.layout.view_net_error)
                .setContainerProgressRes(R.layout.page_progress)
                .setContainerEmptyRes(R.layout.view_empty)
                .setLoadMoreRes(R.layout.page_loadmore);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new NetImageViewHolder(parent);
    }

}
