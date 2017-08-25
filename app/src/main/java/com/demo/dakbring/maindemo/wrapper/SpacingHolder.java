package com.demo.dakbring.maindemo.wrapper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.dakbring.maindemo.R;

public class SpacingHolder extends BaseAdapter.BaseHolder {

  public static SpacingHolder newInstance(LayoutInflater inflater, ViewGroup parent, Type type) {
    int layoutId = R.layout.holder_spacing_list;
    switch (type) {
      case LIST:
        layoutId = R.layout.holder_spacing_list;
        break;
      case CARD_VIEW:
        layoutId = R.layout.holder_spacing_card_view;
        break;
      case ACTION_BAR:
        layoutId = R.layout.holder_spacing_action_bar;
        break;
    }
    return new SpacingHolder(inflater.inflate(layoutId, parent, false));
  }

  protected SpacingHolder(View itemView) {
    super(itemView);
  }

  public enum Type {
    LIST, CARD_VIEW, ACTION_BAR
  }
}