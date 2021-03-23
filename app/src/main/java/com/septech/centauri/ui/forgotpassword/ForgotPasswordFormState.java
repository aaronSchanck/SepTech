package com.septech.centauri.ui.forgotpassword;

import androidx.annotation.Nullable;

public class ForgotPasswordFormState {
    private static final String TAG = "ForgotPasswordFormState";

    @Nullable
    private Integer emailError;

    private boolean emailEdited;

    private boolean isDataValid;

    ForgotPasswordFormState() {
        this.emailError = null;

        this.emailEdited = false;

        this.isDataValid = false;
    }

    @Nullable
    public Integer getEmailError() {
        return emailError;
    }

    public void setEmailError(@Nullable Integer emailError) {
        this.emailError = emailError;
    }

    public boolean isEmailEdited() {
        return emailEdited;
    }

    public void setEmailEdited(boolean emailEdited) {
        this.emailEdited = emailEdited;
    }


    boolean isDataValid() {
        return isDataValid;
    }

    public void checkDataValid() {
        if (emailEdited && emailError != null)
            isDataValid = true;
        isDataValid = false;
    }
}
