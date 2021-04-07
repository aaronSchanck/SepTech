package com.septech.centauri.data.repository;

import com.septech.centauri.data.cache.FileCache;
import com.septech.centauri.data.db.betelgeuse.BetelgeuseDatabase;
import com.septech.centauri.data.model.business.mapper.BusinessDataMapper;
import com.septech.centauri.data.net.RestApiClient;
import com.septech.centauri.data.utils.PasswordUtils;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.domain.repository.BusinessRepository;

import io.reactivex.Single;

public class BusinessDataRepository implements BusinessRepository {
    private static final String TAG = BusinessDataRepository.class.getSimpleName();

    private static BusinessRepository mInstance;

    private BetelgeuseDatabase database;
    private final FileCache fileCache;
    private final RestApiClient restApiImpl;
    private final BetelgeuseDatabase localDb;


    private BusinessDataRepository() {
        this.restApiImpl = RestApiClient.getInstance();
        this.localDb = BetelgeuseDatabase.getDatabase();
        this.fileCache = new FileCache();
    }

    public static BusinessRepository getInstance() {
        if (mInstance == null) {
            mInstance = new BusinessDataRepository();
        }
        return mInstance;
    }

    @Override
    public Single<Business> login(final String email, final String password, String passwordSalt) {
        PasswordUtils pwUtils = new PasswordUtils(password, passwordSalt);
        String pwHash = pwUtils.hash();

        return restApiImpl.businessLogin(email, pwHash).map(BusinessDataMapper::transform);
    }

    @Override
    public Single<Business> getBusinessByEmail(String email) {
        return restApiImpl.getBusinessByEmail(email).map(BusinessDataMapper::transform);
    }
}
