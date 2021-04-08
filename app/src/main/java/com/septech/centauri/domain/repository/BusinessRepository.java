package com.septech.centauri.domain.repository;

import com.septech.centauri.domain.models.Business;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface BusinessRepository {
    Single<Business> getBusinessById(int id);

    Single<Business> getBusinessByEmail(String email);

    Single<Business> login(String email, String password, String passwordSalt);

    Single<Business> createBusinessAccount(Business business);
}
