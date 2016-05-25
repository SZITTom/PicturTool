package com.szittom.picturtool.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.szittom.picturtool.R;
import com.szittom.picturtool.presenter.activitypresenter.MainActivityPresenter;
import com.szittom.picturtool.view.fragment.SearchFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresPresenter(MainActivityPresenter.class)
public class MainActivity extends BeamBaseActivity<MainActivityPresenter>
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    @Bind(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Bind(R.id.search_view)
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        setSupportActionBar(mToolbar);
        onSetToolbar(getToolbar());
        setDrawerLayout();
        initAppBarSetting();
        initSearchView();

    }

    private void initAppBarSetting() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                getPresenter().stopRefresh(verticalOffset);
                if (verticalOffset != 0) {
                    mFab.hide();
                } else {
                    mFab.show();
                }
            }
        });
    }

    private void initSearchView() {
//        searchView.setHint("Search");
        searchView.setVoiceSearch(false);
        searchView.setHintTextColor(Color.GRAY);
        searchView.setSuggestions(new String[] {"2","2","2"});//为了显示透明背景
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra(SearchFragment.SEARCH_DATA, query.toString().trim());
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    private void setDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_main);
    }

    @OnClick(R.id.fab)
    void clickFad() {
        getPresenter().goToUp(0);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            return;
        }
        if (getPresenter().AppExit()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_main) {//首页

        } else if (id == R.id.nav_search) {//搜索图片
            searchView.showSearch();
        } else if (id == R.id.nav_my_picture) {//我的图片

        } else if (id == R.id.nav_camera) {//相机
            startActivity(new Intent(MainActivity.this, CameraWayActivity.class));
        } else if (id == R.id.nav_gallery) {//照片墙

        } else if (id == R.id.nav_share) {//分享

        } else if (id == R.id.nav_manage) {//设置

        } else if (id == R.id.nav_suggestion) {//意见反馈
            startActivity(new Intent(MainActivity.this, SuggestionActivity.class));
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }
}
