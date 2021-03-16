package com.septech.centauri.model.cache.database.betelgeuse.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.septech.centauri.model.cache.database.betelgeuse.models.UserModel;
import java.util.List;

import io.reactivex.Observable;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    Observable<List<UserModel>> getAll();

    @Query("SELECT * FROM users WHERE username LIKE :username AND password LIKE :password LIMIT 1")
    UserModel login(String username, String password);

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    List<UserModel> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM users WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    UserModel findByName(String first, String last);

    @Insert
    void insertAll(UserModel... localUsers);

    @Insert
    void insertInto(UserModel localUser);

    @Delete
    void delete(UserModel localUser);
}
