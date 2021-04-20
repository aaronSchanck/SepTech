package com.septech.centauri.ui.user.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public interface CallBackListener {
    void showLoadingIcon();

    void hideLoadingIcon();

    void hideKeyboard();
}
