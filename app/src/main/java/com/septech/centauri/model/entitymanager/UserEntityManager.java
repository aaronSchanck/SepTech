package com.septech.centauri.model.entitymanager;

import com.septech.centauri.model.cache.database.betelgeuse.BetelgeuseDatabase;
import com.septech.centauri.model.net.RestApi;
import com.septech.centauri.model.net.RestApiImpl;

public class UserEntityManager {
    private RestApi remoteApi;
    private BetelgeuseDatabase localDb;
    // private Cache cache;

    public UserEntityManager() {
        this.remoteApi = new RestApiImpl();
        this.localDb = BetelgeuseDatabase.getDatabase();
    }
}
