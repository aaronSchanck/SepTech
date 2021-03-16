package com.septech.centauri.viewmodel.viewmodel;

import com.septech.centauri.model.repository.UserDataRepository;
import com.septech.centauri.viewmodel.repository.UserRepository;

public class UserViewModel {
    private UserRepository userRepo;

    public UserViewModel() {
        userRepo = UserDataRepository.getInstance();
    }
}
