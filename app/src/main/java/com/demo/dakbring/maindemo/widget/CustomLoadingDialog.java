package com.demo.dakbring.maindemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.demo.dakbring.maindemo.R;

public class CustomLoadingDialog extends Dialog {

    public CustomLoadingDialog(Context context, boolean cancelable) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(cancelable);
        setContentView(R.layout.view_dialog_progressbar);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public CustomLoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomLoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}