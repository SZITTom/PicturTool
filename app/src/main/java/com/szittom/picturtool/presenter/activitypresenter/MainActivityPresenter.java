package com.szittom.picturtool.presenter.activitypresenter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.jude.beam.bijection.Presenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.szittom.picturtool.model.ClassifyModel;
import com.szittom.picturtool.model.bean.Galleryclass;
import com.szittom.picturtool.utils.T;
import com.szittom.picturtool.view.activity.MainActivity;
import com.szittom.picturtool.view.adapter.MainAdapter;

import java.util.List;

import rx.Subscriber;

/**
 * Created by SZITTom on 2016/4/18.
 */
public class MainActivityPresenter extends Presenter<MainActivity> {

    private MainAdapter mAdapter;
    FragmentManager fragmentManager;

    @Override
    protected void onCreateView(@NonNull MainActivity view) {
        super.onCreateView(view);
        fragmentManager = view.getSupportFragmentManager();
        ClassifyModel.getClassifys().subscribe(subscriber);

    }

    Subscriber<List<Galleryclass>> subscriber = new Subscriber<List<Galleryclass>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            T.showToast(getView().getApplicationContext(), "分类信息为获取");
        }

        @Override
        public void onNext(List<Galleryclass> galleryclasses) {
            replaceFragment(galleryclasses);
        }
    };

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
        if (!subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }

    }

    private void replaceFragment(List<Galleryclass> galleryclasses) {
        if (null == mAdapter) {
            mAdapter = new MainAdapter(fragmentManager, galleryclasses);
        }
        getView().getViewPager().setAdapter(mAdapter);
        getView().getTabLayout().setupWithViewPager(getView().getViewPager());
    }


    /**
     * 记录两次退出时间间隔
     */
    private long mExitTime;

    public boolean AppExit() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - mExitTime) > 2000) {
            T.showToast(getView().getApplicationContext(), "再按一次推出");
            mExitTime = currentTime;
        } else {

            return true;
        }
        return false;
    }

    public void goToUp(int position) {
        if (mAdapter.getFragment(getView().getViewPager().getCurrentItem()) != null) {
//            if (getView().getViewPager().getCurrentItem() == 0) {
//                ((MainFragment) mAdapter.getFragment(0)).recyclerView.scrollToPosition(position);
//            } else {
                ((BeamListFragment) mAdapter.getFragment((getView().getViewPager().getCurrentItem()))).getListView().scrollToPosition(position);
//            }
        }
    }

    public void stopRefresh(int i) {
        if (getView().getViewPager().getCurrentItem() != 0 &&
                mAdapter.getFragment(getView().getViewPager().getCurrentItem()) != null)
            ((BeamListFragment) mAdapter.getFragment((getView().getViewPager().getCurrentItem()))).getListView().getSwipeToRefresh().setEnabled(i == 0);
    }

}
