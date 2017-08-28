package com.demo.dakbring.maindemo.base;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.demo.dakbring.maindemo.R;
import com.demo.dakbring.maindemo.widget.CustomLoadingDialog;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected static final int RESULT_CODE_QUIT_APP = -1;

    protected static final int RESULT_LOGOUT = -2;

    private CustomLoadingDialog mProgressDialog;

    protected Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNeedToBeInPortrait()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mHandler = new Handler();
    }

    protected boolean isNeedToBeInPortrait() {
        return true;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public void showProgressDialog(boolean cancelable) {
        if (mProgressDialog == null) {
            mProgressDialog = new CustomLoadingDialog(this, cancelable);
        } else if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void onBackPressed() {
        if (this instanceof IFinishable) {
            setResult(RESULT_CODE_QUIT_APP);
        }
        super.onBackPressed();
    }
}
