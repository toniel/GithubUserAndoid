package com.toniel.githubuser.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.toniel.githubuser.database.UserRepository;
import com.toniel.githubuser.model.User;

import java.util.List;

public class FavouriteUserViewModel extends ViewModel {
    private final UserRepository userRepository;

    public FavouriteUserViewModel(Application application) {
        this.userRepository = new UserRepository(application);
    }

    public LiveData<List<User>> getAllFavouriteUsers(){
        return  userRepository.getAllUsers();
    }


}
