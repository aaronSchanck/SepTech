package com.septech.centauri.ui.user.home;

import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.UserRepositoryImpl;
import com.septech.centauri.domain.repository.UserRepository;

public class HomeViewModel extends ViewModel {
    private UserRepository userRepo;

    private int userId;

    public HomeViewModel() {
        userRepo = UserRepositoryImpl.getInstance();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
