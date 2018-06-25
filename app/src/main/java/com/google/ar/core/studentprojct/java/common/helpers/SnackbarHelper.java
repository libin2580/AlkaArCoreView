/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.ar.core.studentprojct.java.common.helpers;

import android.app.Activity;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
/**
 * Created by Alka Sebastion on 12/06/2018.
 */

/**
 * Helper to manage the sample snackbar. Hides the Android boilerplate code, and exposes simpler
 * methods.
 */
public final class SnackbarHelper {
  private static final int BACKGROUND_COLOR = 0xbf323232;
  private Snackbar messageSnackbar;
  private enum DismissBehavior {
      /**
       * Hide dismiss behavior.
       */
      HIDE, /**
       * Show dismiss behavior.
       */
      SHOW, /**
       * Finish dismiss behavior.
       */
      FINISH };
  private int maxLines = 2;

    /**
     * Is showing boolean.
     *
     * @return the boolean
     */
    public boolean isShowing() {
    return messageSnackbar != null;
  }

    /**
     * Shows a snackbar with a given message.  @param activity the activity
     *
     * @param message the message
     */
    public void showMessage(Activity activity, String message) {
    show(activity, message, DismissBehavior.HIDE);
  }

    /**
     * Shows a snackbar with a given message, and a dismiss button.  @param activity the activity
     *
     * @param message the message
     */
    public void showMessageWithDismiss(Activity activity, String message) {
    show(activity, message, DismissBehavior.SHOW);
  }

    /**
     * Shows a snackbar with a given error message. When dismissed, will finish the activity. Useful
     * for notifying errors, where no further interaction with the activity is possible.
     *
     * @param activity     the activity
     * @param errorMessage the error message
     */
    public void showError(Activity activity, String errorMessage) {
    show(activity, errorMessage, DismissBehavior.FINISH);
  }

    /**
     * Hides the currently showing snackbar, if there is one. Safe to call from any thread. Safe to
     * call even if snackbar is not shown.
     *
     * @param activity the activity
     */
    public void hide(Activity activity) {
    activity.runOnUiThread(
        new Runnable() {
          @Override
          public void run() {
            if (messageSnackbar != null) {
              messageSnackbar.dismiss();
            }
            messageSnackbar = null;
          }
        });
  }

    /**
     * Sets max lines.
     *
     * @param lines the lines
     */
    public void setMaxLines(int lines) {
    maxLines = lines;
  }

  private void show(
      final Activity activity, final String message, final DismissBehavior dismissBehavior) {
    activity.runOnUiThread(
        new Runnable() {
          @Override
          public void run() {
            messageSnackbar =
                Snackbar.make(
                    activity.findViewById(android.R.id.content),
                    message,
                    Snackbar.LENGTH_INDEFINITE);
            messageSnackbar.getView().setBackgroundColor(BACKGROUND_COLOR);
            if (dismissBehavior != DismissBehavior.HIDE) {
              messageSnackbar.setAction(
                  "Dismiss",
                  new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      messageSnackbar.dismiss();
                    }
                  });
              if (dismissBehavior == DismissBehavior.FINISH) {
                messageSnackbar.addCallback(
                    new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                      @Override
                      public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        activity.finish();
                      }
                    });
              }
            }
            ((TextView)
                    messageSnackbar
                        .getView()
                        .findViewById(android.support.design.R.id.snackbar_text))
                .setMaxLines(maxLines);
            messageSnackbar.show();
          }
        });
  }
}
