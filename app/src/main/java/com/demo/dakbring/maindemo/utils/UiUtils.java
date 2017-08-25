package com.demo.dakbring.maindemo.utils;

import android.app.Activity;
import android.view.View;

public class UiUtils {

  private static int DELAY_TIME = 500;

  private static int DELAY_ENABLE_SEEKING_ACTION = 100;

  private static boolean sBeAble = true;

  public static void preventMultipleClicks(View view) {
    if (view == null) {
      return;
    }
    view.setEnabled(false);
    setEnabledDelay(view, true, DELAY_TIME);
  }

  public static void preventTooFastSeekingAction(View view) {
    if (view == null) {
      return;
    }
    sBeAble = false;
    setEnableSeekingAction(view, true, DELAY_ENABLE_SEEKING_ACTION);
  }

  public static boolean isAbleToSendAction() {
    return sBeAble;
  }

  private static void setEnableSeekingAction(final View view, final boolean enabled, long delay) {
    if (view == null) {
      return;
    }
    view.postDelayed(new Runnable() {
      @Override
      public void run() {
        sBeAble = true;
      }
    }, delay);
  }

  private static void setEnabledDelay(final View view, final boolean enabled, long delay) {
    if (view == null) {
      return;
    }
    view.postDelayed(new Runnable() {
      @Override
      public void run() {
        view.setEnabled(enabled);
      }
    }, delay);
  }

  public static int getIdentifier(Activity activity, String name) {
    if (activity != null) {
      return activity.getResources().getIdentifier(name, "drawable", activity.getPackageName());
    } else {
      return 0;
    }
  }
}
