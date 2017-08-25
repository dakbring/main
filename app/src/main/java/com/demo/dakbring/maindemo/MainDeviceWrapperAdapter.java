package com.demo.dakbring.maindemo;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.demo.dakbring.maindemo.utils.UiUtils;
import com.demo.dakbring.maindemo.wrapper.BaseAdapter;
import com.demo.dakbring.maindemo.wrapper.FullViewHolder;
import com.demo.dakbring.maindemo.wrapper.OnErrorRetryListener;
import com.demo.dakbring.maindemo.wrapper.RecyclerViewAdapter;
import com.demo.dakbring.maindemo.wrapper.SpacingHolder;

import java.lang.ref.WeakReference;

public class MainDeviceWrapperAdapter extends RecyclerViewAdapter {

  protected static final int FOOTER_COUNT = 3;

  public static final int HEADER_COUNT = 3;

  protected static final int INDEX_HEADER_EMPTY = 0;

  protected static final int INDEX_HEADER_ERROR = 1;

  protected static final int INDEX_HEADER_LOADING = 2;

  protected static final int INDEX_FOOTER_MENU_SPACE = 2;

  protected static final int INDEX_FOOTER_BOTTOM_SPACE = 1;

  protected static final int INDEX_FOOTER_APPEND_LOADING = 0;

  protected static final int TAG_EMPTY = R.id.tag_empty;

  protected static final int TAG_ERROR = R.id.tag_error;

  protected static final int TAG_LOADING = R.id.tag_loading;

  protected static final int TAG_APPEND_LOADING = R.id.tag_append_loading;

  protected static final int TAG_MENU_SPACE = R.id.tag_menu_space;

  protected static final int TAG_BOTTOM_SPACE = R.id.tag_bottom_space;

  private WeakReference<OnErrorRetryListener> mOnRetryErrorListenerWeakReference;

