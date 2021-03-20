package com.septech.centauri.ui.register;

import com.septech.centauri.data.repository.UserDataRepository;
import com.septech.centauri.domain.repository.UserRepository;

public class RegisterViewModel {
    private static final String TAG = "RegisterViewModel";

    private RegisterFormState registerFormState;

    private final UserRepository userRepo;

    public RegisterViewModel() {
        userRepo = UserDataRepository.getInstance();
    }

    public void createAccount(String email, String password, String firstName, String lastName,
                              String phoneNumber) {
        userRepo.createAccount(email, password, firstName, lastName, phoneNumber);
    }

    public boolean isEmailValid(String email) {
        return false;
    }

    public boolean isPasswordValid(String password) {
        return false;
    }

    public boolean isFirstNameValid(String firstName) {
        return false;
    }

    public boolean isLastNameValid(String firstName) {
        return false;
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        return false;
    }
}
