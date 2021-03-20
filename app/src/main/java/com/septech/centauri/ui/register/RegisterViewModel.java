package com.septech.centauri.ui.register;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.R;
import com.septech.centauri.data.repository.UserDataRepository;
import com.septech.centauri.domain.repository.UserRepository;

public class RegisterViewModel extends ViewModel {
    private static final String TAG = "RegisterViewModel";

    private final MutableLiveData<RegisterFormState> mRegisterFormState = new MutableLiveData<>();

    private final UserRepository userRepo;

    public RegisterViewModel() {
        userRepo = UserDataRepository.getInstance();

        mRegisterFormState.setValue(new RegisterFormState());
    }

    public void createAccount(String email, String password, String firstName, String lastName,
                              String phoneNumber) {
        userRepo.createAccount(email, password, firstName, lastName, phoneNumber);
    }

    public MutableLiveData<RegisterFormState> getRegisterFormState() {
        return mRegisterFormState;
    }

    public void onUpdateFirstName(String fname) {
        RegisterFormState registerFormState = mRegisterFormState.getValue();

        assert registerFormState != null;
        registerFormState.setFirstNameEdited(true);

        if(!isFirstNameValid(fname)) {
            registerFormState.setFirstNameError(R.string.register_string_fname_incorrect);
        } else {
            registerFormState.setFirstNameError(null);
            registerFormState.checkDataValid();
        }

        mRegisterFormState.setValue(registerFormState);
    }

    public void onUpdateLastName(String lname) {
        RegisterFormState registerFormState = mRegisterFormState.getValue();

        assert registerFormState != null;
        registerFormState.setLastNameEdited(true);

        if(!isLastNameValid(lname)) {
            registerFormState.setLastNameError(R.string.register_string_lname_incorrect);
        } else {
            registerFormState.setLastNameError(null);
            registerFormState.checkDataValid();
        }

        mRegisterFormState.setValue(registerFormState);
    }

    public void onUpdateEmail(String email) {
        RegisterFormState registerFormState = mRegisterFormState.getValue();

        assert registerFormState != null;
        registerFormState.setEmailEdited(true);

        if(!isEmailValid(email)) {
            registerFormState.setEmailError(R.string.register_string_email_incorrect);
        } else {
            registerFormState.setEmailError(null);
            registerFormState.checkDataValid();
        }

        mRegisterFormState.setValue(registerFormState);
    }

    public void onUpdatePassword(String password, String confirmPassword) {
        RegisterFormState registerFormState = mRegisterFormState.getValue();

        assert registerFormState != null;
        registerFormState.setPasswordEdited(true);

        if(!isPasswordValid(password)) {
            registerFormState.setPasswordError(R.string.register_string_password_incorrect);
        } else {
            registerFormState.setPasswordError(null);
            registerFormState.checkDataValid();
        }

        if(!isConfirmPasswordValid(confirmPassword, password)) {
            registerFormState.setConfirmPasswordError(R.string.register_string_confirm_password_incorrect);
        } else {
            registerFormState.setConfirmPasswordError(null);
            registerFormState.checkDataValid();
        }

        mRegisterFormState.setValue(registerFormState);
    }

    public void onUpdateConfirmPassword(String confirmPassword, String password) {
        RegisterFormState registerFormState = mRegisterFormState.getValue();

        assert registerFormState != null;
        registerFormState.setConfirmPasswordEdited(true);

        if(!isConfirmPasswordValid(confirmPassword, password)) {
            registerFormState.setConfirmPasswordError(R.string.register_string_confirm_password_incorrect);
        } else {
            registerFormState.setConfirmPasswordError(null);
            registerFormState.checkDataValid();
        }

        mRegisterFormState.setValue(registerFormState);
    }

    public void onUpdatePhoneNumber(String phoneNumber) {
        RegisterFormState registerFormState = mRegisterFormState.getValue();

        assert registerFormState != null;
        registerFormState.setPhoneNumberEdited(true);

        if(!isPhoneNumberValid(phoneNumber)) {
            registerFormState.setPhoneNumberError(R.string.register_string_phone_number_incorrect);
        } else {
            registerFormState.setPhoneNumberError(null);
            registerFormState.checkDataValid();
        }

        mRegisterFormState.setValue(registerFormState);
    }

    public boolean isFirstNameValid(String firstName) {
        return firstName.length() > 1;
    }

    public boolean isLastNameValid(String lastName) {
        return lastName.length() > 1;
    }
    public boolean isEmailValid(String email) {
        return false;
    }

    public boolean isPasswordValid(String password) {
        return false;
    }

    private boolean isConfirmPasswordValid(String confirmPassword, String password) {
        return confirmPassword.equals(password);
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        return false;
    }
}
