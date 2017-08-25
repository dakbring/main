package com.demo.dakbring.maindemo.wrapper;

import android.support.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({View.VISIBLE, View.GONE})
@Retention(RetentionPolicy.SOURCE)
public @interface Visibility {

}