package com.toniel.githubuser.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.toniel.githubuser.model.User;
import com.toniel.githubuser.model.UserResponse;
import com.toniel.githubuser.config.ApiConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private final MutableLiveData<List<User>> _users = new MutableLiveData<>();
    public LiveData<List<User>> getUsers(){
        return _users;
    }

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading(){
        return _isLoading;
    }

    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    public LiveData<String> getToastMessage(){
        return toastMessage;
    }

    public final void getGithubUser(String username){
        _isLoading.setValue(true);
        Call<UserResponse> client = ApiConfig.getApiService().getUsers(username);
        client.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful()){

                    if (response.body().getTotalCount()==0){
                        Log.d(TAG,"user not found");
                        toastMessage.setValue("User not found");

                    }else{
                        _users.setValue(response.body().getItems());
                    }


                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                _isLoading.setValue(false);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }



}
