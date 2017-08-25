package com.demo.dakbring.maindemo.wrapper;

public interface EndlessAdapter {

  int getEndlessThreshold();

  void setEndlessThreshold(int threshold);

  void onNext();

  void setEndlessEnabled(boolean enabled);

  void setOnEndlessListener(OnEndlessListener listener);

  interface OnEndlessListener {

    void onReachThreshold();
  }
}