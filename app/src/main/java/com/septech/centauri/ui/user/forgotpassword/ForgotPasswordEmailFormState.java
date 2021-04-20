package com.septech.centauri.ui.user.forgotpassword;

import androidx.annotation.Nullable;

public class ForgotPasswordEmailFormState {
    private static final String TAG = "ForgotPasswordFormState";

    @Nullable
    private Integer emailError;

    private boolean emailEdited;

    private boolean isDataValid;

    ForgotPasswordEmailFormState() {
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
