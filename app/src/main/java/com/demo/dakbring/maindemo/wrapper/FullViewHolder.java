package com.demo.dakbring.maindemo.wrapper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FullViewHolder extends BaseAdapter.BaseHolder {

  protected static View getFullView(int layoutId, LayoutInflater inflater, ViewGroup parent, View.OnClickListener onClickListener) {
    View view = inflater.inflate(layoutId, parent, false);
    view.getLayoutParams().height = parent.getMeasuredHeight();
    view.getLayoutParams().width = parent.getMeasuredWidth();
    if (onClickListener != null) {
      view.setOnClickListener(onClickListener);
    }
    return view;
  }

  public FullViewHolder(int layoutId, LayoutInflater inflater, ViewGroup parent) {
    this(layoutId, inflater, parent, null);
  }

  public FullViewHolder(int layoutId, LayoutInflater inflater, ViewGroup parent, View.OnClickListener onClickListener) {
    super(getFullView(layoutId, inflater, parent, onClickListener));
  }
}