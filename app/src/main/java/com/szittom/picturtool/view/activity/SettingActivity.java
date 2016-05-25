package com.szittom.picturtool.view.activity;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.szittom.picturtool.R;
import com.szittom.picturtool.config.AppConfig;
import com.szittom.picturtool.presenter.activitypresenter.SettingPresenter;
import com.szittom.picturtool.utils.PreferenceUtils;
import com.szittom.picturtool.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresPresenter(SettingPresenter.class)
public class SettingActivity extends BeamBaseActivity<SettingPresenter> {

    @Bind(R.id.tv_update)
    TextView mTv_update;

    @Bind(R.id.setting_switch_wifi)
    SwitchCompat mSwitch_wifi;

    @Bind(R.id.setting_switch_push)
    SwitchCompat mSwitch_push;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        getToolbar().setTitle("");
        onSetToolbar(getToolbar());
        mSwitch_wifi.setChecked(AppConfig.getShouldWifi());
        mSwitch_push.setChecked(AppConfig.getShouldPush());

    }

    @OnClick(R.id.setting_switch_wifi)
    public void onSwitchWifi() {
        if (mSwitch_wifi.isChecked()) {
            T.showToast("已开启仅wifi下加载图片功能");
        } else {
            T.showToast("已关闭仅wifi下加载图片功能");
        }
        AppConfig.putShouldWifi(mSwitch_wifi.isChecked());
    }

    @OnClick(R.id.setting_switch_push)
    public void onSwitchPush() {
        if (mSwitch_push.isChecked()) {
            T.showToast("已开启接收消息推送功能");
        } else {
            T.showToast("已关闭接收消息推送功能");
        }
        AppConfig.putShouldPush(mSwitch_push.isChecked());
    }
}
