package com.toniel.githubuser.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.toniel.githubuser.database.UserRepository;
import com.toniel.githubuser.model.User;

import java.util.List;

public class UserDetailViewModel extends ViewModel {
    private final UserRepository userRepository;

    public UserDetailViewModel(Application application) {
        this.userRepository = new UserRepository(application);
    }

    public void insert(User user){
        userRepository.insert(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public LiveData<User> currentUser(String username){
        return userRepository.findByLogin(username);
    }
    public LiveData<List<User>> getAll(){
        return userRepository.getAllUsers();
    }
}