  public MainDeviceWrapperAdapter(OnErrorRetryListener listener, RecyclerView.Adapter... baseAdapters) {
    super(HEADER_COUNT, FOOTER_COUNT, baseAdapters);
    setViewState(TAG_EMPTY, BaseAdapter.ItemViewType.HEADER, INDEX_HEADER_EMPTY, View.VISIBLE);
    setViewState(TAG_ERROR, BaseAdapter.ItemViewType.HEADER, INDEX_HEADER_ERROR, View.VISIBLE);
    setViewState(TAG_LOADING, BaseAdapter.ItemViewType.HEADER, INDEX_HEADER_LOADING, View.VISIBLE);
    setViewState(TAG_APPEND_LOADING, BaseAdapter.ItemViewType.FOOTER, INDEX_FOOTER_APPEND_LOADING, View.GONE);
    setViewState(TAG_MENU_SPACE, BaseAdapter.ItemViewType.FOOTER, INDEX_FOOTER_MENU_SPACE, View.VISIBLE);
    setViewState(TAG_BOTTOM_SPACE, BaseAdapter.ItemViewType.FOOTER, INDEX_FOOTER_BOTTOM_SPACE, View.VISIBLE);

    for (int i = 0; i < baseAdapters.length; i++) {
      baseAdapters[i].registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
          super.onChanged();
          onBaseAdapterChanged();
        }
      });
    }
    mOnRetryErrorListenerWeakReference = new WeakReference<>(listener);
  }

  @Override
  public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int index) {

  }

  @Override
  public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int index) {

  }

  @Override
  public RecyclerView.ViewHolder onCreateFooterViewHolder(LayoutInflater inflater, ViewGroup parent, int index) {
    switch (index) {
      case INDEX_FOOTER_APPEND_LOADING:
        return createAppendedLoadingHolder(inflater, parent);
      case INDEX_FOOTER_MENU_SPACE:
        return SpacingHolder.newInstance(inflater, parent, SpacingHolder.Type.ACTION_BAR);
      case INDEX_FOOTER_BOTTOM_SPACE:
        return SpacingHolder.newInstance(inflater, parent, SpacingHolder.Type.LIST);
    }
    return null;
  }

  @Override
  public RecyclerView.ViewHolder onCreateHeaderViewHolder(LayoutInflater inflater, ViewGroup parent, int index) {
    switch (index) {
      case INDEX_HEADER_EMPTY:
        return createEmptyHolder(inflater, parent);
      case INDEX_HEADER_LOADING:
        return createLoadingHolder(inflater, parent);
      case INDEX_HEADER_ERROR:
        return createErrorHolder(inflater, parent);
    }
    return null;
  }

  public BaseAdapter.BaseHolder createErrorHolder(LayoutInflater inflater, ViewGroup parent) {
    return new FullViewHolder(R.layout.view_wrapper_error, inflater, parent, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onErrorViewClicked();
      }
    });
  }

  public BaseAdapter.BaseHolder createLoadingHolder(LayoutInflater inflater, ViewGroup parent) {
    return new FullViewHolder(R.layout.view_wrapper_loading, inflater, parent);
  }

  public BaseAdapter.BaseHolder createAppendedLoadingHolder(LayoutInflater inflater, ViewGroup parent) {
    return new AppendedLoadingViewHolder(R.layout.view_wrapper_appended_loading, inflater, parent);
  }

  public void onErrorViewClicked() {

  }

  public void showEmptyView() {
    hideViewState(TAG_ERROR);
    hideViewState(TAG_LOADING);
    hideViewState(TAG_APPEND_LOADING);
    setBaseAdapterEnabled(false);
    showViewState(TAG_EMPTY);
  }

  public void showErrorView() {
    hideViewState(TAG_EMPTY);
    hideViewState(TAG_LOADING);
    hideViewState(TAG_APPEND_LOADING);
    setBaseAdapterEnabled(false);
    showViewState(TAG_ERROR);
  }

  public void showItemView() {
    setBaseAdapterEnabled(true);
    if (getBaseItemCount() == 0) {
      showViewState(TAG_EMPTY);
    } else {
      hideViewState(TAG_EMPTY);
    }
    hideViewState(TAG_LOADING);
    hideViewState(TAG_APPEND_LOADING);
    hideViewState(TAG_ERROR);
  }

  public void showLoadingView() {
    hideViewState(TAG_ERROR);
    hideViewState(TAG_EMPTY);
    setBaseAdapterEnabled(false);
    showViewState(TAG_LOADING);
    hideViewState(TAG_APPEND_LOADING);
  }

  public void showRefreshView() {
    hideViewState(TAG_ERROR);
    hideViewState(TAG_EMPTY);
    setBaseAdapterEnabled(false);
    hideViewState(TAG_LOADING);
    hideViewState(TAG_APPEND_LOADING);
  }

  public void showAppendedLoading() {
    showViewState(TAG_APPEND_LOADING);
  }

  protected void onBaseAdapterChanged() {
    showItemView();
  }

  public FullViewHolder createEmptyHolder(LayoutInflater inflater, ViewGroup parent) {
    FullViewErrorHolder holder = FullViewErrorHolder.newInstance(inflater, parent);
    if (mOnRetryErrorListenerWeakReference != null && mOnRetryErrorListenerWeakReference.get() != null) {
      holder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          UiUtils.preventMultipleClicks(v);
          if (mOnRetryErrorListenerWeakReference.get() != null) {
            mOnRetryErrorListenerWeakReference.get().onRetry();
          }
        }
      });
    }
    return holder;
  }

  private static class AppendedLoadingViewHolder extends BaseAdapter.BaseHolder {

    public AppendedLoadingViewHolder(@LayoutRes int layoutId, LayoutInflater inflater, ViewGroup parent) {
      super(inflater, parent, layoutId, false);
    }
  }

  private static class FullViewErrorHolder extends FullViewHolder {

    public static FullViewErrorHolder newInstance(LayoutInflater inflater, ViewGroup parent) {
      FullViewErrorHolder holder = new FullViewErrorHolder(R.layout.view_wrapper_empty, inflater, parent);
      return holder;
    }

    private View vContainer;

    public FullViewErrorHolder(int layoutId, LayoutInflater inflater, ViewGroup parent) {
      super(layoutId, inflater, parent);
      onViewCreated(itemView);
    }

    public FullViewErrorHolder(int layoutId, LayoutInflater inflater, ViewGroup parent, View.OnClickListener onClickListener) {
      super(layoutId, inflater, parent, onClickListener);
      onViewCreated(itemView);
    }

    public View getView() {
      return itemView;
    }

    public void setOnClickListener(View.OnClickListener listener) {
      vContainer.setOnClickListener(listener);
    }

    protected void onViewCreated(View view) {
      vContainer = view.findViewById(R.id.container);
    }
  }
}
