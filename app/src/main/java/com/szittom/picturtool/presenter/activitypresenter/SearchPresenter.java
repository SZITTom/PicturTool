package com.szittom.picturtool.presenter.activitypresenter;

import com.jude.beam.bijection.Presenter;
import com.szittom.picturtool.R;
import com.szittom.picturtool.view.activity.SearchActivity;

import java.util.Random;

/**
 * Created by Administrator on 2016/5/25.
 */
public class SearchPresenter extends Presenter<SearchActivity> {

    private int[] bgImgs ={
            R.drawable.bg_1,
            R.drawable.bg_2,
            R.drawable.bg_3,
            R.drawable.bg_4,
            R.drawable.bg_5,
            R.drawable.bg_6,
            R.drawable.bg_7,
            R.drawable.bg_8,
            R.drawable.bg_9,
    };
    public int getBgImg(){
        return bgImgs[new Random().nextInt(bgImgs.length)];
    }


}
