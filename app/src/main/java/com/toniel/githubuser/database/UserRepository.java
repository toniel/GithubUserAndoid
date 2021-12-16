package com.toniel.githubuser.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.toniel.githubuser.model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private final UserDao userDao;
    private final ExecutorService executorService;

    public UserRepository(Application application){
        executorService = Executors.newSingleThreadExecutor();
        UserRoomDatabase database = UserRoomDatabase.getDatabase(application);
        userDao = database.userDao();
    }

    public LiveData<List<User>> getAllUsers(){
        return userDao.getAllUsers();
    }

    public LiveData<User> findByLogin(String username){
        return userDao.findByLogin(username);
    }

    public void insert(final User user){
        executorService.execute(()->userDao.insert(user));
    }

    public void delete(final User user){
        executorService.execute(()->userDao.delete(user));
    }

}
