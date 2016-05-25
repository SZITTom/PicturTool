package com.szittom.picturtool.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.szittom.picturtool.R;
import com.szittom.picturtool.presenter.activitypresenter.ShowDetailsPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(ShowDetailsPresenter.class)
public class ShowDetailsActivity extends BeamBaseActivity<ShowDetailsPresenter> {

    @Bind(R.id.large_page)
    TextView large_page;

    @Bind(R.id.large_viewPager)
    ViewPager large_viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        ButterKnife.bind(this);

        String str = getIntent().getStringExtra("title");
        getToolbar().setTitle(str);
        onSetToolbar(getToolbar());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.context_menu) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public TextView getLarge_page() {
        return large_page;
    }

    public ViewPager getLarge_viewPager() {
        return large_viewPager;
    }
}
