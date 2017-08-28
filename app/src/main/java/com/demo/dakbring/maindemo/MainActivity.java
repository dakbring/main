package com.demo.dakbring.maindemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.demo.dakbring.maindemo.base.BaseActivity;
import com.demo.dakbring.maindemo.screens.DashboardActivity;

import java.lang.ref.WeakReference;

public class MainActivity extends BaseActivity {

    private static final long TRANSITION_TIME = 250;

    private static final int REQUEST_DEFAULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkState(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_CODE_QUIT_APP:
                finish();
                break;
        }
    }

    private void checkState(final Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                postActivity(DashboardActivity.getIntent(context), REQUEST_DEFAULT);
            }
        }, 600);
    }

    private void postActivity(Intent intent, int requestCode) {
        if (mHandler != null) {
            mHandler.postDelayed(new TransitionRunnable(this, intent, requestCode), TRANSITION_TIME);
        }
    }

    private class TransitionRunnable implements Runnable {

        private WeakReference<MainActivity> mWeakReference;

        private Intent mIntent;

        private int mRequestCode = -1;

        public TransitionRunnable(MainActivity activity, Intent intent, int requestCode) {
            mWeakReference = new WeakReference<>(activity);
            mIntent = intent;
            mRequestCode = requestCode;
        }


        @Override
        public void run() {
            if (mWeakReference != null) {
                MainActivity activity = mWeakReference.get();
                if (activity != null && mIntent != null && mRequestCode > 0) {
                    activity.startActivityForResult(mIntent, mRequestCode);
                }
            }
        }
    }
}
