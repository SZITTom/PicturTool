package com.szittom.picturtool.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.szittom.picturtool.R;
import com.szittom.picturtool.presenter.activitypresenter.CameraWayPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(CameraWayPresenter.class)
public class CameraWayActivity extends BeamBaseActivity<CameraWayPresenter> {

    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_way);
        ButterKnife.bind(this);

        getToolbar().setTitle("点评");
        onSetToolbar(getToolbar());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getPresenter().onActivityResult(requestCode, resultCode,data);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
