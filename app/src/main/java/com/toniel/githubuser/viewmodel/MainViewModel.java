package com.toniel.githubuser.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.toniel.githubuser.MainActivity;
import com.toniel.githubuser.User;
import com.toniel.githubuser.UserResponse;
import com.toniel.githubuser.config.ApiConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private MutableLiveData<List<User>> users;
    private String username;


    public LiveData<List<User>> getUsers(String username){
        if (users==null){
            users = new MutableLiveData<List<User>>();
            loadUsers(username);
        }
        return users;
    }



    private void loadUsers(String username) {
        Call<UserResponse> client = ApiConfig.getApiService().getUsers(username);
        client.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        users.setValue(response.body().getItems());
                    }
                }else{
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: gagal");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }


}
