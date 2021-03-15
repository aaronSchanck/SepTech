package com.septech.centauri.model.cache.database.betelgeuse.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.septech.centauri.model.cache.database.betelgeuse.models.LocalUser;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<LocalUser> getAll();

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    List<LocalUser> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM users WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    LocalUser findByName(String first, String last);

    @Insert
    void insertAll(LocalUser... localUsers);

    @Insert
    void insertInto(LocalUser localUser);

    @Delete
    void delete(LocalUser localUser);
}
