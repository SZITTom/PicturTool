package com.szittom.picturtool.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BaseFragmentManager;
import com.jude.beam.expansion.BeamBaseActivity;
import com.szittom.picturtool.R;
import com.szittom.picturtool.presenter.activitypresenter.SearchPresenter;
import com.szittom.picturtool.view.fragment.SearchFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(SearchPresenter.class)
public class SearchActivity extends BeamBaseActivity<SearchPresenter> {

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.bg_img)
    ImageView mBg_imag;

    private String searchParams;
    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);
        initParams();

        getToolbar().setTitle(searchParams);
        onSetToolbar(getToolbar());

        if (imgUrl != null){

        }else {
            mBg_imag.setImageResource(getPresenter().getBgImg());
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        SearchFragment searchFragment = SearchFragment.newInstance(searchParams);
        BaseFragmentManager baseFragmentManager = new BaseFragmentManager(getSupportFragmentManager());
        baseFragmentManager.replace(R.id.search_container, searchFragment);
    }

    private void initParams() {
        searchParams = getIntent().getStringExtra(SearchFragment.SEARCH_DATA);

    }
}
