package com.toniel.githubuser.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.toniel.githubuser.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Delete()
    void delete(User user);

    @Query("SELECT * FROM user WHERE login = :username")
    LiveData<User> findByLogin(String username);

    @Query("SELECT * from user ORDER BY id ASC")
    LiveData<List<User>> getAllUsers();

}
