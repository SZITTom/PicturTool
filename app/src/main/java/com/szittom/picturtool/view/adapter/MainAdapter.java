package com.szittom.picturtool.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.szittom.picturtool.model.bean.Galleryclass;
import com.szittom.picturtool.view.fragment.NetImageFragment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by SZITTom on 2016/4/19.
 */
public class MainAdapter extends FragmentPagerAdapter {

    private List<Galleryclass> galleryclasses;
    private HashMap<String, Fragment> fragments;

    public MainAdapter(FragmentManager fm,List<Galleryclass> galleryclasses) {
        super(fm);
        this.galleryclasses = galleryclasses;
        fragments = new HashMap<String,Fragment>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
//        switch (position){
//            case 0:
//                fragment = new NetImageFragment();
//                break;
//            default:
                fragment = new NetImageFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("classifyId", galleryclasses.get(position).getId());
                fragment.setArguments(bundle);
//                break;
//        }


        fragments.put(""+ position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return galleryclasses.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return galleryclasses.get(position).getName();
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        super.destroyItem(container, position, object);
        fragments.remove(position);
    }

    public Fragment getFragment(int position){
        return fragments.get(position+"");
    }
}
