package com.septech.centauri.ui.user.forgotpasswordcode;

import androidx.annotation.Nullable;

public class ForgotPasswordCodeFormState {
    private static final String TAG = "ForgotPasswordCodeFormState";

    @Nullable
    private Integer codeError;

    private boolean codeEdited;

    private boolean isCodeValid;

    ForgotPasswordCodeFormState() {
        this.codeError = null;

        this.codeEdited = false;

        this.isCodeValid = false;
    }

    @Nullable
    public Integer getCodeError() {
        return codeError;
    }

    public void setCodeError(@Nullable Integer codeError) {
        this.codeError = codeError;
    }

    public boolean isCodeEdited() {
        return codeEdited;
    }

    public void setCodeEdited(boolean codeEdited) {
        this.codeEdited = codeEdited;
    }

    boolean isCodeValid() {
        return isCodeValid;
    }

    public void checkCodeValid() {
        if (codeEdited && codeError != null)
            isCodeValid = true;
        isCodeValid = false;
    }
}
