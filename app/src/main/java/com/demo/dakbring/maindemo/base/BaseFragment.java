package com.demo.dakbring.maindemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    protected BaseActivity mHost;

    private static final int MAX_CLICK_DURATION = 300;

    private static final int MAX_CLICK_DISTANCE = 15;

    private long pressStartTime;

    private float pressedX;

    private float pressedY;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mHost = (BaseActivity) activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHost = null;
    }

    public void showProgressDialog(boolean cancelable) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showProgressDialog(cancelable);
        }
    }

    public void dismissProgressDialog() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).dismissProgressDialog();
        }
    }
}
