package com.demo.dakbring.maindemo.wrapper;

public interface MultiStateAdapter {

  void hideViewState(int tag);

  boolean isViewStateHidden(int tag);

  boolean isViewStateShowed(int tag);

  void onViewStateVisibilityChange(int tag, BaseAdapter.ItemViewType itemViewType, int index, @Visibility int visibility, int position);

  void setFooterViewState(int tag, int index, @Visibility int initVisibility);

  void setHeaderViewState(int tag, int index, @Visibility int initVisibility);

  void setViewState(int tag, BaseAdapter.ItemViewType itemViewType, int index, @Visibility int initVisibility);

  void setViewStateVisibility(int tag, @Visibility int visibility);

  void showViewState(int tag);
}