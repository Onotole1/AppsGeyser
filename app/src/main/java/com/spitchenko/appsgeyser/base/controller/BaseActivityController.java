package com.spitchenko.appsgeyser.base.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;

import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 16:08
 *
 * @author anatoliy
 */
public interface BaseActivityController {
    void updateOnCreate(@Nullable final Bundle savedInstanceState);
    void updateOnSavedInstanceState(@NonNull final Bundle outState);
    void updateOnRestoreInstanceState(@NonNull final Bundle savedInstanceState);
    void updateOnResume();
    void updateOnPause();
}
