package com.septech.centauri.ui.user.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public interface CallBackListener {
    /**
     * Will swap to the specified fragment in the parameters. Called when the fragment has to
     * transition to another fragment by itself, with minimal activity assistance.
     * @param fragmentClass
     */
    void OnCallBack(Class fragmentClass, Bundle bundle);

    void showLoadingIcon();

    void hideLoadingIcon();
}
