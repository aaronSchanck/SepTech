package com.septech.centauri.data.db.betelgeuse.user.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.septech.centauri.data.db.betelgeuse.user.model.UserModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    Observable<List<UserModel>> getAllUsers();

    @Query("SELECT * FROM users WHERE email=:email AND password=:password")
    Single<UserModel> login(String email, String password);

    @Query("SELECT * FROM users WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    UserModel findByName(String first, String last);

    @Insert
    void insertAll(UserModel... user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInto(UserModel user);

    @Delete
    void delete(UserModel user);
}
